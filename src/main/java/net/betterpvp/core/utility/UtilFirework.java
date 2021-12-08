package net.betterpvp.core.utility;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class UtilFirework {

    /**
     * Spawn a firework at a location
     * @param plugin The plugin instance
     * @param effect The effect to apply to the firework
     * @param loc The location of the firework
     */
    public static void spawnFireworkWithEffects(Plugin plugin, FireworkEffect effect, Location loc){
        Firework firework = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fwm = firework.getFireworkMeta();
        fwm.clearEffects();
        fwm.addEffect(effect);
        firework.setFireworkMeta(fwm);
        new BukkitRunnable() {
            public void run() {
                firework.detonate();
            }
        }.runTaskLater(plugin, 1);
    }
}
