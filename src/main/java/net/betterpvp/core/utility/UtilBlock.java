package net.betterpvp.core.utility;

import com.comphenix.protocol.wrappers.BlockPosition;
import net.minecraft.server.v1_15_R1.EntityArrow;
import net.minecraft.server.v1_15_R1.IBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.BlockIterator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UtilBlock {

    public static HashSet<Material> blockAirFoliageSet = new HashSet<>();
    public static HashSet<Material> blockPassSet = new HashSet<>();
    public static HashSet<Material> blockUseSet = new HashSet<>();

    /**
     * Check if a Player is in Lava
     *
     * @param p The Player
     * @return Returns true if the Player is currently standing in lava.
     */
    public static boolean isInLava(Player p) {

        return p.getLocation().getBlock().getType() == Material.LAVA;
    }

    /**
     * Check if a Location is in the tutorial world
     *
     * @param loc Location you wish to check
     * @return Returns true if the locations world is the Tutorial world
     */
    public static boolean isTutorial(Location loc) {

        return loc.getWorld().getName().equals("tutorial");
    }


    /**
     * Check if a Player is in water
     *
     * @param p The Player
     * @return Returns true if the Player is standing in water
     */
    public static boolean isInWater(Player p) {

        return p.getLocation().getBlock().getType() == Material.WATER;
    }

    /**
     * Get the Block a player is looking at
     *
     * @param p     The player
     * @param range Max distance the block can be from a player
     * @return The block the player is looking at
     */
    public static final Block getTarget(Player p, int range) {
        BlockIterator iter = new BlockIterator(p, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    /**
     * Check if an Entity is on the ground
     *
     * @param ent The Entity
     * @return Returns true if the entity is on the ground
     */
    public static boolean isGrounded(Entity ent) {
        if ((ent instanceof CraftEntity)) {
            return ((CraftEntity) ent).getHandle().onGround;
        }
        return !airFoliage(ent.getLocation().add(0, -1, 0).getBlock());
    }

    /**
     * Gets the block under the location provided
     *
     * @param location The location to check
     * @return The block under the location provided
     */
    public static Block getBlockUnder(Location location) {
        location.setY(location.getY() - 1);

        return location.getBlock();
    }


    /**
     * Set the block type at a specific location
     *
     * @param loc  Location to change
     * @param m    Material of the new block

     */
    public static void setBlock(Location loc, Material m) {
        loc.getWorld().getBlockAt(loc).setType(m);
    }

    /**
     * Gets the block an arrow has landed on
     * Uses NMS
     *
     * @param p The arrow
     * @return The block an arrow has landed in
     */
    public static Block getBlockHitByArrow(Projectile p) {

        try {
            EntityArrow arrow = ((CraftArrow) p).getHandle();
            Field fieldX = EntityArrow.class.getDeclaredField("d");
            Field fieldY = EntityArrow.class.getDeclaredField("e");
            Field fieldZ = EntityArrow.class.getDeclaredField("f");

            fieldX.setAccessible(true);
            fieldY.setAccessible(true);
            fieldZ.setAccessible(true);

            int x = fieldX.getInt(arrow);
            int y = fieldY.getInt(arrow);
            int z = fieldZ.getInt(arrow);


            Block d = p.getWorld().getBlockAt(x, y, z);
            return d;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }


    /**
     * Returns a list of blocks surrounding the block provided
     *
     * @param block     The block to check
     * @param diagonals Whether or not to check the blocks diagnol of block
     * @return An ArrayList of blocks that are surrounding the block provided
     */
    public static ArrayList<Block> getSurrounding(Block block, boolean diagonals) {
        ArrayList<Block> blocks = new ArrayList<>();
        if (diagonals) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if ((x != 0) || (y != 0) || (z != 0)) {
                            blocks.add(block.getRelative(x, y, z));
                        }
                    }
                }
            }
        } else {
            blocks.add(block.getRelative(BlockFace.UP));
            blocks.add(block.getRelative(BlockFace.DOWN));
            blocks.add(block.getRelative(BlockFace.NORTH));
            blocks.add(block.getRelative(BlockFace.SOUTH));
            blocks.add(block.getRelative(BlockFace.EAST));
            blocks.add(block.getRelative(BlockFace.WEST));
        }
        return blocks;
    }


    /**
     * Gets a Map of All blocks and their distance from the location provided
     *
     * @param loc The location to check
     * @param dR  The max radius to check for blocks
     * @return A HashMap of <Block, Double> containing all blocks and their distance from a location
     */
    public static HashMap<Block, Double> getInRadius(Location loc, double dR) {
        return getInRadius(loc, dR, 999.0D);
    }

    /**
     * Gets a Map of All blocks and their distance from the location provided
     *
     * @param loc         The location to check
     * @param dR          The max radius to check for blocks
     * @param heightLimit Max distance to check up and down
     * @return A HashMap of <Block, Double> containing all blocks and their distance from a location
     */
    public static HashMap<Block, Double> getInRadius(Location loc, double dR, double heightLimit) {
        HashMap<Block, Double> blockList = new HashMap<Block, Double>();
        int iR = (int) dR + 1;

        for (int x = -iR; x <= iR; x++) {
            for (int z = -iR; z <= iR; z++) {
                for (int y = -iR; y <= iR; y++) {

                    if (Math.abs(y) <= heightLimit) {
                        Block curBlock = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int) (loc.getY() + y), (int) (loc.getZ() + z));

                        double offset = UtilMath.offset(loc, curBlock.getLocation().add(0.5D, 0.5D, 0.5D));

                        if (offset <= dR) {
                            blockList.put(curBlock, 1.0D - offset / dR);
                        }
                    }
                }
            }
        }
        return blockList;
    }

    /**
     * Gets a Map of All blocks and their distance from the location provided
     *
     * @param block The location to check
     * @param dR    The max radius to check for blocks
     * @return A HashMap of <Block, Double> containing all blocks and their distance from a location
     */
    public static HashMap<Block, Double> getInRadius(Block block, double dR) {
        HashMap<Block, Double> blockList = new HashMap<Block, Double>();
        int iR = (int) dR + 1;
        for (int x = -iR; x <= iR; x++) {
            for (int z = -iR; z <= iR; z++) {
                for (int y = -iR; y <= iR; y++) {
                    Block curBlock = block.getRelative(x, y, z);

                    double offset = UtilMath.offset(block.getLocation(), curBlock.getLocation());
                    if (offset <= dR) {
                        blockList.put(curBlock, 1.0D - offset / dR);
                    }
                }
            }
        }
        return blockList;
    }

    /**
     * Check if a specific block is a chest, door, or other type of interactable block
     *
     * @param block Block to check
     * @return Returns true if an ability can be casted, False if block can be interacted with
     */
    public static boolean abilityCheck(Block block) {
        if (block == null) {
            return true;
        }

        return !block.getType().toString().contains("_DOOR") && block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST
                && block.getType() != Material.ANVIL;
    }

    /**
     * Check if block is a block entities can walk through (e.g. Long grass)
     *
     * @param block block ID
     * @return Returns true if the block does not stop player movement
     */
    public static boolean airFoliage(byte block) {
        if (blockAirFoliageSet.isEmpty()) {
            blockAirFoliageSet.add(Material.AIR);
            for(Material m : Material.values()){
                if(m.name().contains("SAPLING")){
                    blockAirFoliageSet.add(m);
                }else if(m.name().contains("DEAD")){
                    blockAirFoliageSet.add(m);
                }
            }
            blockAirFoliageSet.add(Material.FERN);
            blockAirFoliageSet.add(Material.LARGE_FERN);
            blockAirFoliageSet.add(Material.GRASS);
            blockAirFoliageSet.add(Material.SEAGRASS);
            blockAirFoliageSet.add(Material.TALL_SEAGRASS);

            /*blockAirFoliageSet.add((byte) 37);
            blockAirFoliageSet.add((byte) 38);
            blockAirFoliageSet.add((byte) 39);
            blockAirFoliageSet.add((byte) 40);
            blockAirFoliageSet.add((byte) 51);
            blockAirFoliageSet.add((byte) 132);
            blockAirFoliageSet.add((byte) 131);
            blockAirFoliageSet.add((byte) 59);
            blockAirFoliageSet.add((byte) 104);
            blockAirFoliageSet.add((byte) 105);
            blockAirFoliageSet.add((byte) 115);
            blockAirFoliageSet.add((byte) 102);

            blockAirFoliageSet.add((byte) 50);
            blockAirFoliageSet.add((byte) 76);
            blockAirFoliageSet.add((byte) 175);
            blockAirFoliageSet.add((byte) 166);
            blockAirFoliageSet.add((byte) 323);
            blockAirFoliageSet.add((byte) 287);
            blockAirFoliageSet.add((byte) 68);
            blockAirFoliageSet.add((byte) 77);
            blockAirFoliageSet.add((byte) 143);
            //blockAirFoliageSet.add((byte) 183);
            //blockAirFoliageSet.add((byte) 184);
            //blockAirFoliageSet.add((byte) 185);
            //blockAirFoliageSet.add((byte) 186);
            //blockAirFoliageSet.add((byte) 107);
            //blockAirFoliageSet.add((byte) 187);
            blockAirFoliageSet.add((byte) 167);
            blockAirFoliageSet.add((byte) 330);

             */
        }

        return blockAirFoliageSet.contains(block);
    }

    /**
     * Check if block is long grass
     *
     * @param b Block to check
     * @return Returns true if the block is Long Grass
     */
    public static boolean airFoliageBlock(Block b) {
        return b.getType() == Material.TALL_GRASS;
    }

    /**
     * Check if block is a block entities can walk through (e.g. Long grass)
     *
     * @param block block ID
     * @return Returns true if the block does not stop player movement
     */
    public static boolean airFoliage(int block) {
        return airFoliage((byte) block);
    }

    /**
     * Check if block is a block entities can walk through (e.g. Long grass)
     *
     * @param block Block to check
     * @return Returns true if the block does not stop player movement
     */
    @SuppressWarnings("deprecation")
    public static boolean airFoliage(Block block) {
        if (block == null) {
            return false;
        }
        return airFoliage(block.getType().getId());
    }

    /**
     * Check if a block is a solid block
     *
     * @param block Block ID to check
     * @return Returns true if the block is solid (e.g. Stone)
     */
    public static boolean solid(byte block) {
        if (blockPassSet.isEmpty()) {

          /*  blockPassSet.add((byte) 0);
            blockPassSet.add((byte) 6);
            blockPassSet.add((byte) 8);
            blockPassSet.add((byte) 9);
            blockPassSet.add((byte) 10);
            blockPassSet.add((byte) 11);
            blockPassSet.add((byte) 26);
            blockPassSet.add((byte) 27);
            blockPassSet.add((byte) 28);
            blockPassSet.add((byte) 30);
            blockPassSet.add((byte) 31);
            blockPassSet.add((byte) 32);
            blockPassSet.add((byte) 37);
            blockPassSet.add((byte) 38);
            blockPassSet.add((byte) 39);
            blockPassSet.add((byte) 40);
            blockPassSet.add((byte) 50);
            blockPassSet.add((byte) 51);
            blockPassSet.add((byte) 55);
            blockPassSet.add((byte) 59);
            blockPassSet.add((byte) 63);
            blockPassSet.add((byte) 64);
            blockPassSet.add((byte) 65);
            blockPassSet.add((byte) 66);
            blockPassSet.add((byte) 68);
            blockPassSet.add((byte) 69);
            blockPassSet.add((byte) 70);
            blockPassSet.add((byte) 71);
            blockPassSet.add((byte) 72);
            blockPassSet.add((byte) 75);
            blockPassSet.add((byte) 77);
            blockPassSet.add((byte) 76);
            blockPassSet.add((byte) 78);
            blockPassSet.add((byte) 83);
            blockPassSet.add((byte) 90);
            blockPassSet.add((byte) 92);
            blockPassSet.add((byte) 93);
            blockPassSet.add((byte) 94);
            blockPassSet.add((byte) 96);
            blockPassSet.add((byte) 101);
            blockPassSet.add((byte) 102);
            blockPassSet.add((byte) 104);
            blockPassSet.add((byte) 105);
            blockPassSet.add((byte) 106);
            blockPassSet.add((byte) 107);
            blockPassSet.add((byte) 111);
            blockPassSet.add((byte) 115);
            blockPassSet.add((byte) 116);
            blockPassSet.add((byte) 117);
            blockPassSet.add((byte) 118);
            blockPassSet.add((byte) 119);
            blockPassSet.add((byte) 120);
            blockPassSet.add((byte) 208);*/

        }

        return !blockPassSet.contains(block);
    }

    /**
     * Check if a block is a solid block
     *
     * @param block Block ID to check
     * @return Returns true if the block is solid (e.g. Stone)
     */
    public static boolean solid(int block) {
        return solid((byte) block);
    }

    /**
     * Check if a block is a solid block
     *
     * @param block Block to check
     * @return Returns true if the block is solid (e.g. Stone)
     */
    @SuppressWarnings("deprecation")
    public static boolean solid(Block block) {
        if (block == null) {
            return false;
        }
        return solid(block.getType().getId());
    }

    /**
     * Check if a block is usable (can interact with)
     *
     * @param block Block ID to check
     * @return True if the block can be interacted with. (E.g. a chest or door)
     */
    @SuppressWarnings("deprecation")
    public static boolean usable(Block block) {
        if (block == null) {
            return false;
        }
        return usable(block.getType().getId());
    }

    /**
     * Check if a block is usable (can interact with)
     *
     * @param block Block ID to check
     * @return True if the block can be interacted with. (E.g. a chest or door)
     */
    public static boolean usable(byte block) {
        if (blockUseSet.isEmpty()) {
          /*  blockUseSet.add((byte) 23);
            blockUseSet.add((byte) 330);
            blockUseSet.add((byte) 167);
            blockUseSet.add((byte) 26);
            blockUseSet.add((byte) 54);
            blockUseSet.add((byte) 58);
            blockUseSet.add((byte) 61);
            blockUseSet.add((byte) 62);
            blockUseSet.add((byte) 64);
            blockUseSet.add((byte) 69);
            blockUseSet.add((byte) 71);
            blockUseSet.add((byte) 77);
            blockUseSet.add((byte) 93);
            blockUseSet.add((byte) 94);
            blockUseSet.add((byte) 96);
            blockUseSet.add((byte) 107);
            blockUseSet.add((byte) 183);
            blockUseSet.add((byte) 184);
            blockUseSet.add((byte) 185);
            blockUseSet.add((byte) 186);
            blockUseSet.add((byte) 187);
            blockUseSet.add((byte) 117);
            blockUseSet.add((byte) 116);
            blockUseSet.add((byte) 145);
            blockUseSet.add((byte) 146);*/
        }
        return blockUseSet.contains(Byte.valueOf(block));
    }

    /**
     * Check if a block is usable (can interact with)
     *
     * @param block Block ID to check
     * @return True if the block can be interacted with. (E.g. a chest or door)
     */
    public static boolean usable(int block) {
        return usable((byte) block);
    }
}
