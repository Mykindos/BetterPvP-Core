package net.betterpvp.core.settings;

import net.betterpvp.core.Core;
import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.mysql.SettingsRepository;
import net.betterpvp.core.framework.BPVPListener;
import net.betterpvp.core.interfaces.events.ButtonClickEvent;
import net.betterpvp.core.settings.menu.MainSettingsMenu;
import net.betterpvp.core.settings.menu.SubSettingsMenu;
import net.betterpvp.core.settings.menu.buttons.SettingCategoryButton;
import net.betterpvp.core.settings.menu.buttons.SettingsButton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;

import java.util.Map;

public class SettingsListener extends BPVPListener<Core> {

    public SettingsListener(Core instance) {
        super(instance);
    }

    @EventHandler
    public void onMenu(ButtonClickEvent e) {
        if (e.getMenu() instanceof MainSettingsMenu) {
            if (e.getButton() instanceof SettingCategoryButton) {
                String category = ChatColor.stripColor(e.getButton().getName());
                e.getPlayer().openInventory(new SubSettingsMenu(e.getPlayer(), category).getInventory());
            }
        } else if (e.getMenu() instanceof SubSettingsMenu) {
            SubSettingsMenu subMenu = (SubSettingsMenu) e.getMenu();
            if (e.getButton() instanceof SettingsButton) {
                String setting = ChatColor.stripColor(e.getMenu().getTitle() + '.' + e.getButton().getName());
                Client client = ClientUtilities.getOnlineClient(e.getPlayer());
                if (client != null) {
                    if (e.getClickType() == ClickType.LEFT) {
                        if (!client.getSettingAsBoolean(setting)) {
                            if (client.getSettings().get(setting) != 1) {

                                if (setting.contains("Cosmetic")) {
                                    disableCosmetics(client, e.getPlayer());
                                }

                                client.getSettings().put(setting, 1);
                                SettingsRepository.updateSetting(client.getUUID(), setting, 1);
                                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 2f);
                                Bukkit.getPluginManager().callEvent(new SettingChangedEvent(client, e.getPlayer(), setting, true));
                            }
                        }
                    } else if (e.getClickType() == ClickType.RIGHT) {
                        if (client.getSettings().get(setting) != 0) {

                            if (setting.contains("Cosmetic")) {
                                disableCosmetics(client, e.getPlayer());
                            }

                            client.getSettings().put(setting, 0);
                            SettingsRepository.updateSetting(client.getUUID(), setting, 0);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 0.6f);
                            Bukkit.getPluginManager().callEvent(new SettingChangedEvent(client, e.getPlayer(), setting, false));
                        }
                    }

                }

                subMenu.getButtons().clear();
                subMenu.buildPage(e.getPlayer());
                subMenu.construct();
            }
        }
    }

    private void disableCosmetics(Client client, Player player) {

        for (Map.Entry<String, Integer> entry : client.getSettings().entrySet()) {
            if (entry.getKey().contains("Cosmetic")) {
                entry.setValue(0);
                Bukkit.getPluginManager().callEvent(new SettingChangedEvent(client, player, entry.getKey(), false));
                SettingsRepository.updateSetting(client.getUUID(), entry.getKey(), 0);
            }

        }
    }
}
