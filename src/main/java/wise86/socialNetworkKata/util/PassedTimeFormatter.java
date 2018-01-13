package wise86.socialNetworkKata.util;

import java.util.*;

public class PassedTimeFormatter {
    private Date referenceTime;

    private enum TimeUnit {
        SECONDS(100),
        MINUTE(60*SECONDS.inMilliSeconds),
        HOURS(60*MINUTE.inMilliSeconds),
        DAY(24*HOURS.inMilliSeconds),
        YEARS(365*DAY.inMilliSeconds);

        private final long inMilliSeconds;

        TimeUnit(long conversionRate){
            inMilliSeconds=conversionRate;
        }

        long toMilliseconds(){
            return inMilliSeconds;
        }
    }

    private static String getSingleUnitString(TimeUnit unit) {
        switch (unit) {
            case SECONDS:
                return "second";
            case MINUTE:
                return "minute";
            case HOURS:
                return "hour";
            case DAY:
                return "day";
            case YEARS:
                return "year";
        }
        return null;
    }

    private static String getPluralUnitString(TimeUnit unit) {
        return getSingleUnitString(unit)+"s";
    }


    public PassedTimeFormatter(Date referenceTime) {
        this.referenceTime = referenceTime;
    }

    private TimeUnit getBiggerUnitFitting(long milliseconds){
        ArrayList<TimeUnit> units = new ArrayList<>(Arrays.asList(TimeUnit.values()));
        units.sort(Comparator.comparingLong(TimeUnit::toMilliseconds).reversed());
        for (TimeUnit unit : units){
            if(milliseconds>=unit.toMilliseconds())
                return unit;
        }
        return units.get(units.size()-1);
    }

    public String format(Date date){
        long timeDiffMs = referenceTime.getTime()-date.getTime();
        TimeUnit unit = getBiggerUnitFitting(timeDiffMs);
        long timeUnit = timeDiffMs/unit.toMilliseconds();
        String unitName = getPluralUnitString(unit);
        if(timeUnit==1)
            unitName = getSingleUnitString(unit);
        return timeUnit + " " + unitName + " ago";
    }
}

