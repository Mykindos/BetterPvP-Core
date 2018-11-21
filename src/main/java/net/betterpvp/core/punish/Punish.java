package net.betterpvp.core.punish;

import java.util.UUID;

import net.betterpvp.core.utility.UtilTime;

public class Punish {


	private UUID punished;
	private UUID punisher;
	private PunishType type;
	private long time;
	private String reason;

	public Punish(UUID punished, UUID punisher, PunishType type, long time, String reason) {
		this.punished = punished;
		this.punisher = punisher;
		this.type = type;
		this.time = time;
		this.reason = reason;
	}

	public UUID getPunished() {
		return punished;
	}

	public void setPunished(UUID punished) {
		this.punished = punished;
	}

	public UUID getPunisher() {
		return punisher;
	}

	public void setPunisher(UUID punisher) {
		this.punisher = punisher;
	}

	public PunishType getPunishType() {
		return type;
	}

	public void setPunishType(PunishType type) {
		this.type = type;
	}





	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemaining() {
		return UtilTime.getTime(getTime() - System.currentTimeMillis(), UtilTime.TimeUnit.BEST, 1);
	}

	

	public enum PunishType {

		MUTE, BAN, PERM_BAN, PERM_MUTE, PVPLock, BuildLock
    }

}
