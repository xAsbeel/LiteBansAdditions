package pl.minecodes.litebansadditions.config;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import litebans.api.Entry;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import pl.minecodes.litebansadditions.LiteBansAdditions;
import pl.minecodes.litebansadditions.config.element.DiscordLoggingConfiguration;
import pl.minecodes.litebansadditions.objects.DiscordMessage;
import pl.minecodes.litebansadditions.objects.PunishmentType;

import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public final class DiscordMessagesProvider {

    private static final PluginConfiguration CONFIGURATION = LiteBansAdditions.getConfiguration();
    private static Entry entry;
    private static Map<String, String> placeholders;

    private DiscordMessagesProvider() {}

    public static DiscordMessage provideFromType(PunishmentType type, Entry entry) {
        DiscordMessagesProvider.entry = entry;
        DiscordMessage discordMessage = new DiscordMessage();
        discordMessage.setPunishmentType(type);
        DiscordLoggingConfiguration discordConf = CONFIGURATION.getEvents().get(type).getDiscord();
        discordMessage.setEmbed(discordConf.isEmbed());

        preparePlaceholders();

        if (discordConf.isEmbed()) {
            discordMessage.setEmbedMessage(buildEmbedFromConfiguration(discordConf));
        } else {
            discordMessage.setContent(applyPlaceholders(discordConf.getMessage()));
        }

        return discordMessage;
    }

    private static WebhookEmbed buildEmbedFromConfiguration(DiscordLoggingConfiguration configuration) {
        WebhookEmbedBuilder builder = new WebhookEmbedBuilder();
        builder.setTimestamp(Instant.now());

        if (!configuration.isEmbed()) {
            return builder.build();
        }

        if (configuration.getEmbedTitle() != null) {
            builder.setTitle(new WebhookEmbed.EmbedTitle(
                    applyPlaceholders(configuration.getEmbedTitle()),
                    applyPlaceholders(configuration.getEmbedTitleUrl())
            ));
        }

        if (configuration.getEmbedDescription() != null) {
            builder.setDescription(applyPlaceholders(configuration.getEmbedDescription()));
        }

        try {
            Color color = Color.decode(configuration.getEmbedColor());
            builder.setColor(color.getRGB());
        } catch (NumberFormatException ignored) {
        }

        if(configuration.getEmbedAuthorName() != null) {
            builder.setAuthor(new WebhookEmbed.EmbedAuthor(
                    applyPlaceholders(configuration.getEmbedAuthorName()),
                    applyPlaceholders(configuration.getEmbedAuthorIcon()),
                    applyPlaceholders(configuration.getEmbedAuthorUrl())
            ));
        }

        if (configuration.getEmbedFooterText() != null) {
            builder.setFooter(new WebhookEmbed.EmbedFooter(
                    applyPlaceholders(configuration.getEmbedFooterText()),
                    applyPlaceholders(configuration.getEmbedFooterIcon())
            ));
        }

        if (configuration.getEmbedFields() != null) {
            configuration.getEmbedFields().forEach(field -> builder.addField(
                    new WebhookEmbed.EmbedField(
                            field.isInline(),
                            applyPlaceholders(field.getName()),
                            applyPlaceholders(field.getValue())
                    )
            ));
        }

        builder.setImageUrl(applyPlaceholders(configuration.getEmbedIcon()));
        builder.setThumbnailUrl(applyPlaceholders(configuration.getEmbedThumbnail()));

        return builder.build();
    }

    private static void preparePlaceholders() {
        placeholders = new HashMap<>();
        OfflinePlayer player = null;
        try {
            player = Bukkit.getOfflinePlayer(UUID.fromString(entry.getUuid()));
        } catch (NullPointerException ignored) {
        }

        if (player != null && player.getName() != null) {
            placeholders.put("%player%", player.getName());
        } else {
            placeholders.put("%player%", entry.getUuid());
        }

        if(entry.getRemovedByName() != null) {
            placeholders.put("%executor%", entry.getRemovedByName());
        } else {
            placeholders.put("%executor%", entry.getExecutorName());
        }
        long to = entry.getDateEnd();

        DateTimeFormatter formatter = getFormatter();

        if (to < 0) {
            placeholders.put("%punishedTo%", CONFIGURATION.getPlaceholderNeverExpires());
        } else {
            LocalDateTime time = LocalDateTime.from(Instant.ofEpochMilli(to));
            placeholders.put("%punishedTo%", formatter.format(time));
        }

        LocalDateTime punishmentTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(entry.getDateStart()),
                ZoneId.systemDefault()
        );
        placeholders.put("%punishedDate%", formatter.format(punishmentTime));
        placeholders.put("%punishmentDuration%", entry.getDurationString());
        placeholders.put("%serverOrigin%", entry.getServerOrigin());
        placeholders.put("%serverScope%", entry.getServerScope());
        placeholders.put("%reason%", entry.getReason());
        placeholders.put("%permanent%", entry.isPermanent()
                ? CONFIGURATION.getPlaceholderYes()
                : CONFIGURATION.getPlaceholderNo()
        );
    }

    private static DateTimeFormatter getFormatter() {
        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern(CONFIGURATION.getDateFormat());
        } catch (IllegalArgumentException exception) {
            LiteBansAdditions.getInstance().getLogger().warning("Invalid date format! Using default");
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        }
        return formatter;
    }

    private static String applyPlaceholders(String message) {
        if(message == null) {
            return null;
        }
        for (Map.Entry<String, String> mapEntry : placeholders.entrySet()) {
            String placeholder = mapEntry.getKey();
            String replacement = mapEntry.getValue();

            Pattern pattern = Pattern.compile(
                    placeholder,
                    Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
            );

            message = pattern.matcher(message).replaceAll(replacement);
        }

        return message;
    }

}

