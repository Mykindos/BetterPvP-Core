package net.betterpvp.core.donation.perks;


import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.donation.DonationExpiryTimes;
import net.betterpvp.core.donation.IDonation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class ReservedSlot implements IDonation, Listener {


    @EventHandler (priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent e){

        if(Bukkit.getServer().hasWhitelist()){
            if(!Bukkit.getWhitelistedPlayers().contains(e.getPlayer())){
                return;
            }

        }

        //if(e.getPlayer() == null) return;

        Client client = ClientUtilities.getClient(e.getPlayer());
        if(client == null){
            e.setResult(PlayerLoginEvent.Result.ALLOWED);
            return;
        }

        if(client.hasDonation(getName()) || client.hasDonation("VIP") || client.hasRank(Rank.TRIAL_MOD, false)){
            e.setResult(PlayerLoginEvent.Result.ALLOWED);
            return;
        }

        int count = 0;
        for(Player p : Bukkit.getOnlinePlayers()){
            Client pClient = ClientUtilities.getOnlineClient(p);
            if(pClient != null){
                if(pClient.hasDonation(getName()) || pClient.hasDonation("VIP") || pClient.hasRank(Rank.TRIAL_MOD, false)){
                    count++;
                }
            }
        }

        if(Bukkit.getOnlinePlayers().size() < Bukkit.getServer().getMaxPlayers() + count){
            e.setResult(PlayerLoginEvent.Result.ALLOWED);
            return;
        }


        if (e.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            e.setKickMessage(ChatColor.RED + "Server - " + ChatColor.YELLOW + " Visit https://store.betterpvp.net to purchase a reserved slot\n" + ChatColor.WHITE
                    + "There are currently " + ChatColor.GREEN + Bukkit.getOnlinePlayers().size()
                    + ChatColor.YELLOW + " / " + ChatColor.GREEN + Bukkit.getServer().getMaxPlayers() + ChatColor.WHITE + " players online");
        }

    }

    @Override
    public String getName() {
        return "ReservedSlot";
    }

    @Override
    public String getDisplayName() {
        return "Reserved Slot";
    }

    @Override
    public long getExpiryTime() {
        return DonationExpiryTimes.DAY * 31;
    }
}
