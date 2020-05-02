package net.betterpvp.core.client.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.betterpvp.core.Core;
import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.client.mysql.ClientRepository;
import net.betterpvp.core.framework.BPVPListener;
import net.betterpvp.core.framework.UpdateEvent;
import net.betterpvp.core.framework.UpdateEvent.UpdateType;
import net.betterpvp.core.utility.Titles;
import net.betterpvp.core.utility.UtilProxy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ConnectionListener extends BPVPListener<Core> {


    public ConnectionListener(Core i) {
        super(i);
    }

    public static List<String> JOIN_ATTEMPTS = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void handleKicks(PlayerLoginEvent e) {
        if (!getInstance().hasStarted()) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Server has not finished starting up!");
            return;
        }

        if (!JOIN_ATTEMPTS.contains(e.getPlayer().getName())) {
            JOIN_ATTEMPTS.add(e.getPlayer().getName());
        }

        if (Bukkit.hasWhitelist()) {
            Player player = e.getPlayer();
            if (!e.getPlayer().isWhitelisted()) {

                Client client = ClientUtilities.getClient(player.getUniqueId());
                if (client == null) {
                    client = new Client(player.getUniqueId());
                    client.setName(player.getName());
                    client.setIP(player.getAddress().getAddress().getHostAddress());

                    ClientUtilities.addClient(client);
                    ClientRepository.saveClient(client);

                }

                Bukkit.getPluginManager().callEvent(new ClientLoginEvent(client));


            }
        }

    }


    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {

            // Alternate spawns
            if (Bukkit.getOnlinePlayers().size() % 2 == 0) {
                player.teleport(Core.getOptions().getSpawnA());
            } else {
                player.teleport(Core.getOptions().getSpawnB());

            }
        }


        PermissionAttachment attachment = player.addAttachment(getInstance());
        attachment.setPermission("bukkit.command.version", false);
        attachment.setPermission("bukkit.command.help", false);
        attachment.setPermission("safetrade.request", true);
        attachment.setPermission("safetrade.accept", true);
        attachment.setPermission("safetrade.deny", true);
        attachment.unsetPermission("worldedit.calc"); // Server crash

        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        double baseValue = attribute.getBaseValue();

        if (baseValue != 16) {
            attribute.setBaseValue(16);
            player.saveData();
        }

        updateTab(player);


        boolean isNew = false;
        if (!ClientUtilities.isClient(player.getUniqueId())) {
            isNew = true;

            Client client = new Client(player.getUniqueId());
            client.setName(player.getName());
            client.setIP(player.getAddress().getAddress().getHostAddress());

            ClientUtilities.addClient(client);
            ClientRepository.saveClient(client);

            event.setJoinMessage(ChatColor.GREEN + "New> " + ChatColor.GRAY + player.getName());
            Titles.sendTitle(player, 0, 40, 20, ChatColor.YELLOW + "Welcome to " + ChatColor.RED.toString() + ChatColor.BOLD + "Clans!",
                    ChatColor.YELLOW + "");


            addOnlineClient(player);

        } else {

            Client client = ClientUtilities.getClient(player);
            client.setName(player.getName());
            ClientRepository.updateName(player);

            if (!client.getName().equals(player.getName())) {
                client.setOldName(client.getName());
                ClientRepository.updatePreviousName(player);

                ClientRepository.updateName(player);
            }

            if (!client.getIP().equalsIgnoreCase(player.getAddress().getAddress().getHostAddress())) {
                client.setIP(player.getAddress().getAddress().getHostAddress());
                ClientRepository.updateIP(player);
            }


            event.setJoinMessage(ChatColor.GREEN + "Login> " + ChatColor.GRAY + player.getName());
            //client.getGamer().setSafeLogged(false);

            addOnlineClient(player);


        }

        ClientLoginEvent cle = new ClientLoginEvent(ClientUtilities.getOnlineClient(player));
        cle.setNewClient(isNew);

        Bukkit.getPluginManager().callEvent(cle);

        new Thread(() -> {
            if (UtilProxy.isUsingProxy(player)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ClientUtilities.messageStaff("Proxy", player.getName() + " may be using a VPN / Proxy", Rank.ADMIN);
                    }
                }.runTask(getInstance());
            }
        }).start();
    }


    @EventHandler
    public void onClientLogin(ClientLoginEvent e) {
        Player p = Bukkit.getPlayer(e.getClient().getUUID());
        if (p != null) {

        }

    }


    public static int getPing(Player player) {
        try {

            Method getHandleMethod = player.getClass().getDeclaredMethod("getHandle");
            Object entityPlayer = getHandleMethod.invoke(player);
            Field pingField = entityPlayer.getClass().getDeclaredField("ping");
            pingField.setAccessible(true);

            int ping = pingField.getInt(entityPlayer);

            return ping > 0 ? ping : 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public void updateTab(Player p) {

        PacketContainer pc = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        pc.getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.RED + "" + ChatColor.BOLD + "Welcome to BetterPvP Clans!\n"
                + ChatColor.RED + "" + ChatColor.BOLD + "Visit our website at: " + ChatColor.YELLOW + ChatColor.BOLD + "http://betterpvp.net"))
                .write(1, WrappedChatComponent.fromText(ChatColor.GOLD.toString() + ChatColor.BOLD + "Ping: " + ChatColor.YELLOW + getPing(p)
                        + ChatColor.GOLD.toString() + ChatColor.BOLD + " Online: " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size()));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, pc);
        } catch (InvocationTargetException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }


    @EventHandler
    public void updateTabMenu(UpdateEvent e) {
        if (e.getType() == UpdateType.SLOWER) {

            for (Player p : Bukkit.getOnlinePlayers()) {
                updateTab(p);
            }
        }
    }


    public void addOnlineClient(Player p) {
        for (Client c : ClientUtilities.getOnlineClients()) {
            if (c.getUUID().equals(p.getUniqueId())) {
                return;
            }
        }

        Client c = ClientUtilities.getClient(p);
        if (c != null) {
            ClientUtilities.addOnlineClient(c);
            System.out.println("Added Online Client: " + p.getName());
        }
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Client client = ClientUtilities.getOnlineClient(event.getPlayer());

        if (client != null) {
            Bukkit.getPluginManager().callEvent(new ClientQuitEvent(client));
        }

        Player player = event.getPlayer();

        if (String.valueOf(player.getLocation().getX()).equalsIgnoreCase("NaN")) {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }


    }


}


