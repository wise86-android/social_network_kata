package wise86.socialNetworkKata.testUtil;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;

/**
 * Matcher that check that a string contains all the substring in the same order as the one
 * that was used when build the matcher
 */
public class ContainsSubStringInOrderMatcher extends TypeSafeMatcher<String> {

    private String matches[];

    /**
     * @param matches substrings searched in the string
     */
    private ContainsSubStringInOrderMatcher(String... matches) {
        this.matches = matches;
    }


    @Override
    protected boolean matchesSafely(String item) {
        int lastMatch = -1;
        for (String match : matches) {
            //search match starting from the fist character after the last match
            lastMatch = item.indexOf(match, lastMatch + 1);
            if (lastMatch == -1) // no match
                return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("doesn't not contains the string: " + Arrays.toString(matches) + "in this order");
    }

    public static Matcher<String> containsInOrder(String... matches) {
        return new ContainsSubStringInOrderMatcher(matches);
    }

}
