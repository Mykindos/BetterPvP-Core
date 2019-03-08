package net.betterpvp.core.database;

import org.bukkit.plugin.java.JavaPlugin;

public interface Repository<T extends JavaPlugin> {

    void initialize();

    void load(T instance);

    LoadPriority getLoadPriority();
}
