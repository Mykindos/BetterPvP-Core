package net.betterpvp.core.punish.commands;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.database.Log;
import net.betterpvp.core.punish.Punish;
import net.betterpvp.core.punish.Punish.PunishType;
import net.betterpvp.core.punish.PunishManager;
import net.betterpvp.core.punish.mysql.PunishRepository;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UnmuteCommand extends Command {

    public UnmuteCommand() {
        super("unmute", new String[]{}, Rank.MODERATOR);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            UtilMessage.message(player, "Punish", "You did not input a Client to unban.");
            return;
        }

        if (args.length == 1) {
            Client target = ClientUtilities.searchClient(player, args[0], false);
            if (target == null) {
                ClientUtilities.searchClient(player, args[0], true);
                return;
            }

            Punish punish = PunishManager.getPunish(target.getUUID(), PunishType.MUTE);
            if (punish == null) {
                UtilMessage.message(player, "Punish", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " has not been Punished.");
                return;
            }

            if (!PunishManager.isMuted(target.getUUID())) {
                UtilMessage.message(player, "Punish", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " is not muted.");
                return;
            }

            PunishRepository.removePunishment(punish);
            PunishManager.removePunishment(punish);
            UtilMessage.broadcast("Punish", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " unmuted " + ChatColor.YELLOW + target.getName()
                    + ChatColor.GRAY + ".");
            Log.write("Punish", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " unmuted " + ChatColor.YELLOW + target.getName()
                    + ChatColor.GRAY + ".");


        }
    }

    @Override
    public void help(Player player) {
        UtilMessage.message(player, "Client", "Punish Commands List:");
        UtilMessage.message(player, "/ban help", "Recommended punish times", Rank.MODERATOR);
        UtilMessage.message(player, "/ban <player> <time> <reason>", "Ban a player.", Rank.MODERATOR);
        UtilMessage.message(player, "/mute <player> <time> <reason>", "Mute a player.", Rank.MODERATOR);
        UtilMessage.message(player, "/unban <player>", "Unban a player.", Rank.MODERATOR);
        UtilMessage.message(player, "/unmute <player>", "Unmute a player.", Rank.MODERATOR);
    }

}
