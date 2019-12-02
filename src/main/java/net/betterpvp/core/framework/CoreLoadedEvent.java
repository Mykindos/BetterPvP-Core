package net.betterpvp.core.framework;

import net.betterpvp.core.Core;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CoreLoadedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Core core;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CoreLoadedEvent(Core core) {
        this.core = core;
    }

    public Core getCore() {
        return this.core;
    }

    public HandlerList getHandlers() {
        return handlers;
    } 

}
