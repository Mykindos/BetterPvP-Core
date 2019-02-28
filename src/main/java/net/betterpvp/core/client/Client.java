package net.betterpvp.core.client;

import net.betterpvp.core.punish.Punish;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {

    private UUID uuid;

    private String name, oldName = "";
    private Rank rank;
    private DonationRank donationRank;
    private String ip;
    private long timeJoined;
    private boolean mahRequired = false, mahBypass = false;
    private List<UUID> ignore;
    private List<Punish> punishments;
    private boolean administrating;
    private long lastLogin;
    private String password;
    public boolean loggedIn;

    private boolean showSidebar = true;
    private boolean showKillfeed = true;
    private boolean showPms = true;
    private int timePlayed = 0;
    private long lastClick = 0;
    private String discordID;
    private boolean kitRedeemed;
    private boolean showWarnings;

    public Client(UUID uuid) {
        this.uuid = uuid;

        this.rank = Rank.PLAYER;
        this.ignore = new ArrayList<>();
        this.punishments = new ArrayList<>();
        this.administrating = false;
        this.lastLogin = 0;
        this.password = "";

        this.discordID = "";
        this.kitRedeemed = false;
        this.showWarnings = false;
    }

    public void setKitRedeemed(boolean b) {
        this.kitRedeemed = b;
    }

    public boolean isKitRedeemed() {
        return kitRedeemed;
    }

    public void setShowWarnings(boolean b) {
        this.showWarnings = b;
    }

    public boolean isShowingWarnings() {
        return showWarnings;
    }


    public void setTimePlayed(int played) {
        this.timePlayed = played;
    }

    public void setDiscordID(String id) {
        this.discordID = id;
    }

    public boolean isDiscordLinked() {
        return !discordID.equalsIgnoreCase("");
    }

    public String getDiscordID() {
        return discordID;
    }

    public int getTimePlayed() {
        return timePlayed;
    }


    public long getLastLogin() {
        return lastLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String s) {
        this.password = s;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean b) {
        this.loggedIn = b;
    }

    public void setLastLogin(long l) {
        this.lastLogin = l;
    }


    public void setTimeJoined(long l) {
        this.timeJoined = l;
    }

    public long getTimeJoined() {
        return timeJoined;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public long getLastClick() {
        return lastClick;
    }

    public void setLastClick(long a) {
        lastClick = a;
    }

    public void setOldName(String str) {
        this.oldName = str;
    }

    public String getOldName() {
        return this.oldName;
    }


    /*
     * Returns a player value of the client.
     */

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getUUID());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRank() {
        return rank;
    }

    public DonationRank getDonationRank() {
        return donationRank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setDonationRank(DonationRank rank) {
        if (rank == null) {
            donationRank = null;
            return;
        }
        if (donationRank == null) {
            donationRank = rank;
            return;
        }
        if (donationRank.getId() < rank.getId()) {
            this.donationRank = rank;
        }

    }

    public String getIP() {
        return ip;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public List<UUID> getIgnore() {
        return ignore;
    }

    public void setIgnore(List<UUID> ignore) {
        this.ignore = ignore;
    }

    public List<Punish> getPunishments() {
        return punishments;
    }

    public void setPunishments(List<Punish> punishments) {
        this.punishments = punishments;
    }

    public boolean isAdministrating() {
        return administrating;
    }

    public void setAdministrating(boolean administrating) {
        this.administrating = administrating;
    }


    public boolean hasRank(Rank rank, boolean inform) {
        if (getRank().toInt() >= rank.toInt()) {
            return true;
        }

        if (inform) {
            UtilMessage.message(Bukkit.getPlayer(getUUID()), "Permissions", "The requires Permission Rank [" + rank.getTag(false) + ChatColor.GRAY + "].");
        }
        return false;
    }
}
