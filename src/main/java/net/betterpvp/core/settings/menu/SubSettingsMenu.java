package net.betterpvp.core.settings.menu;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.interfaces.Button;
import net.betterpvp.core.interfaces.Menu;
import net.betterpvp.core.settings.menu.buttons.SettingsButton;
import net.betterpvp.core.utility.UtilItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class SubSettingsMenu extends Menu {

    private String sub;

    public SubSettingsMenu(Player player, String sub, int size) {
        super(player, size, ChatColor.YELLOW + sub, new Button[]{});
        this.sub = sub;

        buildPage(player);
        construct();
    }

    public void buildPage(Player player){
        Client client = ClientUtilities.getOnlineClient(player);
        if (client != null) {
            int pos = 0;
            for (Map.Entry<String, Integer> setting : client.getSettings().entrySet()) {
                if (setting.getKey().split("\\.")[0].equalsIgnoreCase(sub)) {

                    addButton(new SettingsButton(pos, setting.getValue() == 1
                            ? UtilItem.addGlow(new ItemStack(Material.BOOK))
                            : new ItemStack(Material.BOOK), ChatColor.YELLOW + setting.getKey().split("\\.")[1]));
                    pos++;
                }

            }
        }
    }
}
