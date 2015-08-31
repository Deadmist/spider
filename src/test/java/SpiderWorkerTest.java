import org.junit.Test;
import parser.SpiderWorker;

/**
 * Created by Deadmist on 30/08/15.
 */
public class SpiderWorkerTest {

    @Test
    public void testFetchUrl() throws Exception {
        new SpiderWorker().fetchUrl("https://security.tfh-wildau.de/stundenplan/strpl/index.php");
    }
}