package net.betterpvp.core.command;

import org.bukkit.command.CommandSender;

public interface IServerCommand {

    void serverCmdExecute(CommandSender sender, String[] args);
}
