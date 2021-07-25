package pl.minecodes.litebansadditions.handlers;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import pl.minecodes.litebansadditions.util.SchedulerUtil;

public class WebhookHandler {

    private String url;

    public WebhookHandler(String url) {
        this.url = url;
    }

    public void sendMessage(String message) {
        if(message.isEmpty()) {
            return;
        }

        SchedulerUtil.runAsynchronously(() -> {
            try (WebhookClient client = WebhookClient.withUrl(url)) {
                client.send(message);
            } catch (Exception ignored) {}
        });
    }

    public void sendMessage(WebhookEmbed embed) {
        SchedulerUtil.runAsynchronously(() -> {
            try (WebhookClient client = WebhookClient.withUrl(url)) {
                client.send(embed);
            } catch (Exception ignored) {}
        });
    }

    public void sendMessage(String message, String username) {
        if(message.isEmpty()) {
            return;
        }

        SchedulerUtil.runAsynchronously(() -> {
            try (WebhookClient client = WebhookClient.withUrl(url)) {
                WebhookMessageBuilder builder = new WebhookMessageBuilder()
                        .setUsername(username)
                        .setContent(message);

                client.send(builder.build());
            } catch (Exception ignored) {}
        });
    }

    public void sendMessage(WebhookEmbed embed, String username) {
        SchedulerUtil.runAsynchronously(() -> {
            try (WebhookClient client = WebhookClient.withUrl(url)) {
                WebhookMessageBuilder builder = new WebhookMessageBuilder()
                        .setUsername(username)
                        .addEmbeds(embed);

                client.send(builder.build());
            } catch (Exception ignored) {}
        });
    }

    public void sendMessage(String message, String username, String avatarUrl) {
        if(message.isEmpty()) {
            return;
        }

        SchedulerUtil.runAsynchronously(() -> {
            try (WebhookClient client = WebhookClient.withUrl(url)) {
                WebhookMessageBuilder builder = new WebhookMessageBuilder()
                        .setUsername(username)
                        .setAvatarUrl(avatarUrl)
                        .setContent(message);

                client.send(builder.build());
            } catch (Exception ignored) {}
        });
    }

    public void sendMessage(WebhookEmbed embed, String username, String avatarUrl) {
        SchedulerUtil.runAsynchronously(() -> {
            try (WebhookClient client = WebhookClient.withUrl(url)) {
                WebhookMessageBuilder builder = new WebhookMessageBuilder()
                        .setUsername(username)
                        .setAvatarUrl(avatarUrl)
                        .addEmbeds(embed);

                client.send(builder.build());
            } catch (Exception ignored) {}
        });
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
