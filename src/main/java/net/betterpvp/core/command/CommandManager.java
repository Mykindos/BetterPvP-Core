package net.betterpvp.core.command;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CommandManager {

    public static List<Command> commands = new ArrayList<Command>();


    public static Command getCommand(String commandLabel) {


        return commands.stream().filter(c -> c.getCommandLable().equalsIgnoreCase(commandLabel)
                || Arrays.stream(c.getAliases()).filter(a -> a.equalsIgnoreCase(commandLabel)).findAny().isPresent())
                .findFirst().orElse(null);

    }

    public static void addCommand(Command command) {
        commands.add(command);
    }

    /**
     * Register all commands within a specific package
     *
     * @param packageName
     * @param instance
     */
    public static void registerCommands(String packageName, Plugin instance) {
        int count = 0;
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> c : classes) {
            try {


                if (Listener.class.isAssignableFrom(c)) {
                    Command command = c.newInstance();
                    Bukkit.getPluginManager().registerEvents((Listener) command, instance);
                    CommandManager.addCommand(command);
                    System.out.println("Registered command + event listener");

                } else {
                    if(c.getConstructors()[0].getParameterCount() > 0){
                        System.out.println("Skipped Command (Requires arguments): " + c.getName());
                        continue;
                    }

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
