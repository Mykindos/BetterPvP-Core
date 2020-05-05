package net.betterpvp.core.settings.menu.buttons;

import net.betterpvp.core.interfaces.Button;
import org.bukkit.inventory.ItemStack;

public class SettingsButton extends Button {
    public SettingsButton(int slot, ItemStack item, String name, String... lore) {
        super(slot, item, name, lore);
    }
}
