package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class HealCommand extends Command {

    public HealCommand() {
        super("heal", new String[]{}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args == null){
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }else{
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null){
                target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            }
        }
    }

    @Override
    public void help(Player player) {

    }
}
