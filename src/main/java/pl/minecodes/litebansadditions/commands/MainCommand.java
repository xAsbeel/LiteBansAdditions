package pl.minecodes.litebansadditions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import pl.minecodes.litebansadditions.LiteBansAdditions;
import pl.minecodes.litebansadditions.util.MessageUtil;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("litebansadditions.reload")) {
            PluginDescriptionFile desc = LiteBansAdditions.getInstance().getDescription();
            MessageUtil.sendMessage(sender, "&8>> &fThis server is running &a" + desc.getFullName() + "&f by&a Nikox3003");
            return false;
        }

        LiteBansAdditions.getInstance().onReload();
        MessageUtil.sendMessage(sender, "&8>> &aConfiguration reloaded!");
        return true;
    }
}
