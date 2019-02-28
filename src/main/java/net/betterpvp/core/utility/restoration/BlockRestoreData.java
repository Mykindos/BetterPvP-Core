package net.betterpvp.core.utility.restoration;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BlockRestoreData {

    public static List<BlockRestoreData> restoreData = new ArrayList<BlockRestoreData>();

    private Block block;
    private int id;
    private int oldID;
    private byte data;
    private byte oldData;
    private long expire;

    @SuppressWarnings("deprecation")
    public BlockRestoreData(Block block, int id, byte data, long expire) {
        this.block = block;
        this.id = id;
        this.oldID = block.getTypeId();
        this.data = data;
        this.oldData = block.getData();
        this.expire = System.currentTimeMillis() + expire;
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

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getOldID() {
        return oldID;
    }

    public void setOldID(int id) {
        this.oldID = id;
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
        getBlock().setTypeIdAndData(getID(), getData(), true);
    }

    @SuppressWarnings("deprecation")
    public void restore() {
        getBlock().setTypeIdAndData(getOldID(), getOldData(), true);
    }

    public void update(Block block, int id, byte data, long expire) {
        setBlock(block);
        setID(id);
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
