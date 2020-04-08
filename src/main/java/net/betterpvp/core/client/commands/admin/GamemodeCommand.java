package net.betterpvp.core.client.commands.admin;

import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMath;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class GamemodeCommand extends Command {

    public GamemodeCommand() {
        super("gamemode", new String[]{"gm"}, Rank.PLAYER);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args != null) {
            if(ClientUtilities.getOnlineClient(player).hasRank(Rank.ADMIN, false) || player.isOp()) {
                if (isNumeric(args[0])) {

                    int gamemode = Integer.valueOf(args[0]);

                    switch (gamemode) {
                        case 0:
                            player.setGameMode(GameMode.SURVIVAL);
                            break;
                        case 1:
                            player.setGameMode(GameMode.CREATIVE);
                            break;
                        case 2:
                            player.setGameMode(GameMode.ADVENTURE);
                            break;
                        case 3:
                            player.setGameMode(GameMode.SPECTATOR);
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void help(Player player) {

    }

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
