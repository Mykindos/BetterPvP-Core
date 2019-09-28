package net.betterpvp.core.client.mysql;

import net.betterpvp.core.Core;
import net.betterpvp.core.database.LoadPriority;
import net.betterpvp.core.database.QueryFactory;
import net.betterpvp.core.database.Repository;
import org.bukkit.plugin.java.JavaPlugin;

public class SettingsRepository implements Repository<Core> {

    private static final String TABLE_NAME = "client_settings";

    public static String CREATE_CLIENT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            "  `UUID` varchar(255) NOT NULL," +
            "  `Name` varchar(255)," +
            "  `Value` int(100));";

    @Override
    public void initialize() {
        QueryFactory.runQuery(CREATE_CLIENT_TABLE);
    }

    @Override
    public void load(Core instance) {

    }

    @Override
    public LoadPriority getLoadPriority() {
        return LoadPriority.HIGHEST;
    }
}
