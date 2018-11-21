package net.betterpvp.core.database;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.betterpvp.core.Core;

public class Log implements Repository<Core> {

	public static final String TABLE_NAME = "kitmap_log";
    public static String CREATE_LOG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " "
            + "(Time DATETIME, "
            + "Type VARCHAR(24), "
            + "Message VARCHAR(256), "
            + "SystemTime LONG); ";

    public static void debug(String module, String message) {
        System.out.println("[BattleAU> " + module + "] " + message);
    }

    public static void write(String type, String message) {
        String query = "INSERT INTO " + TABLE_NAME + " (Time, Type, Message, SystemTime) VALUES "
                + "(NOW(), "
                + "'" + type + "', "
                + "'" + message + "', "
                + "'" + System.currentTimeMillis() + "') ";
        debug("Log", message);
        new Query(query);
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
