package net.betterpvp.core.client.commands.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class SpawnTeleportEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private boolean cancelled;

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public SpawnTeleportEvent(Player p) {
		this.player = p;
	}
	
	public void setCancelled(boolean b) {
		this.cancelled = b;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}


	public Player getPlayer() {
		return player;
	}
}
