package main;

import data.Queues;
import parser.SpiderWorker;

/**
 * Created by Deadmist on 31.08.2015.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Queues.addUrlToProcess("https://xkcd.com/");
        SpiderWorker worker = new SpiderWorker();

        worker.start();

        Thread.sleep(20000);
        worker.interrupt();

        worker.join();

    }

}
