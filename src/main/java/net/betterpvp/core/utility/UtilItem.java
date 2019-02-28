package net.betterpvp.core.utility;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class UtilItem {


    /**
     * Updates an ItemStack, giving it a custom name and lore
     *
     * @param item ItemStack to modify
     * @param name Name to give the ItemStack
     * @param lore Lore to give the ItemStack
     * @return Returns the ItemStack with the newly adjusted name and lore
     */
    public static ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        return setItemNameAndLore(item, name, Arrays.asList(lore));

    }

    /**
     * Updates an ItemStack, giving it a custom name and lore
     *
     * @param item ItemStack to modify
     * @param name Name to give the ItemStack
     * @param lore Lore to give the ItemStack
     * @return Returns the ItemStack with the newly adjusted name and lore
     */
    public static ItemStack setItemNameAndLore(ItemStack item, String name, List<String> lore) {
        ItemMeta im = item.getItemMeta();

        im.setDisplayName(name);
        if (lore != null) {
            im.setLore(lore);
        }
        item.setItemMeta(im);
        return item;
    }

    /**
     * Adds an item to a players inventory, and drops it on the ground if the
     * players inventory is full
     *
     * @param player The Player
     * @param stack  The ItemStack
     */
    public static void insert(Player player, ItemStack stack) {

        if (stack == null || stack.getType() == Material.AIR) {
            return;
        }

        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(stack);
        } else {
            player.getWorld().dropItem(player.getLocation(), stack);
        }
        player.updateInventory();
    }

    /**
     * Check if a Material is a type of sword
     *
     * @param swordType Material to check
     * @return Returns true if the Material is a type of sword
     */
    public static boolean isSword(Material swordType) {
        return (swordType == Material.IRON_SWORD
                || swordType == Material.GOLD_SWORD
                || swordType == Material.DIAMOND_SWORD
                || swordType == Material.STONE_SWORD
                || swordType == Material.WOOD_SWORD);
    }

    /**
     * Check if a Material is a type of axe
     *
     * @param axeType Material to check
     * @return Returns true if the Material is a type of axe
     */
    public static boolean isAxe(Material axeType) {
        return (axeType == Material.IRON_AXE
                || axeType == Material.GOLD_AXE
                || axeType == Material.DIAMOND_AXE
                || axeType == Material.STONE_AXE
                || axeType == Material.WOOD_AXE);
    }

    /**
     * Check if a Material is a type of pickaxe
     *
     * @param pickType Material to check
     * @return Returns true if the Material is a type of pickaxe
     */
    public static boolean isPickAxe(Material pickType) {
        return (pickType == Material.WOOD_PICKAXE
                || pickType == Material.STONE_PICKAXE
                || pickType == Material.IRON_PICKAXE
                || pickType == Material.GOLD_PICKAXE
                || pickType == Material.DIAMOND_PICKAXE);
    }

    public static boolean isShovel(Material shovelType) {
        return (shovelType == Material.WOOD_SPADE
                || shovelType == Material.STONE_SPADE
                || shovelType == Material.IRON_SPADE
                || shovelType == Material.GOLD_SPADE
                || shovelType == Material.DIAMOND_SPADE);
    }

    /**
     * Check if a Material is a type of hoe
     *
     * @param hoeType Material to check
     * @return Returns true if the Material is a type of hoe
     */
    public static boolean isHoe(Material hoeType) {
        return (hoeType == Material.WOOD_HOE
                || hoeType == Material.STONE_HOE
                || hoeType == Material.IRON_HOE
                || hoeType == Material.GOLD_HOE
                || hoeType == Material.DIAMOND_HOE);
    }

    /**
     * Check if a Material is a gold tool
     *
     * @param item Material to check
     * @return Returns true if the Material is a gold tool
     */
    public static boolean isGold(Material item) {
        return (item == Material.GOLD_SWORD
                || item == Material.GOLD_AXE
                || item == Material.GOLD_PICKAXE
                || item == Material.GOLD_SPADE
                || item == Material.GOLD_HOE);
    }

    /**
     * Check if a Material is armour
     *
     * @param m Material to check
     * @return Returns true if the Material is armour
     */
    public static boolean isArmour(Material m) {
        return m.name().contains("LEATHER")
                || m.name().contains("IRON")
                || m.name().contains("GOLD")
                || m.name().contains("CHAINMAIL")
                || m.name().contains("DIAMOND");
    }

    /**
     * Check if a Material is a diamond tool
     *
     * @param item Material to check
     * @return Returns true if the Material is a diamond tool
     */
    public static boolean isDiamond(Material item) {
        return (item == Material.DIAMOND_SWORD
                || item == Material.DIAMOND_AXE
                || item == Material.DIAMOND_PICKAXE
                || item == Material.DIAMOND_SPADE
                || item == Material.DIAMOND_HOE);
    }


    /**
     * Check if a Material is a stone tool
     *
     * @param item Material to check
     * @return Returns true if the Material is a stone tool
     */
    public static boolean isStone(Material item) {
        return (item == Material.STONE_SWORD
                || item == Material.STONE_AXE
                || item == Material.STONE_PICKAXE
                || item == Material.STONE_SPADE
                || item == Material.STONE_HOE);
    }

    /**
     * Check if a Material is a wooden tool
     *
     * @param item Material to check
     * @return Returns true if the Material is a wooden tool
     */
    public static boolean isWood(Material item) {
        return (item == Material.WOOD_SWORD
                || item == Material.WOOD_AXE
                || item == Material.WOOD_PICKAXE
                || item == Material.WOOD_SPADE
                || item == Material.WOOD_HOE);
    }

    /**
     * Check if an item is a tool
     *
     * @param item Item to check
     * @return Returns true if item is a type of tool (has durability)
     */
    public static boolean isRepairable(Material item) {
        return (item == Material.STONE_SWORD
                || item == Material.IRON_SWORD
                || item == Material.GOLD_SWORD
                || item == Material.DIAMOND_SWORD
                || item == Material.STONE_HOE
                || item == Material.IRON_HOE
                || item == Material.GOLD_HOE
                || item == Material.DIAMOND_HOE
                || item == Material.STONE_SPADE
                || item == Material.IRON_SPADE
                || item == Material.GOLD_SPADE
                || item == Material.DIAMOND_SPADE
                || item == Material.STONE_PICKAXE
                || item == Material.IRON_PICKAXE
                || item == Material.GOLD_PICKAXE
                || item == Material.DIAMOND_PICKAXE
                || item == Material.STONE_AXE
                || item == Material.IRON_AXE
                || item == Material.GOLD_AXE
                || item == Material.DIAMOND_AXE
                || item == Material.DIAMOND_HELMET
                || item == Material.DIAMOND_CHESTPLATE
                || item == Material.DIAMOND_LEGGINGS
                || item == Material.DIAMOND_BOOTS
                || item == Material.IRON_HELMET
                || item == Material.IRON_CHESTPLATE
                || item == Material.IRON_LEGGINGS
                || item == Material.IRON_BOOTS
                || item == Material.GOLD_HELMET
                || item == Material.GOLD_CHESTPLATE
                || item == Material.GOLD_LEGGINGS
                || item == Material.GOLD_BOOTS
                || item == Material.CHAINMAIL_HELMET
                || item == Material.CHAINMAIL_CHESTPLATE
                || item == Material.CHAINMAIL_LEGGINGS
                || item == Material.CHAINMAIL_BOOTS
                || item == Material.LEATHER_HELMET
                || item == Material.LEATHER_CHESTPLATE
                || item == Material.LEATHER_LEGGINGS
                || item == Material.LEATHER_BOOTS
                || item == Material.BOW);
    }

    /**
     * Removes attributes from an ItemStack (e.g. the +7 damage that is visible
     * on a diamond sword under the lore)
     *
     * @param item ItemStack to update
     * @return Returns an itemstack without its attributes
     */
    public static ItemStack removeAttributes(ItemStack item) {
        if (!MinecraftReflection.isCraftItemStack(item)) {
            item = MinecraftReflection.getBukkitItemStack(item);
        }
        NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
        compound.put(NbtFactory.ofList("AttributeModifiers"));


        return item;
    }

    /**
     * Add the 'enchanted' glowing effect to any ItemStack
     *
     * @param item Item to update
     * @return Returns an ItemStack that is now glowing
     */
    public static ItemStack addGlow(ItemStack item) {
        if (!MinecraftReflection.isCraftItemStack(item)) {
            item = MinecraftReflection.getBukkitItemStack(item);
        }
        NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
        compound.put(NbtFactory.ofList("ench"));


        return item;
    }


    /**
     * Checks if the block a player is looking at is a door
     * Caps at 4 blocks away
     * This is used to stop skills from being used when a player opens a door from the top,
     * Spigot would call the wrong event https://hub.spigotmc.org/jira/browse/SPIGOT-1955?filter=-2
     *
     * @param p Player to check
     * @return Returns true if the block a player is looking at is a door
     */
    public static boolean isBlockDoor(Player p) {
        try {
            Block b = p.getTargetBlock((Set<Material>) null, 4);

            if (b.getType().name().toLowerCase().contains("door")) {

                return true;
            }
        } catch (IllegalStateException ex) {

        }
        return false;
    }


    /**
     * Check if Item is a weapon / melee tool
     *
     * @param item Item to check
     * @return Returns true if the item is a type of melee tool / weapon
     */
    public static boolean isWeapon(Material item) {
        return (isSword(item) || isAxe(item) || isPickAxe(item)
                || isShovel(item) || isHoe(item)) || item == Material.FISHING_ROD;
    }


    /**
     * Check if a players inventory contains a certain amount of a specific item
     *
     * @param player   Player to check
     * @param item     Material to look for
     * @param data     Data of item to look for
     * @param required Required amount of item to have
     * @return Returns true if players inventory contains an item matching the defined Material, Data and stack size
     */
    @SuppressWarnings("deprecation")
    public static boolean contains(Player player, Material item, byte data, int required) {
        for (Iterator<Integer> localIterator = player.getInventory().all(item).keySet().iterator(); localIterator.hasNext(); ) {
            int i = localIterator.next();
            if (required <= 0) {
                return true;
            }

            ItemStack stack = player.getInventory().getItem(i);
            if ((stack != null) && (stack.getAmount() > 0) && ((stack.getData() == null) || (stack.getData().getData() == data))) {
                required -= stack.getAmount();
            }
        }

        return required <= 0;
    }


    /**
     * Removes a specific amount of an item from a players inventory
     *
     * @param player   The Player
     * @param item     The Material to remove
     * @param data     The data value of the item
     * @param toRemove The amount to remove
     * @return Returns true if the ItemStack was successfully removed from the players inventory
     */
    public static boolean remove(Player player, Material item, byte data, int toRemove) {
        if (!contains(player, item, data, toRemove)) {
            return false;
        }

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) != null) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack.getType() == item) {
                    if (stack.getAmount() > toRemove) {
                        stack.setAmount(stack.getAmount() - toRemove);
                        player.updateInventory();
                    } else {
                        player.getInventory().setItem(i, null);
                        player.updateInventory();
                    }

                    return true;

                }
            }
        }

        return false;
    }

}
