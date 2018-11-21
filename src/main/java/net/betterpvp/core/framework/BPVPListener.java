package net.betterpvp.core.framework;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class BPVPListener<T> implements Listener{
	 
	private T instance;
	
	/*
	 * Handy superclass that will automatically register listeners
	 */
	public BPVPListener(T instance){
		Bukkit.getPluginManager().registerEvents(this, (Plugin) instance);
		this.instance = instance;
	}
	
	protected T getInstance(){
		return instance;
	}

}
