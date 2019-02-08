package net.betterpvp.core.client.listeners;

import net.betterpvp.core.client.Client;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClientLoginEvent extends Event {


    private static final HandlerList handlers = new HandlerList();

    private Client client;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ClientLoginEvent(Client client) {
        this.client = client;
    }

    public Client getClient(){
        return client;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
