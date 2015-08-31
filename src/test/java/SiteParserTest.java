import org.junit.Test;
import parser.SiteParser;

/**
 * Created by Deadmist on 30/08/15.
 */
public class SiteParserTest {

    @Test
    public void testFetchUrl() throws Exception {
        SiteParser.fetchUrl("https://security.tfh-wildau.de/stundenplan/strpl/index.php");
    }
}