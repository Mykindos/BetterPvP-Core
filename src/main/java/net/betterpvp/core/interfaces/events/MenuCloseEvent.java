package net.betterpvp.core.interfaces.events;

import net.betterpvp.core.interfaces.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class MenuCloseEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    private Player player;
    private Menu menu;

    public MenuCloseEvent(Player player, Menu menu) {
        this.player = player;
        this.menu = menu;
    }

    public Player getPlayer() {
        return player;
    }

    public Menu getMenu() {
        return menu;
    }
}