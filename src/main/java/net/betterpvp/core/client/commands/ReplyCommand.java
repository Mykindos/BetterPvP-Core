package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.punish.PunishManager;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReplyCommand extends Command {

    public ReplyCommand() {
        super("r", new String[]{"reply"}, Rank.PLAYER);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args == null || args.length == 0) {
            UtilMessage.message(player, "Message", "Message argument missing.");
            return;
        }

        if (!MessageCommand.messages.containsKey(player.getName())) {
            UtilMessage.message(player, "Message", "You have no one to reply to.");
            return;
        }

        if (PunishManager.isMuted(player.getUniqueId())) {
            UtilMessage.message(player, "Punish", "You cannot private message while muted!");
            return;
        }

        Player target = Bukkit.getPlayer(MessageCommand.messages.get(player.getName()));
        if (target == null) {
            UtilMessage.message(player, "Message", "You have no one to reply to.");
            return;
        }

        if (args.length >= 1) {
            String msg = UtilMessage.getFinalArg(args, 0);

            for (Player online : Bukkit.getOnlinePlayers()) {
                if (ClientUtilities.getClient(online).isAdministrating()) {
                    if (player.equals(online) || online.equals(player)) {
                        continue;
                    }

                    UtilMessage.message(online, ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + player.getName() + ChatColor.DARK_GREEN + " -> " + ChatColor.GREEN + target.getName() + ChatColor.DARK_GREEN + "] " + ChatColor.GRAY + msg);
                }
            }

            UtilMessage.message(player, ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "You" + ChatColor.DARK_AQUA + " -> " + ChatColor.AQUA + target.getName() + ChatColor.DARK_AQUA + "] " + ChatColor.GRAY + msg);
            UtilMessage.message(target, ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + player.getName() + ChatColor.DARK_AQUA + " -> " + ChatColor.AQUA + "You" + ChatColor.DARK_AQUA + "] " + ChatColor.GRAY + msg);
            MessageCommand.messages.put(target.getName(), player.getName());
        }
    }

    @Override
    public void help(Player player) {

    }
}
