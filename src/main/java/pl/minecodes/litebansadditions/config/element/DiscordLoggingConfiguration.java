package pl.minecodes.litebansadditions.config.element;

import club.minnced.discord.webhook.send.WebhookEmbed;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class DiscordLoggingConfiguration extends OkaeriConfig {

    public DiscordLoggingConfiguration() {
        enabled = true;
        embed = false;
        message = "This is default message!";
    }

    private boolean enabled;
    private boolean embed;
    private String message;
    private String embedTitle;
    private String embedTitleUrl;
    private String embedDescription;
    private String embedColor;
    private String embedAuthorName;
    private String embedAuthorIcon;
    private String embedAuthorUrl;
    private String embedFooterText;
    private String embedFooterIcon;
    private String embedIcon;
    private String embedThumbnail;
    private List<FieldConfiguration> embedFields = Collections.singletonList(
            new FieldConfiguration(true, "Test name", "Test value")
    );

    @Getter
    @Setter
    @AllArgsConstructor
    @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
    public static class FieldConfiguration extends OkaeriConfig {

        private boolean inline;
        private String name;
        private String value;

    }


}
