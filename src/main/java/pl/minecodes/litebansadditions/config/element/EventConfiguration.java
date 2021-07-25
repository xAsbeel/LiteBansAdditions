package pl.minecodes.litebansadditions.config.element;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

@Getter
@Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class EventConfiguration extends OkaeriConfig {

    SoundConfiguration sound = new SoundConfiguration(Sound.BLOCK_ANVIL_DESTROY, 0.1F, 1);
    EffectConfiguration effect = new EffectConfiguration(PotionEffectType.CONFUSION.getName(), 90, 1, true, false);
    DiscordLoggingConfiguration discord = new DiscordLoggingConfiguration();

}
