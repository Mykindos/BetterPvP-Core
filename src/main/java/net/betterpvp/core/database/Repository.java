package net.betterpvp.core.database;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public interface Repository<T extends JavaPlugin> {


    void initialize();
    
    void load(T instance);

    LoadPriority getLoadPriority();
}
