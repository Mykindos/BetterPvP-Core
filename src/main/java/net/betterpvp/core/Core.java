package net.betterpvp.core;


import net.betterpvp.core.client.listeners.ConnectionListener;
import net.betterpvp.core.command.CommandCenter;
import net.betterpvp.core.command.CommandManager;
import net.betterpvp.core.configs.ConfigManager;
import net.betterpvp.core.database.Connect;
import net.betterpvp.core.database.QueryFactory;
import net.betterpvp.core.framework.CoreLoadedEvent;
import net.betterpvp.core.framework.Updater;
import net.betterpvp.core.interfaces.MenuManager;
import net.betterpvp.core.punish.PunishManager;
import net.betterpvp.core.punish.listeners.GriefListener;
import net.betterpvp.core.utility.recharge.RechargeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;

import java.sql.Connection;

public class Core extends JavaPlugin {

    private ConfigManager config;
    private Updater updater;

    @Override
    public void onEnable() {
        config = new ConfigManager(this);
        updater = new Updater("Updater");

        new Connect(this);
        new QueryFactory(this);

        QueryFactory.loadRepositories("net.betterpvp.core", this);
        CommandManager.registerCommands("net.betterpvp.core", this);

        new PunishManager(this);
        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    updater.run();
                } catch (Exception e) {

                    System.out.println("Update Event threw an error!");

                }

            }

        }.runTaskTimer(this, 0, 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                RechargeManager.getInstance().handleCooldowns();
                PunishManager.handlePunishments();

            }
        }.runTaskTimerAsynchronously(this, 0L, 2L);

        registerListeners();

        new BukkitRunnable(){
            public void run(){
                Bukkit.getPluginManager().callEvent(new CoreLoadedEvent(Core.this));
            }
        }.runTaskLater(this, 20L);

    }

    @Override
    public void onDisable() {


    }

    public ConfigManager getConfigManager() {
        return config;
    }

    public static int test() {
        return 1;
    }

    public boolean hasStarted() {
        // TODO Auto-generated method stub
        return true;
    }

    private void registerListeners(){
        new ConnectionListener(this);
        new CommandCenter((this));
        new PunishManager(this);
        new GriefListener(this);
        new MenuManager(this);
    }
}
