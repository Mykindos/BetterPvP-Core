package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.client.commands.events.SpawnTeleportEvent;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

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
            Bukkit.getPluginManager().callEvent(new SpawnTeleportEvent(player));

        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(SpawnTeleportEvent e) {
        if (spawns.containsKey(e.getPlayer())) {
            UtilMessage.message(e.getPlayer(), "Spawn", "You are already teleporting to spawn.");
            return;
        }

        if (e.isCancelled()) {
            return;
        }

        if (ClientUtilities.getOnlineClient(e.getPlayer()).hasRank(Rank.ADMIN, false)) {
            e.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
        } else {
            UtilMessage.message(e.getPlayer(), "Spawn", "Teleporting to spawn in " + ChatColor.GREEN + "15 seconds, " + ChatColor.GRAY + "do not move!");
            spawns.put(e.getPlayer(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (spawns.containsKey(e.getPlayer())) {
            if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()
                    || e.getFrom().getY() != e.getTo().getY()) {

                UtilMessage.message(e.getPlayer(), "Spawn", "Teleport cancelled.");
                spawns.remove(e.getPlayer());
            }

        }
    }

	/*@EventHandler
	public void onUpdate(UpdateEvent e){
		if(e.getType() == UpdateType.SEC){
			Iterator<Entry<Player, Long>> it = spawns.entrySet().iterator();
			while(it.hasNext()){
				Entry<Player, Long> next = it.next();
				if(next.getKey() == null){
					it.remove();
					continue;
				}
				if(MorphUtilities.isMorphed(next.getKey())){
					UtilMessage.message(next.getKey(), "Spawn", "Teleport cancelled.");
					continue;
				}
				if(UtilItem.hasValuables(next.getKey())){
					UtilMessage.message(next.getKey(), "Spawn", "Teleport cancelled.");
					it.remove();
					continue;
				}
				if(UtilTime.elapsed(next.getValue(), 15000)){
					if(RechargeManager.getInstance().add(next.getKey(), "Spawn", 900, true, false)){
						next.getKey().teleport(Bukkit.getWorld("world").getSpawnLocation());
						UtilMessage.message(next.getKey(), "Spawn", "You teleported to spawn.");
						it.remove();
					}
				}
			}
		}
	}
	*/


    @Override
    public void help(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
