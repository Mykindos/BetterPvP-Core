package net.betterpvp.core.donation;

import java.util.UUID;

public class Donation {


    private String name;
    private long expiry;
    private boolean claimed;

    public Donation(String name, long expiry, boolean claimed){
        this.name = name;
        this.expiry = expiry;
        this.claimed = claimed;

    }

    public String getName(){
        return name;
    }

    public long getExpiry() {
        return expiry;
    }

    public boolean isClaimed() {
        return claimed;
    }
}
