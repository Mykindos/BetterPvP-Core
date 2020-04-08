package net.betterpvp.core.client.commands.admin;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetResoucePackCommand extends Command {
    public SetResoucePackCommand() {
        super("setresourcepack", new String[]{}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args != null){
            if(args.length == 1){
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.setResourcePack(args[0]);
                }
            }
        }
    }

    @Override
    public void help(Player player) {

    }
}
