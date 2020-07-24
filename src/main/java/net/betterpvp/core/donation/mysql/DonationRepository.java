package net.betterpvp.core.donation.mysql;

import net.betterpvp.core.Core;
import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.database.Connect;
import net.betterpvp.core.database.LoadPriority;
import net.betterpvp.core.database.QueryFactory;
import net.betterpvp.core.database.Repository;
import net.betterpvp.core.donation.Donation;
import net.betterpvp.core.utility.UtilFormat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DonationRepository implements Repository<Core> {

    private static final String TABLE_NAME = "donations";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `" + TABLE_NAME + "`("
            + "id int auto_increment primary key,"
            + "UUID varchar(255) not null,"
            + "Name varchar(255) not null,"
            + "Expiry long null,"
            + "Claimed tinyint(1) default 0 null)";


    @Override
    public void initialize() {
        QueryFactory.runQuery(CREATE_TABLE);
    }

    @Override
    public void load(Core instance) {
        try {

            for (Client client : ClientUtilities.getClients()) {
                PreparedStatement statement = Connect.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME
                        + " WHERE UUID='" + client.getUUID() + "'");
                ResultSet result = statement.executeQuery();

                while (result.next()) {

                    String donation = result.getString(2);
                    long expiry = result.getLong(3);
                    boolean claimed = result.getBoolean(4);
                    long timestamp = result.getLong(5);

                    Donation d = new Donation(donation, expiry, claimed, timestamp);
                    client.getDonations().add(d);
                }

            }

            System.out.println("Finished loading donations");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void saveDonation(UUID uuid, Donation donation) {
        String query = "INSERT IGNORE INTO " + TABLE_NAME + "(UUID, Name, Expiry, Claimed, Timestamp) VALUES('" + uuid.toString()
                + "', '" + donation.getName() + "', '" + donation.getExpiry() + "', '" + UtilFormat.toTinyInt(donation.isClaimed()) + "'," + donation.getTimestamp() + ");";
        QueryFactory.runQuery(query);
    }

    public static void removeDonation(UUID uuid, String perk) {
        String query = "DELETE FROM `" + TABLE_NAME + "` WHERE UUID='" + uuid.toString() + "' AND Name='" + perk + "'";
        QueryFactory.runQuery(query);
    }

    public static void setClaimed(UUID uuid, String perk) {
        String query = "UPDATE `" + TABLE_NAME + "` SET Claimed = 1 WHERE Claimed = 0 AND Name ='" + perk
                + "' AND UUID = '" + uuid.toString() + "' ORDER BY Timestamp ASC LIMIT 1";
        QueryFactory.runQuery(query);
    }

    @Override
    public LoadPriority getLoadPriority() {
        return LoadPriority.HIGHEST;
    }
}
