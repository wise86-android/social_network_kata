package wise86.socialNetworkKata.testUtil;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static wise86.socialNetworkKata.testUtil.ContainsSubStringInOrderMatcher.containsInOrder;

public class ContainsSubStringInOrderMatcherTest {

    @Test
    public void retrunTrueIfTheStringMatchTheSubstringWithTheSameOrder() {
        assertThat("Hello World", containsInOrder("Hello", "World"));
        assertThat("aba", containsInOrder("a", "b", "a"));
        assertThat("Hello world", not(containsInOrder("Hello", "World")));
    }

}
