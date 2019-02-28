package net.betterpvp.core.utility;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


public class UtilPlayer {

    /**
     * Get players in a radius
     *
     * @param loc The base location of where the search starts
     * @param dR  The radius in which it searches for players from the location
     * @return a set of players in the given radius of a location
     */
    public static Set<Player> getInRadius(Location loc, double dR) {
        Set<Player> players = new HashSet<>();

        for (Player cur : loc.getWorld().getPlayers()) {
            if (cur.getGameMode() != GameMode.CREATIVE && cur.getGameMode() != GameMode.SPECTATOR) {

                double offset = UtilMath.offset(loc, cur.getLocation());

                if (offset < dR)
                    players.add(cur);
            }
        }
        return players;
    }

    public static void sendActionBar(Player p, String msg) {
        String nmsver = "v1_8_R3";
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(p);
            Object ppoc;
            Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");

            Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
            Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
            Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(msg);
            ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(o, (byte) 2);

            Method m1 = craftPlayerClass.getDeclaredMethod("getHandle");
            Object h = m1.invoke(craftPlayer);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    public static <T> T getNMSHandler(Object object, Class<T> classType) {
        try {
            return classType.cast(object.getClass().getMethod("getHandle").invoke(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Get the item in a players hand
     *
     * @param p The player to check
     * @return Returns the ItemStack in a players hand
     */
    public static ItemStack getMainHand(Player p) {
        return p.getItemInHand();
    }


    /**
     * Gets the amount of free slots in an inventory
     *
     * @param p The player to check
     * @return Returns the amount of free slots in a players inventory
     */
    public static int getFreeInvSpaces(Player p) {
        int count = 0;
        for (ItemStack i : p.getInventory().getContents()) {

            if (i == null || i.getType() == Material.AIR) {
                count++;
            }
        }
        return count;
    }

    /**
     * Adds a potion effect to a player
     * If the player already had the effect, it removes it, and then adds this one
     *
     * @param p        Player to add effect to
     * @param type     Type of potion effect
     * @param level    Level of the effect
     * @param duration Duration of the effect
     */
    public static void addPotionEffect(Player p, PotionEffectType type, int level, int duration) {
        if (p.hasPotionEffect(type)) {
            p.removePotionEffect(type);
        }

        p.addPotionEffect(new PotionEffect(type, duration, level));
    }

    /**
     * Clears a players inventory and equipment
     *
     * @param p Player to clear
     */
    public static void clearInventory(Player p) {
        p.getInventory().clear();
        p.getEquipment().setHelmet(null);
        p.getEquipment().setChestplate(null);
        p.getEquipment().setLeggings(null);
        p.getEquipment().setBoots(null);
    }


    /**
     * Gets a List of LivingEntity's within a radius of a location
     *
     * @param loc Location to check for entities from
     * @param dR  Max radius to check for entities
     * @return Returns a list of LivingEntity found within a defined radius of a location
     */
    public static Set<LivingEntity> getAllInRadius(Location loc, double dR) {
        Set<LivingEntity> players = new HashSet<>();

        for (LivingEntity cur : loc.getWorld().getLivingEntities()) {

            if (cur instanceof Player) {
                Player x = (Player) cur;
                if (x.getGameMode() == GameMode.CREATIVE || x.getGameMode() == GameMode.SPECTATOR) continue;
            }


            double offset = UtilMath.offset(loc, cur.getLocation());

            if (offset < dR)
                players.add(cur);

        }
        return players;
    }

    /**
     * Finds an online player by their name
     * If the player is offline, it will print a list of viable options
     *
     * @param caller Person searching
     * @param player Name of player to search for
     * @param inform Whether or not to tell the searcher of viable options
     * @return Returns the player if an online player matching the string or contains the string is found
     */
    public static Player searchOnline(Player caller, String player, boolean inform) {
        LinkedList<Player> matchList = new LinkedList<>();
        for (Player cur : Bukkit.getOnlinePlayers()) {
            if (cur.getName().equalsIgnoreCase(player)) {
                return cur;
            }
            if (cur.getName().toLowerCase().contains(player.toLowerCase())) {
                matchList.add(cur);
            }
        }
        if (matchList.size() != 1) {
            if (!inform) {
                return null;
            }

            UtilMessage.message(caller, "Online Player Search", ChatColor.YELLOW.toString() + matchList.size() + ChatColor.GRAY
                    + " matches for [" + ChatColor.YELLOW + player + ChatColor.GRAY + "].");

            if (matchList.size() > 0) {
                String matchString = "";
                for (Player cur : matchList) {
                    matchString = matchString + ChatColor.YELLOW + cur.getName() + ChatColor.GRAY + ", ";
                }
                if (matchString.length() > 1) {
                    matchString = matchString.substring(0, matchString.length() - 2);
                }
                UtilMessage.message(caller, "Online Player Search", ChatColor.GRAY + "Matches [" + ChatColor.YELLOW + matchString + ChatColor.GRAY + "].");
            }
            return null;
        }
        return matchList.get(0);
    }


    /**
     * Gets the closest player to the player provided
     *
     * @param player The player to check from
     * @param list   The list of players to check
     * @return Returns the closest player to another player
     */
    public static Player getNearestPlayer(Player player, List<Player> list) {
        double distNear = 0.0D;
        Player playerNear = null;
        for (Player player2 : list) {
            if (player == player2) continue;
            if (player.getWorld() != player2.getWorld()) continue;

            Location location2 = player.getLocation();
            double dist = player2.getLocation().distance(location2);
            if (playerNear == null || dist < distNear) {
                playerNear = player2;
                distNear = dist;
            }
        }
        return playerNear;
    }

    /**
     * Creates an ItemStack from a material, amount, name and lore
     *
     * @param mat   Material to use
     * @param num   Stack size of the ItemStack
     * @param name  Name of the item
     * @param lores Lore of the item
     * @return Returns an ItemStack with the defined properties
     */
    public static ItemStack createItem(Material mat, int num, String name, String... lores) {
        ItemStack i = new ItemStack(mat, num);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<String>();
        for (String str : lores) {
            lore.add(str);
        }

        im.setLore(lore);
        i.setItemMeta(im);

        return i;
    }

    /**
     * Creates an ItemStack from a material, data, amount, name and lore
     *
     * @param mat   Material to use
     * @param data  Data value of the item
     * @param num   Stack size of the ItemStack
     * @param name  Name of the item
     * @param lores Lore of the item
     * @return Returns an ItemStack with the defined properties
     */
    public static ItemStack createItem(Material mat, byte data, int num, String name, String... lores) {
        ItemStack i = new ItemStack(mat, num, data);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<String>();
        for (String str : lores) {
            lore.add(str);
        }

        im.setLore(lore);
        i.setItemMeta(im);

        return i;
    }

    /**
     * Gets a player from an entity
     * E.g. If the entity is a projectile, it will get the shooter if its a player
     *
     * @param e The entity to get the player from
     * @return Returns the Player object if the Entity is a player
     */
    public static Player getPlayer(Entity e) {
        if (e instanceof Player || e instanceof Projectile) {
            Player dam = null;
            if (e instanceof Projectile) {
                Projectile j = (Projectile) e;
                if (j.getShooter() instanceof Player) {
                    dam = (Player) j.getShooter();
                }
            } else {
                dam = (Player) e;
            }

            if (dam != null) {
                return dam;
            }

        }
        return null;

    }


    /**
     * Check if a player has any armour equipped
     * Will also return true if non armour pieces are in Equipment slots
     *
     * @param p Player to check
     * @return Returns true if the player has any armour equipped
     */
    public static boolean armourEquipped(Player p) {
        for (ItemStack i : p.getInventory().getArmorContents()) {
            if (i == null) continue;
            if (i.getType() != Material.AIR) {
                return true;
            }
        }

        return false;
    }


    /**
     * Adds a specified amount of health to a player
     *
     * @param player Player to add health to
     * @param mod    Amount of health to add to the player
     */
    public static void health(Player player, double mod) {
        if (player.isDead()) {
            return;
        }
        double health = player.getHealth() + mod;
        if (health < 0.0D) {
            health = 0.0D;
        }
        if (health > 20.0D) {
            health = 20.0D;
        }
        player.setHealth(health);
    }

    /**
     * Gets a LinkedList of players that are within a specific radius of a location
     *
     * @param loc     Location to check for players from
     * @param maxDist Max distance players are allowed to be from the location
     * @return Returns a LinkedList of players within the specified radius
     */
    public static LinkedList<Player> getNearby(Location loc, double maxDist) {
        LinkedList<Player> nearbyMap = new LinkedList<>();
        for (Player cur : loc.getWorld().getPlayers()) {
            if (cur.getGameMode() != GameMode.CREATIVE) {
                if (!cur.isDead()) {
                    double dist = loc.toVector().subtract(cur.getLocation().toVector()).length();
                    if (dist <= maxDist) {
                        for (int i = 0; i < nearbyMap.size(); i++) {
                            if (dist < loc.toVector().subtract(nearbyMap.get(i).getLocation().toVector()).length()) {
                                nearbyMap.add(i, cur);
                                break;
                            }
                        }
                        if (!nearbyMap.contains(cur)) {
                            nearbyMap.addLast(cur);
                        }
                    }
                }
            }
        }
        return nearbyMap;
    }


}
