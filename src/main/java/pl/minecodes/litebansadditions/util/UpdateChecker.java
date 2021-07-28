package pl.minecodes.litebansadditions.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import pl.minecodes.litebansadditions.LiteBansAdditions;
import pl.minecodes.litebansadditions.listeners.PlayerJoinListener;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateChecker {

    private final String RESOURCE_URL = "https://api.spigotmc.org/legacy/update.php?resource=94661";
    private boolean updateNeeded = false;
    private String remoteVersion = "";

    public UpdateChecker() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), LiteBansAdditions.getInstance());
        SchedulerUtil.runTaskTimerAsync(() -> {
            updateNeeded = checkUpdate();
            notifyAboutUpdate();
        }, 0L, TimeUnit.HOURS.toSeconds(6) * 20L);

    }

    public boolean checkUpdate() {
        Logger logger = LiteBansAdditions.getInstance().getLogger();
        logger.info("Checking for updates...");
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(RESOURCE_URL).openConnection();
            connection.setRequestMethod("GET");
            String raw = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

            String localVersion = LiteBansAdditions.getInstance().getDescription().getVersion();
            remoteVersion = raw.replace("v", "");

            if(!localVersion.equalsIgnoreCase(remoteVersion)) {
                return true;
            }

        } catch (IOException exception) {
            logger.warning("There was an error while trying to check remote version!");
            exception.printStackTrace();
            return false;
        }

        logger.info("You are up to date!");
        return false;
    }

    public void notifyAboutUpdate() {
        if(!updateNeeded) {
            return;
        }

        String localVersion = LiteBansAdditions.getInstance().getDescription().getVersion();
        String updateMessage = MessageUtil.format(
                " \n" +
                        "&8>> &fNew version of &eLiteBansAdditions&f is available!\n" +
                        "&8>> &fCurrent version:&c " + localVersion + "\n" +
                        "&8>> &fNew version: &a" + remoteVersion + "\n" +
                        "&8>> &fUpdate now! &ehttps://www.spigotmc.org/resources/litebansadditions.94661/\n" +
                        " "
        );

        Logger logger = LiteBansAdditions.getInstance().getLogger();
        logger.info(updateMessage);
    }

    public void notifyPlayerAboutUpdate(Player player) {
        if(!updateNeeded) {
            return;
        }

        String localVersion = LiteBansAdditions.getInstance().getDescription().getVersion();
        String updateMessage = MessageUtil.format(
                " \n" +
                        "&8>> &fNew version of &eLiteBansAdditions&f is available!\n" +
                        "&8>> &fCurrent version:&c " + localVersion + "\n" +
                        "&8>> &fNew version: &a" + remoteVersion + "\n" +
                        "&8>> &fUpdate now! &ehttps://www.spigotmc.org/resources/litebansadditions.94661/\n" +
                        " "
        );

        player.sendMessage(updateMessage);
    }

}
