package net.betterpvp.core.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    public static List<Menu> menus = new ArrayList<>();

    private final Player player;
    private final String title;
    private final Inventory inventory;
    private List<Button> buttons;
    private boolean keep;
    private long openTime;
    private int size;

    public Menu(Player player, int size, String title, Button[] buttons) {
        this.player = player;
        this.title = title;
        this.buttons = new ArrayList<>(Arrays.asList(buttons));
        this.inventory = Bukkit.createInventory(player, size, title);
        this.keep = false;
        this.size = size;
        construct();
        menus.add(this);
        openTime = System.currentTimeMillis();
    }


    public Player getPlayer() {
        return player;
    }

    public String getTitle() {
        return title;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean keepMenu() {
        return keep;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void construct() {

        Inventory inv = getInventory();
        for(int i = 0; i < size; i++){
            getInventory().setItem(i, new ItemStack(Material.AIR));
        }
        for (Button button : getButtons()) {
            inv.setItem(button.getSlot(), button.getItemStack());
        }
    }

    public void addButton(Button button) {
        getButtons().add(button);
    }

    public void addButtons(Button[] buttons) {
        for (Button b : buttons) {
            addButton(b);
        }
    }

    public static boolean isMenu(String title) {
        for (Menu menu : menus) {
            if (menu.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }

    public Button getButton(ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                for (Button button : getButtons()) {
                    if (button.getItemStack().equals(item)) {
                        return button;
                    }
                }
            }

        }
        return null;
    }


    public static Menu getMenu(Inventory inventory, String inventoryTitle, Player p) {
        for (Menu menu : menus) {
            if (menu.getTitle().equals(inventoryTitle)) {
                if (inventory.getViewers().size() > 0) {
                    if (menu.getPlayer().getUniqueId().equals(p.getUniqueId())) {
                        return menu;
                    }
                }
            }
        }
        return null;
    }
}