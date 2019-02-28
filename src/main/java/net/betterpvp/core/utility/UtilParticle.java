package net.betterpvp.core.utility;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class UtilParticle {

    /*
     * Players a particle at the location specified
     */
    public final static void playParticle(EnumParticle type, Location location, float locX, float locY, float locZ, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                type, true, locX, locY, locZ, offsetX, offsetY, offsetZ, speed, count);

        for (Player reciever : Bukkit.getOnlinePlayers()) {
            if ((reciever.getWorld() == location.getWorld())) {
                ((CraftPlayer) reciever).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public enum ParticleType {

        LARGE_EXPLOSION("largeexplode"),
        FIREWORK_SPARK("fireworksSpark"),
        BUBBLE("bubble"),
        SUSPENDED("suspended"),
        DEPTH_SUSPEND("depthsuspend"),
        TOWN_AURA("townaura"),
        CRITICAL_HIT("crit"),
        MAGICAL_CRITICAL_HIT("magicCrit"),
        SMOKE("smoke"),
        MOB_SPELL("mobSpell"),
        MOB_SPELL_AMBIENT("mobSpellAmbient"),
        SPELL("spell"),
        INSTANT_SPELL("instantSpell"),
        WITCH_MAGIC("witchMagic"),
        NOTE("note"),
        PORTAL("portal"),
        ENCHANTMENT_TABLE("enchantmenttable"),
        EXPLODE("explode"),
        FLAME("flame"),
        LAVA("lava"),
        FOOTSTEP("footstep"),
        SPLASH("splash"),
        LARGE_SMOKE("largesmoke"),
        CLOUD("cloud"),
        REDSTONE("reddust"),
        SNOWBALL_POOF("snowballpoof"),
        DROP_WATER("dripWater"),
        DROP_LAVA("dripLava"),
        SNOW_SHOVEL("snowshovel"),
        SLIME("slime"),
        HEART("heart"),
        ANGRY_VILLAGER("angryVillager"),
        HAPPY_VILLAGER("happyVillager"),
        HUGE_EXPLOSION("hugeexplosion");

        private final String id;

        ParticleType(String name) {
            this.id = name;
        }

        public String getParticleName() {
            return this.id;
        }
    }
}
