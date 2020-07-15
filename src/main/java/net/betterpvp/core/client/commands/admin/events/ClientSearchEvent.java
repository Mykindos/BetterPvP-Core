package net.betterpvp.core.client.commands.admin.events;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.donation.IDonation;
import net.betterpvp.core.donation.events.DonationStatusEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

public class ClientSearchEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private List<String> result;
    private Client target;


    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ClientSearchEvent(Player player) {
        this.player = player;
        this.result = new ArrayList<>();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public List<String> getResult() {
        return result;
    }

    public Client getTarget() {
        return target;
    }

    public void setTarget(Client target) {
        this.target = target;
    }
}
