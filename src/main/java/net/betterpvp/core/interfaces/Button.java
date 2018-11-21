package net.betterpvp.core.interfaces;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.betterpvp.core.utility.UtilItem;

public class Button {

	private int slot;
	private ItemStack item;
	private String name;
	private String[] lore;

	public Button(int slot, ItemStack item, String name, String... lore) {

		this.slot = slot;
		this.name = name;
		this.lore = lore;
		this.item = UtilItem.removeAttributes(UtilItem.setItemNameAndLore(item, name, lore));

	}

	public Button(int slot, ItemStack item, String name, List<String> lore) { 

		if(lore != null) {
			this.lore = lore.toArray(new String[0]);
		}
		this.slot = slot;
		this.name = name;
		this.item = UtilItem.removeAttributes(UtilItem.setItemNameAndLore(item, name, lore));
	}


	public int getSlot() {
		return slot;
	}



	public ItemStack getItemStack() {
		return item;
	}


	public String getName() {
		return name;
	}

	public String[] getLore() {
		if(lore == null) {
			return new String[] {};
		}
		return lore;
	}

	public void updateLore(List<String> lores){
		item = UtilItem.removeAttributes(UtilItem.setItemNameAndLore(item, name, lores));
	}

	public static boolean isButton(ItemStack item) {
		if (item != null && item.getType() != Material.AIR) {
			if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
				for (Menu menu : Menu.menus) {
					for (Button button : menu.getButtons()) {
						if (button.getItemStack().equals(item)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static Button getButton(ItemStack item) {
		if (item != null && item.getType() != Material.AIR) {
			if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
				for (Menu menu : Menu.menus) {
					for (Button button : menu.getButtons()) {
						if (button.getItemStack().equals(item)) {
							return button;
						}
					}
				}
			}
		}
		return null;
	}
}