package net.betterpvp.core.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

public class CommandManager {

	public static List<Command> commands = new ArrayList<Command>();



	public static Command getCommand(String commandLable) {
		for (Command command : commands) {
			if (command.getCommandLable().equalsIgnoreCase(commandLable)) {
				return command;
			}

			for (String allias : command.getAliases()) {
				if (commandLable.equalsIgnoreCase(allias)) {
					return command;
				}
			}
		}
		return null;
	}

	public static void addCommand(Command command) {
		commands.add(command);
	}

	public static void registerCommands(String packageName, Plugin instance) {
		int count = 0;
		Reflections reflections = new Reflections(packageName);
		Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);
		for(Class<? extends Command> c : classes) {
			try {
				if(Listener.class.isAssignableFrom(c)) {
					Command command = c.newInstance();
					Bukkit.getPluginManager().registerEvents((Listener) command, instance);
					System.out.println("Registered command + event listener");
					
				}else {
					CommandManager.addCommand(c.newInstance());
				}
				count++;
				
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Registered " + count + " commands for " + packageName);

	}
}
