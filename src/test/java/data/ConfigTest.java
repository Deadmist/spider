package data;

import org.junit.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

/**
 * Created by Deadmist on 01.09.2015.
 */
public class ConfigTest {

    static String testConfig = "testConfig.json";

    @BeforeClass
    public static void setUp() throws Exception {
        try(BufferedWriter w = new BufferedWriter(new FileWriter(testConfig))) {
            w.write("{ \"database\" : { \"address\" : \"test.de\", \"user\" : \"deadmist\", \"password\" : \"n4u\", \"port\" : 1337}}");
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        new File(testConfig).delete();
    }

    @Test
    public void testLoadConfig() throws Exception {
        Config.setConfigFile(testConfig);
        Config.loadConfig();

        assertEquals(Config.getDatabaseAddress(), "test.de");
        assertEquals(Config.getDatabasePassword(), "n4u");
        assertEquals(Config.getDatabasePort(), 1337);
        assertEquals(Config.getDatabaseUsername(), "deadmist");

    }
}