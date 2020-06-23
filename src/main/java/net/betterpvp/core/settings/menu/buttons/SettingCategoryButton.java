package net.betterpvp.core.settings.menu.buttons;

import net.betterpvp.core.interfaces.Button;
import org.bukkit.inventory.ItemStack;

public class SettingCategoryButton extends Button {

    public SettingCategoryButton(int slot, ItemStack item, String name, String... lore) {
        super(slot, item, name, lore);
    }
}
