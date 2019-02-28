package net.betterpvp.core.utility;

import java.util.HashMap;

public abstract class UtilInvite {

    public static HashMap<Object, Object> invites = new HashMap<Object, Object>();


    /**
     * Check if player has an active invite to a clan
     *
     * @param joining Clan joining
     * @param invitee Player attempting to join
     * @return Returns true if the player has an active invite to a clan
     */
    public boolean hasInvite(Object joining, Object invitee) {
        if (invites.containsKey(invitee)) {
            return invites.get(invitee).equals(joining);
        }
        return false;
    }


    /**
     * Sends invite to player from a specific clan
     *
     * @param joining Clan sending invite
     * @param invitee Player receiving invite
     */
    public void sendInvite(Object joining, Object invitee) {
        invites.put(invitee, joining);
        System.out.println("ADDED " + invitee + "   , " + joining);
    }

    /**
     * Called when a player accepts an invite to a clan
     * Will move to an event based system in the future
     *
     * @param joining Clan to join
     * @param invitee Player joining
     */
    public void acceptInvite(Object joining, Object invitee) {
        System.out.println("accept " + joining + "   , " + invitee);
        if (invites.containsKey(invitee)) {
            System.out.println("CONTAINS " + invitee);

            if (invites.get(invitee).equals(joining)) {
                System.out.println("EQUALS " + joining);
                onAccept(joining, invitee);
                invites.remove(invitee);

            }
        }
    }

    public abstract void onAccept(Object joining, Object invitee);
}
