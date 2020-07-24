package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.client.mysql.ClientRepository;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.networking.NetworkReceiver;
import org.bukkit.entity.Player;

public class UnlinkCommand extends Command {
    public UnlinkCommand() {
        super("unlink", new String[]{}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args != null){
            Client client = ClientUtilities.getClient(args[0]);
            if(client != null){
               if(client.isDiscordLinked()){
                   client.setDiscordLinked(false);
                   ClientRepository.updateDiscordLink(client);

                   NetworkReceiver.sendNetworkMessage("network.betterpvp.net", 1335, "Discord", "Unlink-!-" + client.getUUID().toString());
               }
            }
        }
    }

    @Override
    public void help(Player player) {

    }
}
