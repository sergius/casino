package casino.game.strings;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResultFormatterTest {

    @Test
    public void should_return_normalized_string_from_exceeding_one() {
        final String input = "justateststringlongenough";
        final int expectedLength = 10;

        final StringBuilder testString = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            testString.append("g");
        }

        String result = ResultFormatter.normalizeToLength(testString.toString(), expectedLength);

        assertThat(result.length(), is(expectedLength));
        assertThat(result, is(testString.substring(0, expectedLength)));
    }

    @Test
    public void should_return_a_string_completed_with_spaces_when_input_short() {
        final String testString = "j";
        final int expectedLength = 10;

        String result = ResultFormatter.normalizeToLength(testString, expectedLength);

        assertThat(result.length(), is(expectedLength));
        assertThat(result.indexOf(testString), is(0));
    }

}
