package net.betterpvp.core.utility;

import org.apache.commons.lang.WordUtils;
import org.bukkit.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.UUID;

public class UtilFormat {

    public static DecimalFormat formatter = new DecimalFormat("#,###");


    /**
     * @param uuid UUID of a Player
     * @return Returns a ChatColor of Green if the player is online, or Red if the player is offline
     */
    public static String getOnlineStatus(UUID uuid) {
        if (Bukkit.getPlayer(uuid) == null) {
            return ChatColor.RED.toString();
        }
        return ChatColor.GREEN.toString();
    }

    /**
     * Replaces Underscores with Spaces and appropriately capitalizes letters
     *
     * @param string String to clean
     * @return A new String without underscores and bad capital usage
     */
    public static String cleanString(String string) {

        String modifyed = string.replace("_", " ");
        return WordUtils.capitalizeFully(modifyed).replace("_", " ");
    }

    /**
     * @param num Number to format
     * @return Returns a formatted number as String
     */
    public static String formatNumber(int num) {
        return formatter.format(num);
    }


    /**
     * Converts a List of objects to a clean String
     *
     * @param list List to convert
     * @return A String containing string values of all objects in a List
     */
    public static String toString(List<?> list) {
        String string = list.toString();
        return string.replace("[", "").replaceAll("]", "");
    }

    /**
     * Converts boolean to TinyInt for SQL usage
     *
     * @param value Boolean value to convert
     * @return Returns 1 if true, 0 if false
     */
    public static int toTinyInt(boolean value) {
        if (value) {
            return 1;
        }
        return 0;
    }

    /**
     * Converts a TinyInt to boolean
     *
     * @param value int to convert
     * @return Returns true if the value provided is 1, 0 if it is false
     */
    public static boolean fromTinyInt(int value) {
        return value == 1;
    }

    /**
     * Adds commas and stuff to numbers
     *
     * @param i
     * @return Returns a formatted string
     */
    public static String numberFormat(int i) {
        return NumberFormat.getInstance().format(i);
    }

    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }


    /**
     * Converts a Location into a readable String
     *
     * @param loc Location to convert
     * @return Returns A string containing the world name, and coordinates
     */
    public static String locationToFile(Location loc) {
        if (loc == null) {
            return "";
        }
        return loc.getWorld().getName() + "," + Math.round(loc.getX()) + "," + Math.round(loc.getY()) + "," + Math.round(loc.getZ());
    }

    /**
     * Converts a chunk to a readable String
     *
     * @param chunk Chunk to convert
     * @return Returns a String containing world name and chunk location
     */
    public static String chunkToFile(Chunk chunk) {
        return chunk.getWorld().getName() + "/ " + chunk.getX() + "/ " + chunk.getZ();
    }

    /**
     * Converts a String into a World Chunk
     *
     * @param string String to convert
     * @return Returns a World Chunk from String provided
     */
    public static Chunk stringToChunk(String string) {
        try {
            String[] tokens = string.split("/ ");
            World world = Bukkit.getWorld(tokens[0]);
            if (world != null) {
                Chunk chunk = world.getChunkAt(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                return chunk;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
