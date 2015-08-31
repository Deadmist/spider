package main;

import data.Queues;
import parser.SpiderWorker;

/**
 * Created by Deadmist on 31.08.2015.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Queues.addUrlToProcess("https://www.reddit.com/r/Eve");
        SpiderWorker[] workers = new SpiderWorker[10];

        for(int i = 0; i < workers.length; i++) {
            workers[i] = new SpiderWorker();
            workers[i].start();
            System.out.printf("Started worker %d\n", i);
            Thread.sleep(200);
        }

        Thread.sleep(6000);

        for(int i = 0; i < workers.length; i++) {
            workers[i].interrupt();
        }
        for(int i = 0; i < workers.length; i++) {
            workers[i].join();
        }

        System.out.printf("#########################################################################\n" +
                "STOPPED CRAWLING\n" +
                "URLS FOUND:\n" +
                "%s\n", Queues.allUrls());

    }

}
