package com.github.steeldev.betternetherite.util.misc;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;

public class BNSound {
    public Sound sound;
    public SoundCategory category;
    public float volume;
    public float pitch;

    public BNSound(Sound sound,
                   SoundCategory category,
                   float volume,
                   float pitch) {
        this.sound = sound;
        this.category = category;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void playSound(Location location) {
        location.getWorld().playSound(location, sound, category, volume, pitch);
    }
}
