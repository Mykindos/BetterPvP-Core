package net.betterpvp.core.utility;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import net.betterpvp.core.utility.restoration.BlockRestoreData;

public class UtilLocation {

	/**
	 * Converts a locations coordinates to a readable string
	 * Rounds to the nearest whole number
	 * @param location The location
	 * @return Returns a string of a locations coordinates
	 */
	public static String locationToString(Location location) {
		return "(" + Math.round(location.getX()) + ", " + Math.round(location.getY()) + ", " + Math.round(location.getZ()) + ")";
	}

	public static Location offset(Location original, double offx, double offy, double offz)
	{
		double newX = original.getX() + offx;
		double newY = original.getY() + offy;
		double newZ = original.getZ() + offz;
		if (newY > 255.0D) {
			newY = 255.0D;
		} else if (newY < 0.0D) {
			newY = 0.0D;
		}
		return new Location(original.getWorld(), newX, newY, newZ);
	}

	/**
	 * Converts a chunks location to a readable string
	 * @param chunk The chunk
	 * @return Returns a string of a Chunks location
	 */
	public static String chunkToString(Chunk chunk) {
		return "(" + chunk.getX() + ", " + chunk.getZ() + ")";
	}


	/**
	 * Converts a string into a Location
	 * @param string String to convert
	 * @return Returns a location based on the string provided
	 */
	public static Location stringToLocation(String string) {
		if (string.length() == 0) {
			return null;
		}

		String[] tokens = string.split(",");
		try {
			for (World cur : Bukkit.getServer().getWorlds()) {
				if (cur.getName().equalsIgnoreCase(tokens[0])) {
					return new Location(cur, Double.parseDouble(tokens[1]),
							Double.parseDouble(tokens[2]),
							Double.parseDouble(tokens[3]));
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * Creates a temporary outline of glowstone around a chunk
	 * Helps players establish the outline of their land quickly
	 * @param chunk The chunk to outline
	 */
	@SuppressWarnings("deprecation")
	public static void outlineChunk(Chunk chunk) {
		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					if (z == 0 || z == 15 || x == 0 || x == 15) {
						Block down = chunk.getWorld().getHighestBlockAt(chunk.getBlock(x, 0, z).getLocation()).getRelative(BlockFace.DOWN);

						if (down.getTypeId() == 1 || down.getTypeId() == 2
								|| down.getTypeId() == 3 || down.getTypeId() == 12
								|| down.getTypeId() == 8) {
							new BlockRestoreData(down, 89, (byte) 0, 60000L);
						}
					}
				}
			}
		}
	}


	public static boolean chunkOutline(Chunk chunk, Location check){

		for (int x = 0; x < 16; x++) { 
			for (int z = 0; z < 16; z++) {
				if (z == 0 || z == 15 || x == 0 || x == 15) {
					for(int i = -3; i < 3; i++) {
						if((chunk.getZ() * 16) + z == (check.getBlockZ() + i) && (chunk.getX() * 16) + x == (check.getBlockX() + i)) {
							return true;
						}
					}

					//System.out.println(chunk.getZ());



				}
			}
		}

		return false;
	}

	public static  Location findLocationBehind(LivingEntity damager, LivingEntity damagee)
	{

		if(damagee != null && damager != null) {
			double curMult = 0.0D;
			double maxMult = 1.5D;

			double rate = 0.1D;

			Location lastValid = damager.getLocation();
			Location lastValid2 = damagee.getLocation();
			while (curMult <= maxMult)
			{
				Vector vec = UtilVelocity.getTrajectory(damagee, damager).multiply(curMult);
				Location loc = damagee.getLocation().add(vec);




				if(loc.getBlock().getType().name().contains("DOOR") || loc.getBlock().getType().name().contains("GATE")) {

					return lastValid2;
				}


				if ((!UtilBlock.airFoliage(loc.getBlock())) || (!UtilBlock.airFoliage(loc.getBlock().getRelative(BlockFace.UP)))) {

					Block b2 = loc.add(0,1,0).getBlock();
					if(UtilBlock.airFoliage(b2) && UtilBlock.airFoliage(b2.getRelative(BlockFace.UP))) {

						break;
					}

					return lastValid2;
				}



				curMult += rate;
			}

			curMult = 0.0D;

			while (curMult <= maxMult)
			{
				Vector vec = UtilVelocity.getTrajectory(damager, damagee).multiply(curMult);
				Location loc = damager.getLocation().subtract(vec);

				if(loc.getBlock().getType().name().contains("DOOR") || loc.getBlock().getType().name().contains("GATE")) {
					UtilVelocity.velocity(damagee, UtilVelocity.getTrajectory(damagee, damager), 0.3, false, 0, 0.1, 0.2, false);
					return lastValid;
				}

				if ((!UtilBlock.airFoliage(loc.getBlock())) || (!UtilBlock.airFoliage(loc.getBlock().getRelative(BlockFace.UP)))) {
					return lastValid;
				}
				lastValid = loc;

				curMult += rate;
			}

			return lastValid;

		}

		return null;
	}
}
