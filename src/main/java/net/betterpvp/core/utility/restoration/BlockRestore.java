package net.betterpvp.core.utility.restoration;


import net.betterpvp.core.Core;
import net.betterpvp.core.framework.BPVPListener;
import net.betterpvp.core.framework.UpdateEvent;
import net.betterpvp.core.framework.UpdateEvent.UpdateType;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;

import java.util.Iterator;

public class BlockRestore extends BPVPListener<Core> {

    public BlockRestore(Core i) {
        super(i);
        // TODO Auto-generated constructor stub
    }

    @EventHandler
    public void restoreBlockData(UpdateEvent event) {
        if (event.getType() == UpdateType.TICK) {
            if (BlockRestoreData.restoreData.isEmpty()) return;
            Iterator<BlockRestoreData> iterator = BlockRestoreData.restoreData.iterator();
            while (iterator.hasNext()) {
                BlockRestoreData data = iterator.next();

                if (System.currentTimeMillis() >= data.getExpire()) {
                    data.restore();
                    iterator.remove();
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (BlockRestoreData.isRestoredBlock(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMelt2(BlockFadeEvent e) {
        if (BlockRestoreData.isRestoredBlock(e.getBlock())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMelt(BlockFromToEvent e) {
        if (e.getToBlock().getType() == Material.WATER) {
            if (BlockRestoreData.isRestoredBlock(e.getBlock())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (BlockRestoreData.isRestoredBlock(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void Piston(BlockPistonExtendEvent event) {
        Block push = event.getBlock();
        for (int i = 0; i < 13; i++) {
            push = push.getRelative(event.getDirection());
            if (push.getType() == Material.AIR) {
                return;
            }

            if (BlockRestoreData.isRestoredBlock(push)) {
                push.getWorld().playEffect(push.getLocation(), Effect.STEP_SOUND, push.getType().getId());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void Piston2(BlockPistonRetractEvent e) {
        Block retract = e.getBlock();
        retract = retract.getRelative(e.getDirection());
        if (retract.getType() == Material.AIR) {
            return;
        }

        if (BlockRestoreData.isRestoredBlock(retract)) {
            retract.getWorld().playEffect(retract.getLocation(), Effect.STEP_SOUND, retract.getType().getId());
            e.setCancelled(true);
        }
    }
}
