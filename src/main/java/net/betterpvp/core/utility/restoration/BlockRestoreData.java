package net.betterpvp.core.utility.restoration;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BlockRestoreData {

    public static List<BlockRestoreData> restoreData = new ArrayList<BlockRestoreData>();

    private Block block;
    private Material material;
    private Material oldMaterial;
    private byte data;
    private byte oldData;
    private long expire;
    private int blocklevel = 0;

    public BlockRestoreData(Block block, Material mat, byte data, long expire) {
        this.block = block;
        this.material = mat;
        this.oldMaterial = block.getType();
        this.data = data;
        this.oldData = block.getData();
        this.expire = System.currentTimeMillis() + expire;
        if(block.getBlockData() instanceof Levelled){
            Levelled levelled = (Levelled) block.getBlockData();
            this.blocklevel = levelled.getLevel();

        }
        if (!isRestoredBlock(block)) {
            restoreData.add(this);
            setRestoreData();
        }
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material mat) {
        this.material = mat;
    }

    public Material getOldID() {
        return oldMaterial;
    }

    public void setOldMaterial(Material mat) {
        this.oldMaterial = mat;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public byte getOldData() {
        return oldData;
    }

    public void setOldData(byte data) {
        this.oldData = data;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    @SuppressWarnings("deprecation")
    public void setRestoreData() {
        getBlock().setType(material);
    }

    @SuppressWarnings("deprecation")
    public void restore() {
        getBlock().setType(getOldID());
        if(getBlock().getBlockData() instanceof Levelled){
            Levelled levelled = (Levelled) getBlock().getBlockData();
            levelled.setLevel(blocklevel);
            getBlock().setBlockData(levelled);
        }
    }

    public void update(Block block, Material mat, byte data, long expire) {
        setBlock(block);
        setMaterial(mat);
        setData(data);
        setExpire(expire);


    }

    public static BlockRestoreData getRestoreData(Block block) {
        Iterator<BlockRestoreData> iterator = restoreData.iterator();
        while (iterator.hasNext()) {
            BlockRestoreData data = iterator.next();

            if (block.equals(data.getBlock())) {
                return data;
            }
        }
        return null;
    }

    public static boolean isRestoredBlock(Block block) {
        for (BlockRestoreData data : restoreData) {
            if (data.getBlock().equals(block)) {
                return true;
            }
        }
        return false;
    }
}
