package net.betterpvp.core.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import net.betterpvp.core.client.Rank;

public abstract class Command {

    private String commandLable;
    private String[] aliases;
    private Rank requiredRank;

 
    
    public Command(String commandLable, String[] aliases, Rank requiredRank) {
        this.commandLable = commandLable;
        this.requiredRank = requiredRank;
        this.aliases = aliases;
      
    }

    public String getCommandLable() {
        return this.commandLable;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public Rank getRequiredRank() {
        return this.requiredRank;
    }

    

    public abstract void execute(Player player, String[] args);
    public abstract void help(Player player);
}
