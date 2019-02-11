package net.betterpvp.core.utility;

import java.text.DecimalFormat;

public class UtilTime {

	/**
	 * Check if a specified amount of time has elapsed from a certain point
	 * @param from Time you are checking from
	 * @param required Amount of time elapsed
	 * @return Returns true if the amount of time has elapsed since the defined point
	 */
    public static boolean elapsed(long from, long required) {
        return System.currentTimeMillis() - from > required;
    }

    public static double trim(double untrimmeded, double d) {
        String format = "#.#";

        for (int i = 1; i < d; i++) {
            format = format + "#";
        }
        DecimalFormat twoDec = new DecimalFormat(format);
        return Double.valueOf(twoDec.format(untrimmeded));
    }

    public static double convert(double d, TimeUnit unit, int decPoint) {
        if (unit == TimeUnit.BEST) {
            if (d < 60000L) {
                unit = TimeUnit.SECONDS;
            } else if (d < 3600000L) {
                unit = TimeUnit.MINUTES;
            } else if (d < 86400000L) {
                unit = TimeUnit.HOURS;
            } else if (d < 31536000000L){
                unit = TimeUnit.DAYS;
            }else{
            	unit = TimeUnit.YEARS;
            }
        }
        if (unit == TimeUnit.SECONDS) {
            return trim(d / 1000.0D, decPoint);
        }
        if (unit == TimeUnit.MINUTES) {
            return trim(d / 60000.0D, decPoint);
        }
        if (unit == TimeUnit.HOURS) {
            return trim(d / 3600000.0D, decPoint);
        }
        if (unit == TimeUnit.DAYS) {
            return trim(d / 86400000, decPoint);
        }
        if (unit == TimeUnit.YEARS){
        	return trim(d / 31536000000.0D, decPoint);
        }
        return trim(d, decPoint);
    }

    public static String getTimeUnit2(double d) {
        if (d < 60000L) {
            return "Seconds";
        } else if (d >= 60000L && d <= 3600000L) {
            return "Minutes";
        } else if (d >= 3600000L && d <= 86400000L) {
            return "Hours";
        } else if (d >= 86400000L && d <= 31536000000L) {
            return "Days";
        }
        return "Years";
    }


    public static String getTimeUnit(String unit) {
    	switch(unit){
    	case "s":
    		return "Seconds";
    	case "m":
    		return "Minutes";
    	case "h":
    		return "Hours";
    	case "d":
    		return "Days";
    	case "y":
    		return "Years";
    	
    	}
    	
    	return "";
    }
    
    public static String getTimeUnit(TimeUnit unit) {
    	switch(unit){
    	case SECONDS:
    		return "Seconds";
    	case MINUTES:
    		return "Minutes";
    	case HOURS:
    		return "Hours";
    	case DAYS:
    		return "Days";
    	case YEARS:
    		return "Years";
    	
    	}
    	
    	return "";
    }
    
    public static String getTime(double d, TimeUnit unit, int decPoint) {
        return UtilTime.convert(d, TimeUnit.BEST, decPoint) + " "
                + UtilTime.getTimeUnit2(d);
    }

    public static String getTime2(double d, TimeUnit unit, int decPoint) {
        return UtilTime.convert(d, unit, decPoint) + " "
                + UtilTime.getTimeUnit(unit);
    }

    public enum TimeUnit {

        BEST, YEARS, DAYS, HOURS, MINUTES, SECONDS,
    }
}
