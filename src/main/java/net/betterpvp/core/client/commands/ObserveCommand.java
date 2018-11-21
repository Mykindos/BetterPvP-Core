package net.betterpvp.core.client.commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.UtilPlayer;

public class ObserveCommand extends Command {

    public static HashMap<UUID, Location> observer = new HashMap<UUID, Location>();

    public ObserveCommand() {
        super("observe", new String[]{"obs"}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args == null || args.length == 0) {
            if (observer.containsKey(player.getUniqueId())) {
                player.teleport(observer.get(player.getUniqueId()));
                player.setGameMode(GameMode.SURVIVAL);
                observer.remove(player.getUniqueId());
                UtilMessage.message(player, "Observe", "You have left Observer mode.");
            } else {
                observer.put(player.getUniqueId(), player.getLocation());
                player.setGameMode(GameMode.SPECTATOR);
                UtilMessage.message(player, "Observe", "You entered into Observer mode.");
            }
        } else if (args.length == 1) {
            Player target = UtilPlayer.searchOnline(player, args[0], false);
            if (target == null) {
                UtilPlayer.searchOnline(player, args[0], true);
                return;
            }

            if (observer.containsKey(target.getUniqueId())) {
                target.teleport(observer.get(target.getUniqueId()));
                target.setGameMode(GameMode.SURVIVAL);
                observer.remove(target.getUniqueId());
                UtilMessage.message(target, "Observe", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " removed you from observer mode.");
                UtilMessage.message(player, "Observe", "You removed " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " out of Observer mode.");
            } else {
                observer.put(target.getUniqueId(), target.getLocation());
                target.setGameMode(GameMode.SPECTATOR);
                UtilMessage.message(target, "Observe", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " entered you into observer mode.");
                UtilMessage.message(player, "Observe", "You entered " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " into Observer mode.");
            }
        }
    }

    @Override
    public void help(Player player) {

    }

}
