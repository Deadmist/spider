package data;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Deadmist on 30/08/15.
 */
public class Queues {
    private static LinkedBlockingQueue<String> unprocessed;
    private static LinkedBlockingQueue<Results> finished;

    static {
        unprocessed = new LinkedBlockingQueue<String>();
        finished = new LinkedBlockingQueue<Results>();
    }

    public Results getResult() {
        return finished.poll();
    }

    public void addResult(Results result) {
        try {
            finished.put(result);
        } catch (InterruptedException e) {
            System.out.printf("Could not add %s to the finished queue because we were interrupted\n", result.getUrl());
            e.printStackTrace();
        }
    }

    public String getUrlToProcess() {
        return unprocessed.poll();
    }

    public void addUrlToProcess(String url) {
        try {
            unprocessed.put(url);
        } catch (InterruptedException e) {
            System.out.printf("Could not add %s to the processsing queue because we were interrupted\n", url);
            e.printStackTrace();
        }
    }

    public boolean unprocessedQueueEmpty() {
        return unprocessed.isEmpty();
    }

}
