package net.betterpvp.core.donation.ranks;

import net.betterpvp.core.donation.DonationExpiryTimes;
import net.betterpvp.core.donation.IDonation;

public class VIPRank implements IDonation {

    @Override
    public String getName() {
        return "VIP";
    }

    @Override
    public String getDisplayName() {
        return "VIP";
    }

    @Override
    public long getExpiryTime() {
        return DonationExpiryTimes.DAY * 31;
    }

}
