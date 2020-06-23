package net.betterpvp.core.settings.menu;

import net.betterpvp.core.interfaces.Button;
import net.betterpvp.core.interfaces.Menu;
import net.betterpvp.core.settings.menu.buttons.SettingCategoryButton;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainSettingsMenu extends Menu {

    public MainSettingsMenu(Player player) {
        super(player, 9, "Settings", new Button[]{});

        addButton(new SettingCategoryButton(0, new ItemStack(Material.BOOK), ChatColor.YELLOW + "General", null));
        addButton(new SettingCategoryButton(2, new ItemStack(Material.SUNFLOWER), ChatColor.YELLOW + "Cosmetics", null));

        construct();
    }
}
