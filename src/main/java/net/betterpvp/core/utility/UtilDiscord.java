package net.betterpvp.core.utility;

import net.betterpvp.core.networking.discord.DiscordWebhook;

public class UtilDiscord {

    /**
     * Send a msg via discord webhook
     * @param webhook Webhook URL
     * @param author Author of the message (e.g. player name)
     * @param message The webhook content
     */
    public static void sendWebhook(String webhook, String author, String message){
        DiscordWebhook hook = new DiscordWebhook(webhook);
        hook.setUsername(author);
        hook.setContent(message);
        hook.execute();
    }
}
