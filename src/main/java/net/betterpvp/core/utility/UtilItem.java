package net.betterpvp.core.utility;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;


public class UtilItem {


    public static ItemStack updateNames(ItemStack item){
 // TODO
        return item;
    }

    /**
     * Updates an ItemStack, giving it a custom name and lore
     *
     * @param item ItemStack to modify
     * @param name Name to give the ItemStack
     * @param lore Lore to give the ItemStack
     * @return Returns the ItemStack with the newly adjusted name and lore
     */
    public static ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        if(lore != null){
            return setItemNameAndLore(item, name, Arrays.asList(lore));
        }

        return setItemNameAndLore(item, name, new ArrayList<>());

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
                || swordType == Material.GOLDEN_SWORD
                || swordType == Material.DIAMOND_SWORD
                || swordType == Material.STONE_SWORD
                || swordType == Material.WOODEN_SWORD);
    }

    /**
     * Check if a Material is a type of axe
     *
     * @param axeType Material to check
     * @return Returns true if the Material is a type of axe
     */
    public static boolean isAxe(Material axeType) {
        return (axeType == Material.IRON_AXE
                || axeType == Material.GOLDEN_AXE
                || axeType == Material.DIAMOND_AXE
                || axeType == Material.STONE_AXE
                || axeType == Material.WOODEN_AXE);
    }

    /**
     * Check if a Material is a type of pickaxe
     *
     * @param pickType Material to check
     * @return Returns true if the Material is a type of pickaxe
     */
    public static boolean isPickAxe(Material pickType) {
        return (pickType == Material.WOODEN_PICKAXE
                || pickType == Material.STONE_PICKAXE
                || pickType == Material.IRON_PICKAXE
                || pickType == Material.GOLDEN_PICKAXE
                || pickType == Material.DIAMOND_PICKAXE);
    }

    public static boolean isShovel(Material shovelType) {
        return (shovelType == Material.WOODEN_SHOVEL
                || shovelType == Material.STONE_SHOVEL
                || shovelType == Material.IRON_SHOVEL
                || shovelType == Material.GOLDEN_SHOVEL
                || shovelType == Material.DIAMOND_SHOVEL);
    }

    /**
     * Check if a Material is a type of hoe
     *
     * @param hoeType Material to check
     * @return Returns true if the Material is a type of hoe
     */
    public static boolean isHoe(Material hoeType) {
        return (hoeType == Material.WOODEN_HOE
                || hoeType == Material.STONE_HOE
                || hoeType == Material.IRON_HOE
                || hoeType == Material.GOLDEN_HOE
                || hoeType == Material.DIAMOND_HOE);
    }

    /**
     * Check if a Material is a type of ranged weapon
     *
     * @param wep Material to check
     * @return Returns true if the Material is a type of ranged weapon
     */
    public static boolean isRanged(Material wep) {
        return (wep == Material.BOW || wep == Material.CROSSBOW);
    }

    /**
     * Check if a Material is a gold tool
     *
     * @param item Material to check
     * @return Returns true if the Material is a gold tool
     */
    public static boolean isGold(Material item) {
        return (item == Material.GOLDEN_SWORD
                || item == Material.GOLDEN_AXE
                || item == Material.GOLDEN_PICKAXE
                || item == Material.GOLDEN_SHOVEL
                || item == Material.GOLDEN_HOE);
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
                || item == Material.DIAMOND_SHOVEL
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
                || item == Material.STONE_SHOVEL
                || item == Material.STONE_HOE);
    }

    /**
     * Check if a Material is a wooden tool
     *
     * @param item Material to check
     * @return Returns true if the Material is a wooden tool
     */
    public static boolean isWood(Material item) {
        return (item == Material.WOODEN_SWORD
                || item == Material.WOODEN_AXE
                || item == Material.WOODEN_PICKAXE
                || item == Material.WOODEN_SHOVEL
                || item == Material.WOODEN_HOE);
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
                || item == Material.GOLDEN_SWORD
                || item == Material.DIAMOND_SWORD
                || item == Material.STONE_HOE
                || item == Material.IRON_HOE
                || item == Material.GOLDEN_HOE
                || item == Material.DIAMOND_HOE
                || item == Material.STONE_SHOVEL
                || item == Material.IRON_SHOVEL
                || item == Material.GOLDEN_SHOVEL
                || item == Material.DIAMOND_SHOVEL
                || item == Material.STONE_PICKAXE
                || item == Material.IRON_PICKAXE
                || item == Material.GOLDEN_PICKAXE
                || item == Material.DIAMOND_PICKAXE
                || item == Material.STONE_AXE
                || item == Material.IRON_AXE
                || item == Material.GOLDEN_AXE
                || item == Material.DIAMOND_AXE
                || item == Material.DIAMOND_HELMET
                || item == Material.DIAMOND_CHESTPLATE
                || item == Material.DIAMOND_LEGGINGS
                || item == Material.DIAMOND_BOOTS
                || item == Material.IRON_HELMET
                || item == Material.IRON_CHESTPLATE
                || item == Material.IRON_LEGGINGS
                || item == Material.IRON_BOOTS
                || item == Material.GOLDEN_HELMET
                || item == Material.GOLDEN_CHESTPLATE
                || item == Material.GOLDEN_LEGGINGS
                || item == Material.GOLDEN_BOOTS
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
        ItemMeta itemMeta =item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(itemMeta);


        return item;
    }

    /**
     * Add the 'enchanted' glowing effect to any ItemStack
     *
     * @param item Item to update
     * @return Returns an ItemStack that is now glowing
     */
    public static ItemStack addGlow(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.LUCK, 1);


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
     * @param required Required amount of item to have
     * @return Returns true if players inventory contains an item matching the defined Material, Data and stack size
     */
    @SuppressWarnings("deprecation")
    public static boolean contains(Player player, Material item, int required) {
        for (Iterator<Integer> localIterator = player.getInventory().all(item).keySet().iterator(); localIterator.hasNext(); ) {
            int i = localIterator.next();
            if (required <= 0) {
                return true;
            }

            ItemStack stack = player.getInventory().getItem(i);
            if ((stack != null) && (stack.getAmount() > 0)) {
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
     * @param toRemove The amount to remove
     * @return Returns true if the ItemStack was successfully removed from the players inventory
     */
    public static boolean remove(Player player, Material item, int toRemove) {
        if (!contains(player, item, toRemove)) {
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
