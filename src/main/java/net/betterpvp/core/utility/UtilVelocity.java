package net.betterpvp.core.utility;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class UtilVelocity {

    public static void velocity(Entity ent, double str, double yAdd, double yMax, boolean groundBoost) {
        velocity(ent, ent.getLocation().getDirection(), str, false, 0.0D, yAdd, yMax, groundBoost);
    }
    
 
    public static Vector getTrajectory2d(Entity from, Entity to)
    {
      return getTrajectory2d(from.getLocation().toVector(), to.getLocation().toVector());
    }
    
    public static Vector getTrajectory2d(Location from, Location to)
    {

      return getTrajectory2d(from.toVector(), to.toVector());
    }
    
    public static Vector getTrajectory2d(Vector from, Vector to)
    {
      return to.subtract(from).setY(0).normalize();
    }

    
    /**
     * 
     * @param ent Entity to apply velocity to
     * @param vec Vector of velocity
     * @param str Strength of velocity
     * @param ySet
     * @param yBase
     * @param yAdd
     * @param yMax
     * @param groundBoost
     */
    public static void velocity(Entity ent, Vector vec, double str, boolean ySet, double yBase, double yAdd, double yMax, boolean groundBoost) {
        if ((Double.isNaN(vec.getX())) || (Double.isNaN(vec.getY())) || (Double.isNaN(vec.getZ())) || (vec.length() == 0.0D)) {
            return;
        }

        if (ySet) {
            vec.setY(yBase);
        }

        vec.normalize();
        vec.multiply(str);

        vec.setY(vec.getY() + yAdd);

        if (vec.getY() > yMax) {
            vec.setY(yMax);
        }
        if ((groundBoost) && (UtilBlock.isGrounded(ent))) {
            vec.setY(vec.getY() + 0.2D);
        }

        ent.setFallDistance(0.0F);
        ent.setVelocity(vec);
    }

    public static Vector getTrajectory(Entity from, Entity to) {
        return getTrajectory(from.getLocation().toVector(), to.getLocation().toVector());
    }

    public static Vector getTrajectory(Location from, Location to) {
        return getTrajectory(from.toVector(), to.toVector());
    }

    public static Vector getTrajectory(Vector from, Vector to) {
        return to.subtract(from).normalize();
    }

}
