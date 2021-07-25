package pl.minecodes.litebansadditions.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import pl.minecodes.litebansadditions.config.element.EventConfiguration;
import pl.minecodes.litebansadditions.objects.PunishmentType;

import java.util.HashMap;
import java.util.Map;

@Header("LiteBansAdditions configuration.")
@Header(" ")
@Header("Placeholders can be found here:")
@Header("https://paste.letcraft.pl/izoyohibiq.shell")
@Header(" ")
@Header("Plugin issues and questions:")
@Header("https://github.com/Nikox3003/LiteBansAdditions/issues")
@Header(" ")
@Header("Sound and effect names must be valid Spigot names")
@Header("https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html")
@Header("https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html")

@Getter
@Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfiguration extends OkaeriConfig {

    @Comment("DO NOT CHANGE THIS!")
    private String configVersion = "1.0";
    private String dateFormat = "dd/MM/yyyy HH:mm";
    private String webhookUrl = "https://discord.com/api/webhooks/xxxxxx/xxxxxxxxx";
    private String placeholderYes = "Yes";
    private String placeholderNo = "No";
    @Comment("%punishedTo% if punishment is permanent")
    private String placeholderNeverExpires = "Never";

    private Map<PunishmentType, EventConfiguration> events = new HashMap<PunishmentType, EventConfiguration>() {{
        for (PunishmentType value : PunishmentType.values()) {
            this.put(value, new EventConfiguration());
        }
    }};
}
