package net.betterpvp.core.client.commands.admin;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TPCommand extends Command {

    public TPCommand() {
        super("tp", new String[]{}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 1) {
            Player p = Bukkit.getPlayer(args[0]);
            if (p != null) {
                player.teleport(p.getLocation());
            }
        }else if(args.length == 2){
            Player p1 = Bukkit.getPlayer(args[0]);
            Player p2 = Bukkit.getPlayer(args[1]);

            if(p1 != null && p2 != null){
                p1.teleport(p2);
            }
        }

    }

    @Override
    public void help(Player player) {
        // TODO Auto-generated method stub

    }
}
