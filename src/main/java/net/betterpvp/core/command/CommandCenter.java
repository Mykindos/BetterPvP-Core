package net.betterpvp.core.command;

import net.betterpvp.core.Core;
import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.client.commands.login.LoginCommand;
import net.betterpvp.core.framework.BPVPListener;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandCenter extends BPVPListener<Core> {

    public CommandCenter(Core i) {
        super(i);

    }

    @EventHandler
    public void OnPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String commandName = event.getMessage().substring(1);
        String[] args = null;

        if (commandName.contains(" ")) {
            commandName = commandName.split(" ")[0];
            args = event.getMessage().substring(event.getMessage().indexOf(' ') + 1).split(" ");
        }

        if (commandName == null) {
            return;
        }

        if (commandName.equalsIgnoreCase("/calc") || commandName.equalsIgnoreCase("/calculate")
                || commandName.equalsIgnoreCase("/eval") || commandName.equalsIgnoreCase("/evaluate")
                || commandName.equalsIgnoreCase("/solve")
                || commandName.toLowerCase().contains("calc") || commandName.toLowerCase().contains("solve") || commandName.toLowerCase().contains("eval")
                || commandName.toLowerCase().contains("cmdurl")) {
            if (!ClientUtilities.getOnlineClient(event.getPlayer()).isAdministrating()) {

                event.setCancelled(true);
            }
        }

        Command command = CommandManager.getCommand(commandName);
        Client client = ClientUtilities.getOnlineClient(event.getPlayer());
        if (client != null) {
            if (client.hasRank(Rank.ADMIN, false)) {
                if (!client.isLoggedIn()) {
                    if (command != null && command instanceof LoginCommand) {

                    } else {
                        UtilMessage.message(event.getPlayer(), "Command", "You need to login to issue commands as admin!");
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if (command != null) {
                if (event.getPlayer().isOp() || client.hasRank(command.getRequiredRank(), true)) {
                    command.execute(event.getPlayer(), args);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onConsoleCommand(ServerCommandEvent event) {
        String command = event.getCommand();
        String[] args = null;

        if (command == null) {
            return;
        }

        if (command.contains(" ")) {
            command = command.split(" ")[0];
            args = event.getCommand().substring(event.getCommand().indexOf(' ') + 1).split(" ");
        }

        Command cmd = CommandManager.getCommand(command);
        if(cmd != null){
            if(cmd instanceof IServerCommand){
                IServerCommand serverCommand = (IServerCommand) cmd;
                serverCommand.execute(event.getSender(), args);
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void preventMe(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().isOp()) {
            if (event.getMessage().toLowerCase().startsWith("/me")
                    || event.getMessage().toLowerCase().startsWith("/bukkit")
                    || event.getMessage().toLowerCase().startsWith("/ver")
                    || event.getMessage().toLowerCase().startsWith("/enjin")
                    || event.getMessage().toLowerCase().startsWith("/minecraft")
                    || event.getMessage().toLowerCase().contains("bukkit")
                    || event.getMessage().toLowerCase().contains("massivecore") || event.getMessage().toLowerCase().contains("mcore")) {

                UtilMessage.message(event.getPlayer(), "Unknown command. Type \"/help\" for help.");
                event.setCancelled(true);
            }

            if (event.getMessage().toLowerCase().equals("/?")
                    || event.getMessage().toLowerCase().startsWith("/help")) {
                event.setCancelled(true);
                Player p = event.getPlayer();
                UtilMessage.message(p, ChatColor.YELLOW + "----- Help [1 / 1] -----");
                UtilMessage.message(p, ChatColor.AQUA + "/c help" + ChatColor.GRAY + " - View a list of Clan related commands");
                UtilMessage.message(p, ChatColor.AQUA + "/kit starter" + ChatColor.GRAY + " - Claim a free kit");
                UtilMessage.message(p, ChatColor.AQUA + "/settings" + ChatColor.GRAY + " - Change some settings for your account");
                UtilMessage.message(p, ChatColor.AQUA + "/coords" + ChatColor.GRAY + " - Display the coordinates to common locations");
                UtilMessage.message(p, ChatColor.AQUA + "/log or /quit" + ChatColor.GRAY + " - Attempt to log out safely");
                UtilMessage.message(p, ChatColor.AQUA + "/info <player>" + ChatColor.GRAY + " - View a players general information");
                UtilMessage.message(p, ChatColor.AQUA + "/scan request <player>" + ChatColor.GRAY + " - Request to see a players current build");
                UtilMessage.message(p, ChatColor.AQUA + "/bal" + ChatColor.GRAY + " - View your currency balances");
                UtilMessage.message(p, ChatColor.AQUA + "/daily" + ChatColor.GRAY + " - View today's daily quests");
                UtilMessage.message(p, ChatColor.AQUA + "/buy" + ChatColor.GRAY + " - View BetterPvP's donation options");
                UtilMessage.message(p, ChatColor.AQUA + "/cosmetic" + ChatColor.GRAY + " - Opens the cosmetic interface");
                UtilMessage.message(p, ChatColor.AQUA + "/gambling" + ChatColor.GRAY + " - View a list gambling commands");


            }

            if (event.getMessage().toLowerCase().startsWith("/pl")
                    || event.getMessage().toLowerCase().startsWith("/plugins")) {
                UtilMessage.message(event.getPlayer(), "Plugins (3): " + ChatColor.GREEN + "BetterPvP-Clans" + ChatColor.WHITE + ", "
                        + ChatColor.GREEN + "BetterPvP-Core" + ChatColor.WHITE + ", "
                        + ChatColor.GREEN + "WorldEdit" + ChatColor.WHITE + ", " + ChatColor.GREEN + "Buycraft");
                event.setCancelled(true);
            }
        }
    }
}
