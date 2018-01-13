package wise86.socialNetworkKata.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * Print nicely the time pass from a reference time.
 * at example it show:
 * - after 10 seconds: 10 seconds ago
 * - after 66 seconds: 1 minute ago
 * - after 185 seconds: 3 minutes ago
 * - after 3645 seconds: 1 hour ago
 */
public class PassedTimeFormatter {
    private Date referenceTime;

    /**
     * enum containing the supported time units with its value in millisecond
     * and the string to show in the formatter output
     */
    private enum TimeUnit {
        SECOND(1000, "second"),
        MINUTE(60 * SECOND.inMilliSeconds, "minute"),
        HOUR(60 * MINUTE.inMilliSeconds, "hour"),
        DAY(24 * HOUR.inMilliSeconds, "day"),
        YEAR(365 * DAY.inMilliSeconds, "year");

        private final long inMilliSeconds;
        private final String unitName;

        TimeUnit(long conversionRate, String unitName) {
            inMilliSeconds = conversionRate;
            this.unitName = unitName;
        }

        long toMilliseconds() {
            return inMilliSeconds;
        }

        String getUnitNameSingle() {
            return unitName;
        }

        /**
         * build the plural version of the unit name
         *
         * @return plural version of the unit name
         */
        String getUnitNamePlural() {
            return unitName + "s";
        }
    }


    /**
     * build a formatter that will show the time difference from the reference time
     *
     * @param referenceTime reference time use to compute the time difference
     */
    public PassedTimeFormatter(Date referenceTime) {
        this.referenceTime = referenceTime;
    }

    /**
     * find the biggest unit that it is needed to represent the time
     *
     * @param milliseconds time to represent in milliseconds
     * @return biggest unit that it is needed to represent the time
     */
    private TimeUnit getBiggerUnitFitting(long milliseconds) {
        ArrayList<TimeUnit> units = new ArrayList<>(Arrays.asList(TimeUnit.values()));
        units.sort(Comparator.comparingLong(TimeUnit::toMilliseconds).reversed());
        for (TimeUnit unit : units) {
            if (milliseconds >= unit.toMilliseconds())
                return unit;
        }
        return units.get(units.size() - 1);
    }

    /**
     * format the difference between the reference time and the parameter.
     * Note: the reference time must be in the past respect the parameter
     *
     * @param date date
     * @return string representing the time difference
     */
    public String format(Date date) {
        long timeDiffMs = referenceTime.getTime() - date.getTime();
        TimeUnit unit = getBiggerUnitFitting(timeDiffMs);
        long timeUnit = timeDiffMs / unit.toMilliseconds();
        String unitName = unit.getUnitNamePlural();
        if (timeUnit == 1)
            unitName = unit.getUnitNameSingle();
        return timeUnit + " " + unitName + " ago";
    }
}

