package net.betterpvp.core.punish.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import net.betterpvp.core.Core;
import net.betterpvp.core.framework.BPVPListener;
import net.betterpvp.core.punish.PunishManager;

public class GriefListener extends BPVPListener<Core>{

	
	public GriefListener(Core i){
		super(i);
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onBreak(BlockBreakEvent e){
		if(e.isCancelled()){
			return;
		}
		if(PunishManager.isBuildLocked(e.getPlayer().getUniqueId())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlace(BlockPlaceEvent e){
		
		
		if(PunishManager.isBuildLocked(e.getPlayer().getUniqueId())){
			e.setCancelled(true);
		}
	}
}
