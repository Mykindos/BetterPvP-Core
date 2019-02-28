package net.betterpvp.core.framework;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final UpdateType type;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public UpdateEvent(UpdateType example) {
        this.type = example;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public UpdateType getType() {
        return this.type;
    }

    public enum UpdateType {

        FAST, FASTER, FASTEST, SEC_30, MIN_01, MIN_02, MIN_04, MIN_08, MIN_16, MIN_32, MIN_6, MIN_60, MIN_64, MIN_128, SEC, SLOW, SLOWER, SLOWEST, TICK, TICK_2

    }
}
