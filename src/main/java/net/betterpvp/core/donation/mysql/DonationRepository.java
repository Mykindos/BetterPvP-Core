package net.betterpvp.core.donation.mysql;

import net.betterpvp.core.Core;
import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.database.Connect;
import net.betterpvp.core.database.LoadPriority;
import net.betterpvp.core.database.QueryFactory;
import net.betterpvp.core.database.Repository;
import net.betterpvp.core.donation.Donation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DonationRepository implements Repository<Core> {

    private static final String TABLE_NAME = "donations";

    @Override
    public void initialize() {

    }

    @Override
    public void load(Core instance) {
        try {

            for (Client client : ClientUtilities.getClients()) {
                PreparedStatement statement = Connect.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME
                        + " WHERE UUID=" + client.getUUID());
                ResultSet result = statement.executeQuery();

                while (result.next()) {

                    String donation = result.getString(2);


                    Donation d = new Donation(donation);
                    client.getDonations().add(d);
                }

            }

            System.out.println("Finished loading donations");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveDonation(UUID uuid, String donation){
        String query = "INSERT IGNORE INTO " + TABLE_NAME + " VALUES('" + uuid.toString() + "', '" + donation + "');";
        QueryFactory.runQuery(query);
    }


    @Override
    public LoadPriority getLoadPriority() {
        return LoadPriority.HIGHEST;
    }
}
