package net.betterpvp.core.settings;

import net.betterpvp.core.client.Client;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SettingChangedEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Client client;
    private Player player;
    private String setting;
    private boolean value;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public SettingChangedEvent(Client client, Player player, String setting, boolean value) {
        this.client = client;
        this.player = player;
        this.setting = setting;
        this.value = value;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }


    public Client getClient() {
        return client;
    }

    public String getSetting() {
        return setting;
    }

    public boolean getValue() {
        return value;
    }
}