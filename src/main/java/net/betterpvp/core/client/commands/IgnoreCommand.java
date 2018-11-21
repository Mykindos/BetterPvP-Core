package net.betterpvp.core.client.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.client.mysql.ClientRepository;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.UtilPlayer;

public class IgnoreCommand extends Command {

    public IgnoreCommand() {
        super("ignore", new String[]{}, Rank.PLAYER);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args == null || args.length == 0) {
            UtilMessage.message(player, "Ignore", "Player argument missing.");
            return;
        }

        Player target = UtilPlayer.searchOnline(player, args[0], false);
        if (target == null) {
            UtilPlayer.searchOnline(player, args[0], true);
            return;
        }

        if (args.length == 1) {
            if (target.getName().equals(player.getName())) {
                UtilMessage.message(player, "Ignore", "You cannot ignore yourself.");
                return;
            }

            if (ClientUtilities.getClient(player).getIgnore().contains(target.getUniqueId())) {
                ClientUtilities.getClient(player).getIgnore().remove(target.getUniqueId());
                UtilMessage.message(player, "Ignore", "You are no longer ignoring " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                ClientRepository.updateIgnore(ClientUtilities.getClient(player.getUniqueId()));
            } else {
                ClientUtilities.getClient(player).getIgnore().add(target.getUniqueId());
                UtilMessage.message(player, "Ignore", "You are now ignoring " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                ClientRepository.updateIgnore(ClientUtilities.getClient(player.getUniqueId()));
            }
        }
    }

    @Override
    public void help(Player player) {

    }
}
