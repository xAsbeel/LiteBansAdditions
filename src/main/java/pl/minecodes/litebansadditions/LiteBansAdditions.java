package pl.minecodes.litebansadditions;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.postprocessor.SectionSeparator;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import litebans.api.Events;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.minecodes.litebansadditions.abstraction.AbstractPunishmentListener;
import pl.minecodes.litebansadditions.commands.MainCommand;
import pl.minecodes.litebansadditions.config.PluginConfiguration;
import pl.minecodes.litebansadditions.handlers.WebhookHandler;
import pl.minecodes.litebansadditions.listeners.PunishmentListener;
import pl.minecodes.litebansadditions.util.UpdateChecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class LiteBansAdditions extends JavaPlugin {

    private static LiteBansAdditions instance;
    private static PluginConfiguration configuration;

    public static LiteBansAdditions getInstance() {
        return instance;
    }

    public static PluginConfiguration getConfiguration() {
        return configuration;
    }

    private List<AbstractPunishmentListener> liteBansListeners;

    @Override
    public void onEnable() {
        instance = this;
        liteBansListeners = new ArrayList<>();

        Metrics metrics = new Metrics(this, 12204);

        configuration = ConfigManager.create(PluginConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(SectionSeparator.NEW_LINE));
            it.withBindFile(new File(getDataFolder(), "config.yml"));
            it.saveDefaults();
            it.load(true);
        });

        if(configuration.isCheckForUpdates()) {
            UpdateChecker updateChecker = new UpdateChecker();
        }

        registerLiteBansListeners();
        getCommand("litebansadditions").setExecutor(new MainCommand());

    }

    private void registerLiteBansListeners() {
        registerLiteBansListener(new PunishmentListener(new WebhookHandler(configuration.getWebhookUrl())));
    }

    private void registerLiteBansListener(AbstractPunishmentListener listener) {
        liteBansListeners.add(listener);
        listener.register();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        unregisterLiteBansListeners();
    }

    private void unregisterLiteBansListeners() {
        liteBansListeners.forEach(AbstractPunishmentListener::unregister);
        liteBansListeners.clear();
    }

    public void onReload() {
        getLogger().info("Starting plugin reload...");
        unregisterLiteBansListeners();
        configuration.load();
        registerLiteBansListeners();
        getLogger().info("Reload completed!");
    }
}
