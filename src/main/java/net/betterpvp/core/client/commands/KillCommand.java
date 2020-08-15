package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.framework.UpdateEvent;
import net.betterpvp.core.framework.UpdateEvent.UpdateType;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.UtilPlayer;
import net.betterpvp.core.utility.UtilTime;
import net.betterpvp.core.utility.recharge.RechargeManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public class KillCommand extends Command implements Listener {

    private WeakHashMap<Player, Long> kills;


    public KillCommand() {
        super("kill", new String[]{"suicide", "neck", "kys", "kms", "bleach"}, Rank.PLAYER);

        kills = new WeakHashMap<>();
    }

    @Override
    public void execute(Player player, String[] args) {

        if (args == null || args.length == 0) {
            if (kills.containsKey(player)) {
                UtilMessage.message(player, "Suicide", "You are already waiting to neck.");
                return;
            }
            if (RechargeManager.getInstance().add(player, "Suicide", 30, true, false)) {

                kills.put(player, System.currentTimeMillis());
                UtilMessage.message(player, "Suicide", "Suiciding in " + ChatColor.GREEN + "10 seconds" + ChatColor.GRAY + ".");


            }
            return;
        }

        if (ClientUtilities.getClient(player).hasRank(Rank.ADMIN, true)) {
            Player target = UtilPlayer.searchOnline(player, args[0], false);
            if (target == null) {
                UtilPlayer.searchOnline(player, args[0], true);
                return;
            }

            if (args.length == 1) {
                UtilMessage.message(player, "Suicide", "You killed " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                UtilMessage.message(target, "Suicide", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " killed you.");

                target.setHealth(0);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (kills.containsKey(e.getPlayer())) {
            if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {

                UtilMessage.message(e.getPlayer(), "Suicide", "Suicide cancelled.");
                kills.remove(e.getPlayer());
            }

        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getType() == UpdateType.SEC) {
            Iterator<Entry<Player, Long>> it = kills.entrySet().iterator();
            while (it.hasNext()) {
                Entry<Player, Long> next = it.next();
                if (next.getKey() == null) {
                    it.remove();
                    continue;
                }

                if (next.getKey().isDead()) {
                    it.remove();
                    continue;
                }

                if (UtilTime.elapsed(next.getValue(), 10000)) {
                    next.getKey().setHealth(0);
                    UtilMessage.message(next.getKey(), "Suicide", "You comitted suicide.");
                    it.remove();
                }
            }
        }
    }


    @Override
    public void help(Player player) {

    }

}
