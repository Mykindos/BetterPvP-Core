package net.betterpvp.core.utility.recharge;

import net.betterpvp.core.utility.UtilTime;

public class Recharge {

    private String ability;

    private double seconds;
    private long systime;
    private boolean removeOnDeath;
    private boolean inform;
    private boolean cancellable;

    public Recharge(String ability, double d, long systime, boolean removeOnDeath, boolean inform) {
        this.ability = ability;
        this.seconds = d * 1000.0; // Convert to milliseconds
        this.systime = systime;
        this.removeOnDeath = removeOnDeath;
        this.inform = inform;
    }

    public Recharge(String ability, double d, long systime, boolean removeOnDeath, boolean inform, boolean cancellable) {
        this.ability = ability;
        this.seconds = d * 1000.0; // Convert to milliseconds
        this.systime = systime;
        this.removeOnDeath = removeOnDeath;
        this.inform = inform;
        this.cancellable = cancellable;
    }

    /**
     * @return The name of the ability
     */
    public String getAbility() {
        return ability;
    }

    public boolean isCancellable() {
        return cancellable;
    }


    /**
     * @return Cooldown in milliseconds
     */
    public double getSeconds() {
        return seconds;
    }

    public long getSystemTime() {
        return systime;
    }

    /**
     * @return Whether or not the cooldown is removed from the player on death
     */
    public boolean isRemoveOnDeath() {
        return removeOnDeath;
    }

    public boolean getInform() {
        return inform;
    }

    public double getRemaining() {

        return UtilTime.convert((getSeconds() + getSystemTime()) - System.currentTimeMillis(), UtilTime.TimeUnit.SECONDS, 1);

    }


}
