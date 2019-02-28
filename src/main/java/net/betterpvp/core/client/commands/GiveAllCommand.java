package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilFormat;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveAllCommand extends Command {

    public GiveAllCommand() {
        super("giveall", new String[]{}, Rank.ADMIN);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(Player player, String[] args) {
        if (args == null || args.length == 0) {
            UtilMessage.message(player, "Give", "You did not input a Item.");
            return;
        }

        Material material = Material.matchMaterial(args[0]);
        if (material == null) {
            material = Bukkit.getUnsafe().getMaterialFromInternalName(args[0]);
        }

        if (material != null) {
            if (args.length == 2) {
                int amount = Integer.valueOf(args[1]);
                ItemStack stack = new ItemStack(material);
                stack.setAmount(amount);

                for (Player target : Bukkit.getOnlinePlayers()) {

                    //	target.getInventory().addItem(UtilItem.updateNames(stack.clone()));
                }
                UtilMessage.broadcast("Give", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " gave everyone"
                        + ChatColor.GREEN + " " + amount + " " + UtilFormat.cleanString(material.toString()));
            }
        } else {
            player.sendMessage("There's no item called " + args[1]);
        }
    }

    @Override
    public void help(Player player) {

    }

}
