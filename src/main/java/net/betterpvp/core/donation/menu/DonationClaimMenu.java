package net.betterpvp.core.donation.menu;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.donation.Donation;
import net.betterpvp.core.donation.DonationManager;
import net.betterpvp.core.donation.IClaimable;
import net.betterpvp.core.donation.IDonation;
import net.betterpvp.core.interfaces.Button;
import net.betterpvp.core.interfaces.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DonationClaimMenu extends Menu {

    public DonationClaimMenu(Player player) {
        super(player, 18, ChatColor.GREEN + "Claim your donations!", new Button[]{});

        buildPage(player);
        construct();
    }

    public void buildPage(Player player) {
        int slot = 0;
        Client client = ClientUtilities.getOnlineClient(player);
        if (client != null) {
            for (Donation d : client.getDonations()) {
                IDonation perk = DonationManager.getDonation(d.getName());
                if (perk == null) continue;
                if (!(perk instanceof IClaimable)) continue;
                if (d.hasExpired()) continue;
                if (d.isClaimed()) continue;

                addButton(new Button(slot, new ItemStack(Material.GREEN_WOOL), ChatColor.GREEN + perk.getDisplayName()));
                slot++;
            }
        }
    }
}
