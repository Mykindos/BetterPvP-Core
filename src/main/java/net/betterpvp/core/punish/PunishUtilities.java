package net.betterpvp.core.punish;

public class PunishUtilities {

	public static Long getProperLength(Long time, String unit){
		Long l = time;
		switch(unit.toLowerCase()){
		case "m":
			return time * 60000;
		case "h":
			return time * 3600000;
		case "s":
			return time * 1000;
		case "d":
			return time * 87400000;
		case "y":
			return time * 31536000000L;
		}
		return l;
	}


}
