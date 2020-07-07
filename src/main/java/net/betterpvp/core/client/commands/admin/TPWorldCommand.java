package net.betterpvp.core.client.commands.admin;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TPWorldCommand extends Command {

    public TPWorldCommand() {
        super("tpworld", new String[]{}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args != null){
            World world = Bukkit.getWorld(args[0]);
            if(world != null){
                player.teleport(new Location(world, 0, 64, 0));
            }
        }
    }

    @Override
    public void help(Player player) {

    }
}
