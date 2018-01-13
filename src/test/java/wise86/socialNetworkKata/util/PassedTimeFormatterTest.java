package wise86.socialNetworkKata.util;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PassedTimeFormatterTest {

    private static final long SECONDS_IN_MS = 1000;
    private static final long MINUTE_IN_MS = 60 * SECONDS_IN_MS;
    private static final long HOUR_IN_MS = 60 * MINUTE_IN_MS;
    private static final long DAY_IN_MS = 24 * HOUR_IN_MS;
    private static final long YEAR_IN_MS = 365 * DAY_IN_MS;


    @Test
    public void theConstructorAcceptTheReferenceTime() {
        new PassedTimeFormatter(new Date());
    }

    @Test
    public void after1SecPrint1SecondAgo() {
        PassedTimeFormatter f = new PassedTimeFormatter(new Date(1 * SECONDS_IN_MS));
        assertThat(f.format(new Date(0)), is("1 second ago"));
    }

    @Test
    public void after10SecPrint10SecondsAgo() {
        PassedTimeFormatter f = new PassedTimeFormatter(new Date(10 * SECONDS_IN_MS));
        assertThat(f.format(new Date(0)), is("10 seconds ago"));
    }

    @Test
    public void after60SecPrint1MinuteAgo() {
        PassedTimeFormatter f = new PassedTimeFormatter(new Date(MINUTE_IN_MS));
        assertThat(f.format(new Date(0)), is("1 minute ago"));
    }

    @Test
    public void after3MinPrint3MinuteAgo() {
        PassedTimeFormatter f = new PassedTimeFormatter(new Date(3 * MINUTE_IN_MS));
        assertThat(f.format(new Date(0)), is("3 minutes ago"));
    }

    @Test
    public void after60MinPrint1HourAgo() {
        PassedTimeFormatter f = new PassedTimeFormatter(new Date(60 * MINUTE_IN_MS));
        assertThat(f.format(new Date(0)), is("1 hour ago"));
    }

    @Test
    public void after3HoursPrint3HoursAgo() {
        PassedTimeFormatter f = new PassedTimeFormatter(new Date(3 * HOUR_IN_MS));
        assertThat(f.format(new Date(0)), is("3 hours ago"));
    }

    @Test
    public void after24HoursPrint1DayAgo() {
        PassedTimeFormatter f = new PassedTimeFormatter(new Date(1 * DAY_IN_MS));
        assertThat(f.format(new Date(0)), is("1 day ago"));
    }

    @Test
    public void after3DaysPrint3DayAgo() {
        PassedTimeFormatter f = new PassedTimeFormatter(new Date(3 * DAY_IN_MS));
        assertThat(f.format(new Date(0)), is("3 days ago"));
    }

    @Test
    public void after356DaysPrint1YearAgo() {
        PassedTimeFormatter f = new PassedTimeFormatter(new Date(365 * DAY_IN_MS));
        assertThat(f.format(new Date(0)), is("1 year ago"));
    }

    @Test
    public void after4YearsPrint1YearAgo() {
        PassedTimeFormatter f = new PassedTimeFormatter(new Date(4 * YEAR_IN_MS));
        assertThat(f.format(new Date(0)), is("4 years ago"));
    }

}
