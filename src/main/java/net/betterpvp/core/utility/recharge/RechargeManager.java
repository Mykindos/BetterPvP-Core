package net.betterpvp.core.utility.recharge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.betterpvp.core.utility.UtilTime;
import net.betterpvp.core.utility.UtilTime.TimeUnit;

public class RechargeManager {

	private static RechargeManager instance;
	private ConcurrentHashMap<String, List<Recharge>> recharge = new ConcurrentHashMap<>();

	public ConcurrentHashMap<String, List<Recharge>> getRecharges(){
		return recharge;
	}

	public List<Recharge> getRecharges(Player p){
		if(recharge.containsKey(p.getName())){
			return recharge.get(p.getName());
		}

		return new ArrayList<Recharge>();
	}

	private RechargeManager(){

	}

	public static RechargeManager getInstance(){
		if(instance == null){
			instance = new RechargeManager();
		}

		return instance;
	}

	public boolean add(Player player, String ability, double d, boolean inform) {

		return add(player,ability,d,inform, true);

	}
	
	public boolean add(Player player, String ability, double d, boolean inform, boolean removeOnDeath){
		return add(player,ability,d, inform, removeOnDeath, false);
	}

	public boolean add(Player player, String ability, double d, boolean inform, boolean removeOnDeath, boolean cancellable){

		if (isCooling(player.getName(), ability)) {

			if (inform) {
				player.sendMessage(ChatColor.BLUE + "Recharge> " + ChatColor.GRAY + "You cannot use " + ChatColor.GREEN + ability + ChatColor.GRAY + " for "
						+ ChatColor.GREEN + getAbilityRecharge(player.getName(), ability).getRemaining() + " Seconds" + ChatColor.GRAY + ".");
			}

			return false;
		}

		if (!recharge.containsKey(player.getName())) {
			recharge.put(player.getName(), new ArrayList<Recharge>());
			//	return true;
		}

		recharge.get(player.getName()).add(new Recharge(ability, d, System.currentTimeMillis(), removeOnDeath, inform, cancellable));

		return true;
	}

	public synchronized boolean isCooling(String player, String ability) {
		if (!recharge.containsKey(player)) {
			return false;
		}

		for(Recharge r : recharge.get(player)){
			if(r.getAbility().toLowerCase().equalsIgnoreCase(ability.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	public double getRemaining(String player, String ability) {
		for(Recharge r : recharge.get(player)){
			if(r.getAbility().equalsIgnoreCase(ability)) {
				return UtilTime.convert((r.getSeconds() + r.getSystemTime()) - System.currentTimeMillis(), UtilTime.TimeUnit.MINUTES, 1);

			}
			
		}
		
		return 0;
	}
	
	public String getRemainingString(String player, String ability) {
		for(Recharge r : recharge.get(player)){
			if(r.getAbility().equalsIgnoreCase(ability)) {
				return UtilTime.getTime((r.getSeconds() + r.getSystemTime()) - System.currentTimeMillis(), TimeUnit.BEST, 2);

			}
			
		}
		
		return "";
	}

	public Recharge getAbilityRecharge(String player, String ability){

		if(recharge.containsKey(player)){
			for(Recharge r : recharge.get(player)){
				if(r.getAbility().toLowerCase().equalsIgnoreCase(ability.toLowerCase())){
					return r;
				}
			}
		}
		return null;
	}



	public void removeCooldown(String player, String ability, boolean silent) {
		Recharge r = getAbilityRecharge(player, ability);
		if(r == null) return;
		recharge.get(player).remove(r);

		if (!silent) {
			Player cPlayer = Bukkit.getPlayer(player);
			if (cPlayer != null) {
				cPlayer.sendMessage(ChatColor.BLUE + "Recharge> " + ChatColor.GREEN + ability + ChatColor.GRAY + " has been recharged.");
			}
		}
	}

	public synchronized void handleCooldowns() {

		Iterator<Entry<String, List<Recharge>>> it = recharge.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, List<Recharge>> next = it.next();
			ListIterator<Recharge> ability = next.getValue().listIterator();
			while(ability.hasNext()){
				Recharge ab = ability.next();
				if(ab == null){
					it.remove();
					continue;
				}
				if(ab.getRemaining() <= 0){
					Player p = Bukkit.getPlayer(next.getKey());
					if(p != null){
						if(ab.getInform()){
							p.sendMessage(ChatColor.BLUE + "Recharge> " + ChatColor.GREEN + ab.getAbility() + ChatColor.GRAY + " has been recharged.");
						}
					}

					ability.remove();
					if(next.getValue().isEmpty()){
						it.remove();
					}
				}

			}
		}



	}

}
