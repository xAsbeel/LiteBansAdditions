package pl.minecodes.litebansadditions.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public final class MessageUtil {

    public static final String ARROW_RIGHT = "»";
    public static final String ARROW_LEFT = "«";

    private MessageUtil() {}

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(format(message));
    }

    public static String format(String message) {
        message = applySpecialChars(message);
        message = translateLegacyColor(message);

        return message;
    }

    public static List<String> format(List<String> messages) {
        return messages
                .stream()
                .map(msg -> {
                    msg = format(msg);
                    return msg;
                })
                .collect(Collectors.toList());
    }

    public static String translateLegacyColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String applySpecialChars(String message) {
        message = message.replace(">>", ARROW_RIGHT);
        message = message.replace("<<", ARROW_LEFT);
        message = message.replace("%nl%", "\n").replace("%newline%", "\n");

        return message;
    }

}
