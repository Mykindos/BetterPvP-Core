package net.betterpvp.core.client.commands.login;

import org.bukkit.entity.Player;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.recharge.RechargeManager;

public class LoginCommand extends Command{

	public LoginCommand() {
		super("login", new String[]{}, Rank.PLAYER);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player player, String[] args) {
		Client c = ClientUtilities.getOnlineClient(player);
		if(args != null){
			if(args.length == 1){
				if(RechargeManager.getInstance().add(player, "Login", 10, true)){
					if(args[0].equalsIgnoreCase(c.getPassword())){
						c.setLoggedIn(true);
						UtilMessage.message(player, "Account", "You have logged in succesfully");
					}
				}
					/* Dedicated servers only.
					GoogleAuthenticator gAuth = new GoogleAuthenticator();
					if(gAuth.authorize(c.getPassword(), Integer.valueOf(args[0]))){
					

							c.setLoggedIn(true);
							UtilMessage.message(player, "Account", "You have logged in succesfully");
						
					}
				}
				*/
			}else if(args.length == 2){
				if(c.isLoggedIn()){
					Client cd = ClientUtilities.getClient(args[0]);
					if(cd != null){
						if(cd.getPassword().equalsIgnoreCase(args[1])){
							cd.setLoggedIn(true);
						}
					}
				}
			}
		}
	}

	@Override
	public void help(Player player) {
	}

}
