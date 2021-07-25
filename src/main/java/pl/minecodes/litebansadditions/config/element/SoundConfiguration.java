package pl.minecodes.litebansadditions.config.element;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;

@Getter
@Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class SoundConfiguration extends OkaeriConfig {

    public SoundConfiguration() {
        enabled = false;
    }

    public SoundConfiguration(Sound sound, float pitch, float volume) {
        enabled = true;
        this.sound = sound;
        this.pitch = pitch;
        this.volume = volume;
    }

    boolean enabled;
    private Sound sound;
    private float pitch;
    private float volume;

}
