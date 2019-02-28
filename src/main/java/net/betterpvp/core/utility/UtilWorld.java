package net.betterpvp.core.utility;

import org.bukkit.Location;
import org.bukkit.World;


public class UtilWorld {


    /**
     * Merges 2 locations
     *
     * @param a Location A
     * @param b Location B
     * @return Returns a location with merged coordinates
     */
    public static Location locMerge(Location a, Location b) {
        a.setX(b.getX());
        a.setY(b.getY());
        a.setZ(b.getZ());
        return a;
    }

    /**
     * Gets the name of a world environment
     *
     * @param env The environment to check
     * @return Returns the name of a worlds Environment
     */
    public static String envToStr(World.Environment env) {
        if (env == World.Environment.NORMAL) {
            return "Overworld";
        }
        if (env == World.Environment.NETHER) {
            return "Nether";
        }
        if (env == World.Environment.THE_END) {
            return "The End";
        }
        return "Unknown";
    }


}
