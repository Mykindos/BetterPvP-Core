package net.betterpvp.core.client.mysql;

import net.betterpvp.core.Core;
import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.database.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SettingsRepository implements Repository<Core> {

    private static final String TABLE_NAME = "client_settings";

    public static String CREATE_CLIENT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            "  `UUID` varchar(255) NOT NULL," +
            "  `Name` varchar(255)," +
            "  `Value` int(100)," +
            "UNIQUE KEY `uuid_name` (`UUID`,`Name`));";

    public static void saveSetting(UUID uuid, String setting, int value) {
        String query = "INSERT IGNORE INTO `" + TABLE_NAME + "` VALUES ('"
                + uuid.toString() + "','"
                + setting + "',"
                + value + ");";
        QueryFactory.runQuery(query);
    }

    public static void updateSetting(UUID uuid, String setting, int value) {
        String query = "UPDATE `" + TABLE_NAME + "` SET Value = "
                + value + " WHERE UUID ='" + uuid + "' AND Name ='" + setting + "'";

        QueryFactory.runQuery(query);
    }

    @Override
    public void initialize() {
        QueryFactory.runQuery(CREATE_CLIENT_TABLE);
    }

    @Override
    public void load(Core instance) {

    }

    public static void loadSettings(JavaPlugin instance, Client client) {
        BukkitRunnable loadRunnable = new BukkitRunnable() {

            @Override
            public void run() {
                int count = 0;
                try {
                    PreparedStatement statement = Connect.getConnection().prepareStatement("SELECT * FROM `client_settings` where UUID ='" + client.getUUID() + "'");
                    ResultSet result = statement.executeQuery();

                    while (result.next()) {
                        UUID uuid = UUID.fromString(result.getString(1));
                        String name = result.getString(2);
                        int value = result.getInt(3);


                        Client client = ClientUtilities.getOnlineClient(uuid);
                        if (client != null) {
                            client.getSettings().put(name, value);
                        }else{
                            System.out.println("Null client, failed to load settings");
                        }

                        count++;
                    }

                    statement.close();
                    result.close();

                    Log.debug("MySQL", "Loaded " + count + " settings");

                } catch (SQLException ex) {
                    Log.debug("Connection", "Could not load Clients (Connection Error), ");
                    ex.printStackTrace();
                }


            }
        };

        QueryFactory.requestData(loadRunnable);

    }

    @Override
    public LoadPriority getLoadPriority() {
        return LoadPriority.HIGHEST;
    }
}
