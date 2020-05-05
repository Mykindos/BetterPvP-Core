package net.betterpvp.core.donation.events;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.donation.Perk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DonationStatusEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Client client;
    private Perk perk;
    private Status status;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public DonationStatusEvent(Client client, Perk perk, Status status) {
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

    public Perk getPerk() {
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