package net.betterpvp.core.client.commands;

import net.betterpvp.core.Core;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.client.commands.events.SpawnTeleportEvent;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.framework.UpdateEvent;
import net.betterpvp.core.utility.UtilItem;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.UtilTime;
import net.betterpvp.core.utility.recharge.RechargeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class SpawnCommand extends Command implements Listener {

    private WeakHashMap<Player, Long> spawns;

    public SpawnCommand() {
        super("spawn", new String[]{}, Rank.PLAYER);
        spawns = new WeakHashMap<>();

    }

    @Override
    public void execute(Player player, String[] args) {

        if (args == null || args.length == 0) {

            if (ClientUtilities.getOnlineClient(player).hasRank(Rank.ADMIN, false)) {
                player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                return;
            }
            Bukkit.getPluginManager().callEvent(new SpawnTeleportEvent(player));

        }
    }

    @Override
    public void help(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
