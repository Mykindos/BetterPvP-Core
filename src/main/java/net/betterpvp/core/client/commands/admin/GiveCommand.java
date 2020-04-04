package net.betterpvp.core.client.commands.admin;

import com.google.common.base.Joiner;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilFormat;
import net.betterpvp.core.utility.UtilMath;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class GiveCommand extends Command {

    public GiveCommand() {
        super("give", new String[]{}, Rank.ADMIN);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(Player player, String[] args) {
        if (args == null || args.length == 0) {
            UtilMessage.message(player, "Give", "You did not input a Player.");
            return;
        }

        if (args.length == 1) {
            UtilMessage.message(player, "Give", "You did not input a Item.");
            return;
        }

        Player target = UtilPlayer.searchOnline(player, args[0], false);
        if (target != null) {
            Material material = Material.matchMaterial(args[1]);
          /*  if (material == null) {
                material = Bukkit.getUnsafe().getMaterialFromInternalName(args[1]);
            }*/

            if (material != null) {
                int amount = 1;
                short data = 0;
                if (args.length >= 3) {
                    amount = UtilMath.getInteger(args[2], 1, 1024);
                    if (args.length >= 4) {
                        try {
                            data = Short.parseShort(args[3]);
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                ItemStack stack = new ItemStack(material, amount, data);
                if (args.length >= 5) {
                    try {
                        stack = Bukkit.getUnsafe().modifyItemStack(stack, Joiner.on(' ').join(Arrays.asList(args).subList(4, args.length)));
                    } catch (Throwable ex) {
                        target.sendMessage("Not a valid tag");
                        return;
                    }
                }
                //target.getInventory().addItem(UtilItem.updateNames(stack));
                UtilMessage.broadcast("Give", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " gave " + ChatColor.YELLOW
                        + target.getName() + ChatColor.GREEN + " " + amount + " " + UtilFormat.cleanString(material.toString()));
            } else {
                player.sendMessage("There's no item called " + args[1]);
            }
        } else {
            player.sendMessage("Can't find target " + args[0]);
        }
    }

    @Override
    public void help(Player player) {

    }

}
