package net.betterpvp.core.utility;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class UtilPacket {

    /**
     * Sends a packet to a player
     *
     * @param player Player to receive the packet
     * @param packet Packet to send
     */
    public static void send(Player player, Packet<?> packet) {
        if ((player == null) || (packet == null)) {
            return;
        }
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
