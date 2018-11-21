package net.betterpvp.core.utility;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;


public class UtilEntity {
	
	/**
	 * Gets a Set of all LivingEntities within a radius of a location
	 * @param loc Location to check from
	 * @param dR Radius to check from the location
	 * @return Returns a Set of all Living Entities within a radius of a location
	 */
	public static Set<LivingEntity> getAllInRadius(Location loc, double dR) {
		Set<LivingEntity> players = new HashSet<>();

		for (Entity cur : loc.getWorld().getEntities())
		{
			if(cur instanceof LivingEntity){
				if(cur instanceof Player){
					Player x = (Player) cur;
					if(x.getGameMode() == GameMode.CREATIVE) continue;
				}


				double offset = UtilMath.offset(loc, cur.getLocation());

				if (offset < dR)
					players.add((LivingEntity) cur);
			}
		}
		return players;
	}

	/**
	 * Gets the LivingEntity who dealt damage in an EntityDamageEvent
	 * @param event EntityDamageEvent
	 * @return Returns the LivingEntity that caused the damage in a EntityDamageByEntityEvent
	 */
    public static LivingEntity getDamagerEntity(EntityDamageEvent event) {
        if (!(event instanceof EntityDamageByEntityEvent)) {
            return null;
        }

        EntityDamageByEntityEvent eventEE = (EntityDamageByEntityEvent) event;
        if ((eventEE.getDamager() instanceof LivingEntity)) {
            return (LivingEntity) eventEE.getDamager();
        }

        if (!(eventEE.getDamager() instanceof Projectile)) {
            return null;
        }

        Projectile projectile = (Projectile) eventEE.getDamager();
        if (projectile.getShooter() == null) {
            return null;
        }

        if (!(projectile.getShooter() instanceof LivingEntity)) {
            return null;
        }

        return (LivingEntity) projectile.getShooter();
    }

    
   
    
    /**
     * Check if an entity has collided with a specific location
     * @param loc Location to check collision
     * @param ent Entity to check collision with location
     * @param mult Multiplier
     * @return Returns true if an Entity collides with a specified Location
     */
       public static boolean collide(Location loc, Entity ent, double mult) {
        if (ent instanceof Player) {
            Player player = (Player) ent;
            if (UtilMath.offset(loc, player.getEyeLocation()) < 0.4D * mult) {
                return true;
            }
            if (UtilMath.offset2d(loc, player.getLocation()) < 0.6D * mult) {
                return loc.getY() > player.getLocation().getY() && loc.getY() < player.getEyeLocation().getY();
            }
        } else if (ent instanceof Giant) {
            return loc.getY() > ent.getLocation().getY() && loc.getY() < ent.getLocation().getY() + 12.0D
                    && UtilMath.offset2d(loc, ent.getLocation()) < 4.0D;
        } else return loc.getY() > ent.getLocation().getY() && loc.getY() < ent.getLocation().getY() + 2.0D
                && UtilMath.offset2d(loc, ent.getLocation()) < 0.5D * mult;
        return false;
    }
}
