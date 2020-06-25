package net.betterpvp.core.utility;

import net.minecraft.server.v1_16_R1.EntityFireworks;
import net.minecraft.server.v1_16_R1.EntityTypes;
import net.minecraft.server.v1_16_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_16_R1.World;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;


public class UtilFirework extends EntityFireworks {
    Player[] players = null;

    public UtilFirework(World world, Player... p) {
        super(EntityTypes.FIREWORK_ROCKET, world);
        players = p;
        this.a(0.25F, 0.25F);
    }

    boolean gone = false;

    @Override
    public void tick() {
        if (gone) {
            return;
        }

        if (!this.world.isClientSide) {
            gone = true;

            if (players != null)
                if (players.length > 0)
                    for (Player p : players) {
                        (((CraftPlayer) p).getHandle()).playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte) 17));
                    }
                else
                    world.broadcastEntityEffect(this, (byte) 17);
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

