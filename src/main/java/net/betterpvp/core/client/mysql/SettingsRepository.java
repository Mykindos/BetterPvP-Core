package net.betterpvp.core.client.mysql;

import net.betterpvp.core.Core;
import net.betterpvp.core.database.LoadPriority;
import net.betterpvp.core.database.QueryFactory;
import net.betterpvp.core.database.Repository;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class SettingsRepository implements Repository<Core> {

    private static final String TABLE_NAME = "client_settings";

    public static String CREATE_CLIENT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            "  `UUID` varchar(255) NOT NULL," +
            "  `Name` varchar(255)," +
            "  `Value` int(100));";

    public static void saveSetting(UUID uuid, String setting, int value){
        String query = "INSERT INTO `" + TABLE_NAME + "` VALUES ('"
                + uuid.toString() + "','"
                + setting + "',"
                + value + ");";
        QueryFactory.runQuery(query);
    }

    public static void updateSetting(UUID uuid, String setting, int value){
        String query = "UPDATE `" + TABLE_NAME + "` SET Value ="
                + value + " WHERE UUID ='" + uuid + "' AND settings='" + setting + "'";

        QueryFactory.runQuery(query);
    }

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
