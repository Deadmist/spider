package parser;

import data.Queues;
import data.Results;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by Deadmist on 30/08/15.
 */
public class SpiderWorker extends Thread {

    private boolean interrupted;

    @Override
    public void run() {
        runWorker();
    }

    @Override
    public void interrupt() {
        super.interrupt();
        this.interrupted = true;
    }

    private void runWorker() {
        while (!this.interrupted) {
            try {
                String url = Queues.getUrlToProcess();
                if (url != null) {
                    ArrayList<String> links = fetchUrl(url);
                    if (links == null) continue;
                    System.out.printf("Found %d on %s\n", links.size(), url);
                    for (String l : links) {
                        Queues.addUrlToProcess(l);
                    }
                    Queues.addResult(new Results(url, null, null));
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                this.interrupted = true;
            }
        }
    }

    public ArrayList<String> fetchUrl(String url) {
        Document doc;
        ArrayList<String> re = new ArrayList<String>();
        try {
            doc = Jsoup.connect(url).userAgent("Spider").get();
            Elements links = doc.select("a[href]");
            //System.out.printf("Links:\n");
            for (Element link : links) {
                re.add(link.attr("abs:href"));
                //System.out.printf("%s (%s)\n", link.attr("abs:href"), link.text());
            }
            return re;
        } catch (MalformedURLException ex) {
            System.out.printf("%s was malformed\n", url);
        } catch (HttpStatusException ex) {
            System.out.printf("%s returned %s", url, ex.toString());
        } catch (IOException e) {
            System.out.printf("IOException fetching %s\n", url);
        } catch (Exception e) {
            System.out.printf("Uncaught exception %s\n", e.getMessage());
        }
        return null;
    }

}
