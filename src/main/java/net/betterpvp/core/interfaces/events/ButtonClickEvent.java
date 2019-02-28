package net.betterpvp.core.interfaces.events;

import net.betterpvp.core.interfaces.Button;
import net.betterpvp.core.interfaces.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;


public class ButtonClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    private Player player;
    private Menu menu;
    private Button button;
    private ClickType clickType;
    private Integer slot;

    public ButtonClickEvent(Player player, Menu menu, Button button, ClickType clickType, Integer slot) {
        this.player = player;
        this.menu = menu;
        this.button = button;
        this.clickType = clickType;
        this.slot = slot;
    }

    public Player getPlayer() {
        return player;
    }

    public Menu getMenu() {
        return menu;
    }

    public Button getButton() {
        return button;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public Integer getSlot() {
        return slot;
    }
}

