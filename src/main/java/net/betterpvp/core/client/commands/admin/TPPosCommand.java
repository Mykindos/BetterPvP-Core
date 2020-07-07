package net.betterpvp.core.client.commands.admin;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TPPosCommand extends Command {

    public TPPosCommand() {
        super("tppos", new String[]{}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args != null) {
            if (args.length == 3) {
                World world = player.getWorld();
                double x = Double.valueOf(args[0]);
                double y = Double.valueOf(args[1]);
                double z = Double.valueOf(args[2]);
                player.teleport(new Location(world, x, y, z));

            } else if (args.length == 4) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    World world = target.getWorld();
                    double x = Double.valueOf(args[1]);
                    double y = Double.valueOf(args[2]);
                    double z = Double.valueOf(args[3]);
                    target.teleport(new Location(world, x, y, z));
                }
            }
        }
    }

    @Override
    public void help(Player player) {

    }
}
