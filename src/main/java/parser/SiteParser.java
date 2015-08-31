package parser;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Deadmist on 30/08/15.
 */
public class SiteParser {

    public static void fetchUrl(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).userAgent("Spider").get();
            Elements links = doc.select("a[href]");
            System.out.printf("Links:\n");
            for (Element link : links) {
                System.out.printf("%s (%s)\n", link.attr("abs:href"), link.text());
            }
        } catch (MalformedURLException ex) {
            System.out.printf("%s was malformed\n", url);
        } catch (HttpStatusException ex) {
            System.out.printf("%s returned %s", ex.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
