package neo4j;

import data.Config;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by Deadmist on 10/09/15.
 */
public class Neo4jTest {

    @BeforeClass
    public static void setUpClass() throws KeyManagementException, NoSuchAlgorithmException {
        enableTrustingAllCerts();
    }

    @Before
    public void setUp() {
        Config.loadConfig();
    }

    @Test
    public void testStringQueryDB() throws Exception {
        System.out.println(Neo4j.stringQueryDB("Match n return n"));
    }

    /**
     * This is useful if you don't have a valid certificate for your testing
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static void enableTrustingAllCerts() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = (hostname, session) -> true;

        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

    }

}