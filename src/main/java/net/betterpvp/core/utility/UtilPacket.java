package net.betterpvp.core.utility;

import net.minecraft.network.protocol.Packet;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
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
        // b = playerConnection
        ((CraftPlayer) player).getHandle().b.a(packet);
    }
}
