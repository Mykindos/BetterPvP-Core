package net.betterpvp.core.database;

import net.betterpvp.core.Core;
import org.bukkit.ChatColor;

public class Log implements Repository<Core> {

    public static final String TABLE_NAME = "log";
    public static String CREATE_LOG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " "
            + "(Time DATETIME, "
            + "Type VARCHAR(24), "
            + "Message VARCHAR(255), "
            + "SystemTime LONG); ";

    public static void debug(String module, String message) {
        System.out.println("[BetterPvP> " + module + "] " + message);
    }

    public static void write(String type, String message) {
        String query = "INSERT INTO " + TABLE_NAME + " (Time, Type, Message, SystemTime) VALUES "
                + "(NOW(), "
                + "'" + type + "', "
                + "'" + ChatColor.stripColor(message) + "', "
                + "'" + System.currentTimeMillis() + "') ";
        debug("Log", message);
        QueryFactory.runQuery(query);
    }

    @Override
    public void initialize() {
        QueryFactory.runQuery(CREATE_LOG_TABLE);

    }

    @Override
    public LoadPriority getLoadPriority() {

        return LoadPriority.HIGHEST;
    }

    @Override
    public void load(Core plugin) {


    }


}
