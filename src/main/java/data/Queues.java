package data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Deadmist on 30/08/15.
 */
public class Queues {
    private static LinkedBlockingQueue<String> unprocessed;     //Urls that need to be visited
    private static LinkedBlockingQueue<Results> finished;       //Resutls from visits
    private static Set<String> ignore;                      //Urls that have already been visited or are otherwise ignored

    static {
        unprocessed = new LinkedBlockingQueue<String>();
        finished = new LinkedBlockingQueue<Results>();
        ignore = Collections.synchronizedSet(new HashSet<String>());
    }

    public static Results getResult() {
        return finished.poll();
    }

    public static void addResult(Results result) {
        try {
            ignore.add(result.getUrl());
            finished.put(result);
        } catch (InterruptedException e) {
            System.out.printf("Could not add %s to the finished queue because we were interrupted\n", result.getUrl());
            e.printStackTrace();
        }
    }

    public static boolean isUrlIgnored(String url) {
        return ignore.contains(url);
    }

    public static String getUrlToProcess() {
        return unprocessed.poll();
    }

    public static void addUrlToProcess(String url) {
        try {
            unprocessed.put(url);
        } catch (InterruptedException e) {
            System.out.printf("Could not add %s to the processsing queue because we were interrupted\n", url);
            e.printStackTrace();
        }
    }

    public static boolean unprocessedQueueEmpty() {
        return unprocessed.isEmpty();
    }

}
