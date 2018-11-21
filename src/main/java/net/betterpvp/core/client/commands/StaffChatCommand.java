package net.betterpvp.core.client.commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;

public class StaffChatCommand extends Command {

    public static Set<String> enabled = new HashSet<String>();

    public StaffChatCommand() {
        super("staffchat", new String[]{"sc"}, Rank.MODERATOR);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args == null || args.length == 0) {
         

            if (!enabled.contains(player.getName())) {
                UtilMessage.message(player, "Staff Chat", "Staff Chat: " + ChatColor.GREEN + "Enabled");
                enabled.add(player.getName());
            } else {
                UtilMessage.message(player, "Staff Chat", "Staff Chat: " + ChatColor.RED + "Disabled");
                enabled.remove(player.getName());
            }

         
            
            return;
        }

        if (args.length >= 1) {
           
          
            String msg = UtilMessage.getFinalArg(args, 0);
            ClientUtilities.messageStaff(ChatColor.RED + player.getName() + "> " + ChatColor.WHITE + msg, Rank.MODERATOR);
          
        }
    }

    @Override
    public void help(Player player) {

    }

}
