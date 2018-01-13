package wise86.socialNetworkKata.util;

import java.util.Date;

/**
 * Simple class used to set the date during the test mocking the now method
 */
public class DateProvider {

    /**
     * create a date representing the current system time.
     * @return get the current time
     */
    public Date now() {
        return new Date();
    }

}
