package net.betterpvp.core.client.commands.admin;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class UnforceChunks extends Command {

    public UnforceChunks() {
        super("unforcechunks", new String[]{}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        for(World w : Bukkit.getWorlds()){
            for(Chunk c : w.getLoadedChunks()){
                c.setForceLoaded(false);
                c.unload();
            }
        }
    }

    @Override
    public void help(Player player) {

    }
}
