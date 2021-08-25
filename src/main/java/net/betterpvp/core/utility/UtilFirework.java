package net.betterpvp.core.utility;


import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.level.World;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;


public class UtilFirework extends EntityFireworks {
    Player[] players = null;

    public UtilFirework(World world, Player... p) {
        super(EntityTypes.D, world);
        players = p;
        this.a(0.25F, 0.25F);
    }

    boolean gone = false;

    @Override
    public void tick() {
        if (gone) {
            return;
        }

        // t = world
        if (!this.t.isClientSide()) {
            gone = true;

            if (players != null)
                if (players.length > 0)
                    for (Player p : players) {
                        // b = playerConnection
                        (((CraftPlayer) p).getHandle()).b.sendPacket(new PacketPlayOutEntityStatus(this, (byte) 17));
                    }
                else
                    t.broadcastEntityEffect(this, (byte) 17);
            this.die();
        }
    }

    /**
     * Spawns a firework at a specific location
     *
     * @param location Location to spawn the firework
     * @param effect   Effect the firework has
     * @param players  Players that can see the firework
     */
    public static void spawn(Location location, FireworkEffect effect, Player... players) {
        try {
            UtilFirework firework = new UtilFirework(((CraftWorld) location.getWorld()).getHandle(), players);
            FireworkMeta meta = ((Firework) firework.getBukkitEntity()).getFireworkMeta();
            meta.addEffect(effect);
            ((Firework) firework.getBukkitEntity()).setFireworkMeta(meta);
            firework.setPosition(location.getX(), location.getY(), location.getZ());

            if ((((CraftWorld) location.getWorld()).getHandle()).addEntity(firework)) {
                firework.setInvisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

