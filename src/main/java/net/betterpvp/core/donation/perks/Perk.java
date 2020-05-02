package net.betterpvp.core.donation.perks;

import java.util.stream.Stream;

public enum Perk {

    RESERVEDSLOT("Reserved Slot", ExpiryTimes.NONE),
    SUPERTOOLS("Super Tools", ExpiryTimes.DAY * 60),
    RAVEARMOUR("Rave Armour", ExpiryTimes.NONE);

    private String name;
    private long expiry;

    Perk(String name, long expiry) {
        this.name = name;
        this.expiry = expiry;
    }

    public String getName() {
        return name;
    }

    public long getExpiry() {
        return expiry;
    }

    public static boolean isPerk(String s) {
        return Stream.of(Perk.values()).anyMatch(p -> p.getName().equalsIgnoreCase(s));
    }


    static final class ExpiryTimes {

        static final long NONE = 0;
        static final long DAY = 86400000;

    }
}
