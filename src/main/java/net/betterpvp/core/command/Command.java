package net.betterpvp.core.command;

import net.betterpvp.core.client.Rank;
import org.bukkit.entity.Player;

public abstract class Command {

    private String commandLabel;
    private String[] aliases;
    private Rank requiredRank;


    public Command(String commandLabel, String[] aliases, Rank requiredRank) {
        this.commandLabel = commandLabel;
        this.requiredRank = requiredRank;
        this.aliases = aliases;

    }

    public String getCommandLabel() {
        return this.commandLabel;
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
