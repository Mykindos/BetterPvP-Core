package net.betterpvp.core.donation.events;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.donation.IDonation;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DonationStatusEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Client client;
    private IDonation perk;
    private Status status;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public DonationStatusEvent(Client client, IDonation perk, Status status) {
        this.client = client;
        this.perk = perk;
        this.status = status;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Client getClient() {
        return client;
    }

    public IDonation getPerk() {
        return perk;
    }

    public Status getStatus() {
        return status;
    }


    public enum Status {
        ADDED,
        REMOVED
    }

}