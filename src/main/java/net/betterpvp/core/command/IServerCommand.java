package net.betterpvp.core.command;

import org.bukkit.command.CommandSender;

public interface IServerCommand {

    void execute(CommandSender sender, String[] args);
}
