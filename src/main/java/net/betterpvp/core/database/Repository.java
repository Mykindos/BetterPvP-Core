package net.betterpvp.core.database;

import org.bukkit.plugin.java.JavaPlugin;

public interface Repository<T extends JavaPlugin> {

    void initialize();

    /**
     * Repositories that other repositories are dependent on should be loaded on the server thread.
     * All other repositories can be ran asynchronously.
     * @param instance
     */
    void load(T instance);

    LoadPriority getLoadPriority();

}
