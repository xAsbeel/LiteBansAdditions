package pl.minecodes.litebansadditions.config.element;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class EffectConfiguration extends OkaeriConfig {

    public EffectConfiguration() {
        enabled = false;
    }

    public EffectConfiguration(String type, int duration, int amplifier, boolean ambient, boolean particles) {
        enabled = true;
        this.type = type;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.particles = particles;
    }

    private boolean enabled;
    private String type;
    private int duration;
    private int amplifier;
    private boolean ambient;
    private boolean particles;

}
