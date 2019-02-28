package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.punish.PunishManager;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.recharge.RechargeManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class AdminChatCommand extends Command {

    public AdminChatCommand() {
        super("adminchat", new String[]{"a"}, Rank.PLAYER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args != null) {
            if (ClientUtilities.isStaffOnline(Rank.ADMIN)) {

                if (PunishManager.isMuted(player.getUniqueId())) {
                    if (!RechargeManager.getInstance().add(player, "Admin Chat", 600, true, false)) {

                        return;
                    }
                }
                String message = "";
                for (String s : args) {
                    message += s + " ";
                }
                UtilMessage.message(player, ChatColor.GOLD.toString() + ChatColor.BOLD + player.getName() + "> " + ChatColor.YELLOW.toString() + ChatColor.BOLD + message);
                ClientUtilities.messageStaffSound(ChatColor.GOLD.toString() + ChatColor.BOLD + player.getName(),
                        ChatColor.YELLOW.toString() + ChatColor.BOLD + message, Sound.LEVEL_UP, Rank.ADMIN);
            } else {
                UtilMessage.message(player, "Admin Chat", "There is currently no admins online to receive this message!");
            }
        } else {
            UtilMessage.message(player, "Admin Chat", "Correct Usage: /a <message>");
        }
    }

    @Override
    public void help(Player player) {
    }

}
