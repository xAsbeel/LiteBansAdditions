package pl.minecodes.litebansadditions.listeners;

import litebans.api.Entry;
import litebans.api.Events;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.minecodes.litebansadditions.LiteBansAdditions;
import pl.minecodes.litebansadditions.abstraction.AbstractPunishmentListener;
import pl.minecodes.litebansadditions.config.DiscordMessagesProvider;
import pl.minecodes.litebansadditions.config.element.EffectConfiguration;
import pl.minecodes.litebansadditions.config.element.EventConfiguration;
import pl.minecodes.litebansadditions.config.element.SoundConfiguration;
import pl.minecodes.litebansadditions.handlers.WebhookHandler;
import pl.minecodes.litebansadditions.objects.DiscordMessage;
import pl.minecodes.litebansadditions.objects.PunishmentType;
import pl.minecodes.litebansadditions.util.SchedulerUtil;

import java.util.Map;
import java.util.UUID;

public class PunishmentListener extends AbstractPunishmentListener {

    private final WebhookHandler handler;
    private final Map<PunishmentType, EventConfiguration> eventConfiguration;

    public PunishmentListener(WebhookHandler handler) {
        this.handler = handler;
        this.eventConfiguration = LiteBansAdditions.getConfiguration().getEvents();
    }

    @Override
    public void onEntryAdded(Entry entry) {
        // LiteBans invokes listeners asynchronously
        // Bukkit API cannot be used off the main thread
        SchedulerUtil.run(() -> {
            if(entry.isSilent() && !LiteBansAdditions.getConfiguration().isActionsOnSilentPunishments()) {
                return;
            }

            PunishmentType type = PunishmentType.get(entry.getType(), PunishmentType.EntryListeningType.ADDED);
            logOnDiscord(entry, type);
            playSound(type);
            applyEffect(type);
        });
    }

    @Override
    public void onEntryRemoved(Entry entry) {
        // LiteBans invokes listeners asynchronously
        // Bukkit API cannot be used off the main thread
        SchedulerUtil.run(() -> {
            if(entry.isSilent() && !LiteBansAdditions.getConfiguration().isActionsOnSilentPunishments()) {
                return;
            }
            PunishmentType type = PunishmentType.get(entry.getType(), PunishmentType.EntryListeningType.REMOVED);
            logOnDiscord(entry, type);
            playSound(type);
            applyEffect(type);
        });
    }

    private void logOnDiscord(Entry entry, PunishmentType type) {
        if(!eventConfiguration.get(type).getDiscord().isEnabled()) {
            return;
        }
        DiscordMessage message = DiscordMessagesProvider.provideFromType(type, entry);
        if(message.isEmbed()) {
            handler.sendMessage(message.getEmbedMessage());
        } else {
            handler.sendMessage(message.getContent());
        }
    }

    private void playSound(PunishmentType type) {
        SoundConfiguration sound = eventConfiguration.get(type).getSound();
        if(!sound.isEnabled()) {
            return;
        }

        if(sound.getSound() == null) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(
                player.getLocation(),
                sound.getSound(),
                sound.getVolume(),
                sound.getPitch()
        ));
    }

    private void applyEffect(PunishmentType type) {
        EffectConfiguration effect = eventConfiguration.get(type).getEffect();
        if(!effect.isEnabled()) {
            return;
        }

        PotionEffectType effectType = PotionEffectType.getByName(effect.getType());
        if(effectType == null) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> player.addPotionEffect(new PotionEffect(
                effectType,
                effect.getDuration(),
                effect.getAmplifier(),
                effect.isAmbient(),
                effect.isParticles()
        )));
    }

}
