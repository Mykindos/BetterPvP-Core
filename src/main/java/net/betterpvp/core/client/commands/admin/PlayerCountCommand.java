package net.betterpvp.core.client.commands.admin;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerCountCommand extends Command implements Listener {

    public PlayerCountCommand() {
        super("playercount", new String[]{}, Rank.ADMIN);

    }

    private int count = 0;

    @Override
    public void execute(Player player, String[] args) {
        player.sendMessage(count + "");

    }

    @Override
    public void help(Player player) {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (Bukkit.getOnlinePlayers().size() > count) {
            count = Bukkit.getOnlinePlayers().size();
        }
    }
}
