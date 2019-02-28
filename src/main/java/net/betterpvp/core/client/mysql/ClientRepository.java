package net.betterpvp.core.client.mysql;

import net.betterpvp.core.Core;
import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.DonationRank;
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

    public static String CREATE_CLIENT_TABLE = "CREATE TABLE IF NOT EXISTS clients "
            + "(UUID VARCHAR(64), "
            + "Name VARCHAR(16), "
            + "IP VARCHAR(64), "
            + "Rank VARCHAR(64), "
            + "Ignored BLOB,"
            + "PreviousName VARCHAR(255),"
            + "DonationRank VARCHAR(64),"
            + "MAH tinyint(1),"
            + "Bypass tinyint(1),"
            + "LastLogin bigint(255),"
            + "Password VARCHAR(255)); ";

    public static void saveClient(Client client) {
        String query = "INSERT IGNORE INTO clients VALUES "
                + "('" + client.getUUID().toString() + "', "
                + "'" + client.getName() + "', "
                + "'" + client.getIP() + "', "
                + "'" + client.getRank().toString() + "', "
                + "'" + UtilFormat.toString(client.getIgnore()) + "', "
                + "'" + "" + "', "
                + "'" + "" + "', "
                + "'" + System.currentTimeMillis() + "', "
                + "'" + "" + "', "
                + "'" + 0 + "', "
                + "'" + "" + "', "
                + "'" + 0 + "')";

        System.out.println(query);
        Log.write("Clans", "Saved Client [" + client.getName() + "]");
        new Query(query);
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


                        List<UUID> ignore = new ArrayList<UUID>();
                        if (!ignoreString.isEmpty()) {
                            String[] ignoreArray = ignoreString.split(", ");
                            for (int i = 0; i < ignoreArray.length; i++) {
                                ignore.add(UUID.fromString(ignoreArray[i]));
                            }
                        }

                        String password = result.getString(9);
                        int timePlayed = result.getInt(10);
                        String discordID = result.getString(11);
                        boolean kitRedeemed = result.getBoolean(12);


                        Client client = new Client(uuid);
                        client.setName(name);
                        client.setIP(ip);
                        client.setRank(rank);

                        client.setPassword(password);
                        client.setTimePlayed(timePlayed);
                        if (result.getString(7) != null && !result.getString(7).equals("")) {
                            DonationRank donationRank = DonationRank.valueOf(result.getString(7));
                            client.setDonationRank(donationRank);
                        }
                        client.setIgnore(ignore);
                        client.setOldName(oldName);
                        client.setDiscordID(discordID);
                        client.setKitRedeemed(kitRedeemed);
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


                        List<UUID> ignore = new ArrayList<UUID>();
                        if (!ignoreString.isEmpty()) {
                            String[] ignoreArray = ignoreString.split(", ");
                            for (int i = 0; i < ignoreArray.length; i++) {
                                ignore.add(UUID.fromString(ignoreArray[i]));
                            }
                        }

                        String password = result.getString(9);
                        int timePlayed = result.getInt(10);
                        String discordID = result.getString(11);
                        boolean kitRedeemed = result.getBoolean(12);

                        Client client = ClientUtilities.getOnlineClient(uuid);
                        if (client != null) {
                            client.setName(name);
                            client.setIP(ip);
                            client.setRank(rank);

                            client.setPassword(password);
                            client.setTimePlayed(timePlayed);
                            if (result.getString(7) != null && !result.getString(7).equals("")) {
                                DonationRank donationRank = DonationRank.valueOf(result.getString(7));
                                client.setDonationRank(donationRank);
                            }
                            client.setIgnore(ignore);
                            client.setOldName(oldName);
                            client.setDiscordID(discordID);
                            client.setKitRedeemed(kitRedeemed);
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
        new Query(query);
    }


    public static void updatePreviousName(Player client) {
        String query = "UPDATE clients SET PreviousName='" + client.getName() + "' WHERE UUID='" + client.getUniqueId() + "'";
        new Query(query);
    }

    public static void updateName(Player client) {
        String query = "UPDATE clients SET Name='" + client.getName() + "' WHERE UUID='" + client.getUniqueId() + "'";
        new Query(query);
    }

    public static void updateIP(Player client) {
        Client c = ClientUtilities.getClient(client);
        String query = "UPDATE clients SET IP='" + c.getIP() + "' WHERE UUID='" + client.getUniqueId() + "'";
        new Query(query);
    }

    public static void updateIgnore(Client client) {
        String query = "UPDATE clients SET Ignored='" + UtilFormat.toString(client.getIgnore()) + "' WHERE UUID='" + client.getUUID() + "'";
        new Query(query);
    }


    public static void flagMAH(Client client) {
        String query = "INSERT INTO mahflag (IP) VALUES ('" + client.getIP() + "');";
        new Query(query);
    }


    public static void updateTimePlayed(Client client) {
        String query = "UPDATE clients SET TimePlayed='" + client.getTimePlayed() + "' WHERE UUID='" + client.getUUID().toString() + "'";
        new Query(query);
    }

    public static void updateDiscord(Client client) {
        String query = "UPDATE clients SET DiscordID='" + client.getDiscordID() + "' WHERE UUID='" + client.getUUID().toString() + "'";
        new Query(query);
    }


    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }


    @Override
    public LoadPriority getLoadPriority() {
        // TODO Auto-generated method stub
        return LoadPriority.LOWEST;
    }


}
