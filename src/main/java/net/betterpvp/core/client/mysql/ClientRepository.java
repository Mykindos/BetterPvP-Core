package net.betterpvp.core.client.mysql;

import net.betterpvp.core.Core;
import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.database.*;
import net.betterpvp.core.utility.UtilFormat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientRepository implements Repository<Core> {

    public static String CREATE_CLIENT_TABLE = "CREATE TABLE IF NOT EXIST `clients`  (" +
            "  `UUID` varchar(255) NOT NULL," +
            "  `Name` varchar(255)," +
            "  `IP` varchar(255)," +
            "  `Rank` varchar(255)," +
            "  `Ignored` blob NULL," +
            "  `PreviousName` varchar(255)," +
            "  `LastLogin` bigint(255)," +
            "  `TimePlayed` bigint(255)," +
            "  `Password` varchar(255)," +
            "  PRIMARY KEY (`UUID`)" +
            ");";

    @Override
    public void initialize() {
        QueryFactory.runQuery(CREATE_CLIENT_TABLE);
    }


    public static void saveClient(Client client) {
        String query = "INSERT IGNORE INTO clients VALUES "
                + "('" + client.getUUID().toString() + "', "
                + "'" + client.getName() + "', "
                + "'" + client.getIP() + "', "
                + "'" + client.getRank().toString() + "', "
                + "'" + UtilFormat.toString(client.getIgnore()) + "', "
                + "'" + "" + "', "
                + "'" + System.currentTimeMillis() + "', "
                + "'" + 0 + "', "
                + "'" + "" + "')";

        System.out.println(query);
        Log.write("Clans", "Saved Client [" + client.getName() + "]");
        QueryFactory.runQuery(query);
    }

    @Override
    public void load(Core plugin) {
        new BukkitRunnable() {

            @Override
            public void run() {
                int count = 0;
                try {
                    PreparedStatement statement = Connect.getConnection().prepareStatement("SELECT * FROM `clients`");
                    ResultSet result = statement.executeQuery();

                    while (result.next()) {
                        UUID uuid = UUID.fromString(result.getString(1));
                        String name = result.getString(2);
                        String ip = result.getString(3);
                        Rank rank = Rank.valueOf(result.getString(4));

                        String ignoreString = result.getString(5);
                        String oldName = "" + result.getString(6);


                        List<UUID> ignore = new ArrayList<>();
                        if (!ignoreString.isEmpty()) {
                            String[] ignoreArray = ignoreString.split(", ");
                            for (int i = 0; i < ignoreArray.length; i++) {
                                ignore.add(UUID.fromString(ignoreArray[i]));
                            }
                        }

                        long lastLogin = result.getLong(7);
                        int timePlayed = result.getInt(8);
                        String password = result.getString(9);



                        Client client = new Client(uuid);
                        client.setName(name);
                        client.setIP(ip);
                        client.setRank(rank);

                        client.setLastLogin(lastLogin);
                        client.setPassword(password);
                        client.setTimePlayed(timePlayed);

                        client.setIgnore(ignore);
                        client.setOldName(oldName);

                        ClientUtilities.addClientOnLoad(client);
                        count++;
                    }

                    statement.close();
                    result.close();

                    Log.debug("MySQL", "Loaded " + count + " Clients");

                } catch (SQLException ex) {
                    Log.debug("Connection", "Could not load Clients (Connection Error), ");
                    ex.printStackTrace();
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    Client client = ClientUtilities.getClient(p);
                    ClientUtilities.addOnlineClient(client);
                }

            }
        }.runTaskAsynchronously(plugin);

    }

    public static void loadClient(final Core i, Player p) {
        new BukkitRunnable() {

            @Override
            public void run() {
                int count = 0;
                try {
                    PreparedStatement statement = Connect.getConnection().prepareStatement("SELECT * FROM `clients` where UUID ='" + p.getUniqueId().toString() + "'");
                    ResultSet result = statement.executeQuery();

                    while (result.next()) {
                        UUID uuid = UUID.fromString(result.getString(1));
                        String name = result.getString(2);
                        String ip = result.getString(3);
                        Rank rank = Rank.valueOf(result.getString(4));

                        String ignoreString = result.getString(5);
                        String oldName = "" + result.getString(6);


                        List<UUID> ignore = new ArrayList<>();
                        if (!ignoreString.isEmpty()) {
                            String[] ignoreArray = ignoreString.split(", ");
                            for (int i = 0; i < ignoreArray.length; i++) {
                                ignore.add(UUID.fromString(ignoreArray[i]));
                            }
                        }

                        long lastLogin = result.getLong(7);
                        String password = result.getString(8);
                        int timePlayed = result.getInt(9);


                        Client client = ClientUtilities.getOnlineClient(uuid);
                        if (client != null) {
                            client.setName(name);
                            client.setIP(ip);
                            client.setRank(rank);
                            client.setIgnore(ignore);
                            client.setOldName(oldName);

                            client.setLastLogin(lastLogin);
                            client.setPassword(password);
                            client.setTimePlayed(timePlayed);


                        }

                        count++;
                    }

                    statement.close();
                    result.close();

                    Log.debug("MySQL", "Loaded " + count + " Clients");

                } catch (SQLException ex) {
                    Log.debug("Connection", "Could not load Clients (Connection Error), ");
                    ex.printStackTrace();
                }


            }
        }.runTaskAsynchronously(i);

    }


    public static void updateRank(Client client) {
        String query = "UPDATE clients SET Rank='" + client.getRank().toString() + "' WHERE UUID='" + client.getUUID() + "'";
        QueryFactory.runQuery(query);
    }


    public static void updatePreviousName(Player client) {
        String query = "UPDATE clients SET PreviousName='" + client.getName() + "' WHERE UUID='" + client.getUniqueId() + "'";
        QueryFactory.runQuery(query);
    }

    public static void updateName(Player client) {
        String query = "UPDATE clients SET Name='" + client.getName() + "' WHERE UUID='" + client.getUniqueId() + "'";
        QueryFactory.runQuery(query);
    }

    public static void updateIP(Player client) {
        Client c = ClientUtilities.getClient(client);
        String query = "UPDATE clients SET IP='" + c.getIP() + "' WHERE UUID='" + client.getUniqueId() + "'";
        QueryFactory.runQuery(query);
    }

    public static void updateIgnore(Client client) {
        String query = "UPDATE clients SET Ignored='" + UtilFormat.toString(client.getIgnore()) + "' WHERE UUID='" + client.getUUID() + "'";
        QueryFactory.runQuery(query);
    }


    public static void flagMAH(Client client) {
        String query = "INSERT INTO mahflag (IP) VALUES ('" + client.getIP() + "');";
        QueryFactory.runQuery(query);
    }


    public static void updateTimePlayed(Client client) {
        String query = "UPDATE clients SET TimePlayed='" + client.getTimePlayed() + "' WHERE UUID='" + client.getUUID().toString() + "'";
        QueryFactory.runQuery(query);
    }

    public static void updateDiscord(Client client) {
        String query = "UPDATE clients SET DiscordID='" + client.getDiscordID() + "' WHERE UUID='" + client.getUUID().toString() + "'";
        QueryFactory.runQuery(query);
    }



    @Override
    public LoadPriority getLoadPriority() {
        return LoadPriority.LOWEST;
    }


}
