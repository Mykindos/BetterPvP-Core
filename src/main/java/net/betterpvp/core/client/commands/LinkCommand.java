package net.betterpvp.core.client.commands;

import net.betterpvp.core.Core;
import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.client.mysql.ClientRepository;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.framework.BPVPListener;
import net.betterpvp.core.networking.NetworkReceiver;
import net.betterpvp.core.networking.events.NetworkMessageEvent;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.fancymessage.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;
import java.util.WeakHashMap;

public class LinkCommand extends Command implements Listener {

    private HashMap<UUID, String> pendingLinks;

    public LinkCommand() {
        super("link", new String[]{}, Rank.PLAYER);
        pendingLinks = new HashMap<>();
    }

    @EventHandler
    public void onNetworkMessage(NetworkMessageEvent e){
        if(e.getChannel().equals("Discord")){
            String[] data = e.getMessage().split("-!-");
            if(data[0].equals("Link")){
                Player player = Bukkit.getPlayer(data[1]);
                if(player != null){
                    new FancyMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Click ")
                            .then(ChatColor.RED.toString() + ChatColor.BOLD + "here").command("/link finish").then(ChatColor.YELLOW.toString() + ChatColor.BOLD + " to finish linking your discord account.").send(player);
                    pendingLinks.put(player.getUniqueId(), data[2]);
                }
            }
        }
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args == null){
            UtilMessage.message(player, "Discord", "To link your account, type " + ChatColor.GREEN
                    + "!link " + player.getName() + ChatColor.GRAY + " in the BetterPvP Discord! https://discord.gg/qPZTbvx");
        }else{
            if(args.length == 1) {
                if(pendingLinks.containsKey(player.getUniqueId())) {
                    if (args[0].equals("finish")) {
                        NetworkReceiver.sendNetworkMessage("network.betterpvp.net", 1335, "Discord",
                                "FinishLink-!-" + pendingLinks.get(player.getUniqueId()) + "-!-" + player.getUniqueId().toString() + "-!-" + player.getName());
                        UtilMessage.message(player, "Discord", "Successfully linked your account. Enjoy the benefits!");
                        pendingLinks.remove(player.getUniqueId());

                        Client client = ClientUtilities.getOnlineClient(player);
                        if(client != null){
                            client.setDiscordLinked(true);
                            ClientRepository.updateDiscordLink(client);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void help(Player player) {

    }
}
