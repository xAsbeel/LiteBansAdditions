package pl.minecodes.litebansadditions.objects;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;

public class DiscordMessage {

    private boolean embed;
    private PunishmentType punishmentType;
    private String content;
    private WebhookEmbed embedMessage;

    public boolean isEmbed() {
        return embed;
    }

    public PunishmentType getPunishmentType() {
        return punishmentType;
    }

    public String getContent() {
        return content;
    }

    public WebhookEmbed getEmbedMessage() {
        return embedMessage;
    }

    public void setEmbed(boolean embed) {
        this.embed = embed;
    }

    public void setPunishmentType(PunishmentType punishmentType) {
        this.punishmentType = punishmentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEmbedMessage(WebhookEmbed embedMessage) {
        this.embedMessage = embedMessage;
    }
}
