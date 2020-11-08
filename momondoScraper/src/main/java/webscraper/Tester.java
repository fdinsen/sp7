package webscraper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//import jdk.jshell.spi.ExecutionControl;

class GetTagCounter implements Callable<TagCounter> {

    String url;

    GetTagCounter(String url) {
        this.url = url;
    }

    @Override
    public TagCounter call() throws Exception {
        TagCounter tc = new TagCounter(url);
        tc.doWork();
        return tc;
    }
}

public class Tester {

    public static List<TagCounter> runSequental() {
        List<TagCounter> urls = new ArrayList();
        urls.add(new TagCounter("https://www.fck.dk"));
        urls.add(new TagCounter("https://www.google.com"));
        urls.add(new TagCounter("https://politiken.dk"));
        urls.add(new TagCounter("https://cphbusiness.dk"));
        for (TagCounter tc : urls) {
            tc.doWork();
        }
        return urls;
    }

    public static List<TagCounter> runParrallel() throws Exception {
        List<String> urls = new ArrayList();
        urls.add("https://www.fck.dk");
        urls.add("https://www.google.com");
        urls.add("https://politiken.dk");
        urls.add("https://cphbusiness.dk");
         
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<TagCounter>> futures = new ArrayList<>();
        for (String url : urls) {
            Callable<TagCounter> tagger = new GetTagCounter(url);
            Future future = executor.submit(tagger);
            futures.add(future);
        }
        List<TagCounter> tagList = new ArrayList();
        //Get the results
        for (Future<TagCounter> future : futures) {
            TagCounter tag = future.get();
            tagList.add(tag);
        }
        return tagList;
    }

    public static void main(String[] args) throws Exception {
        long timeSequental;
        long start = System.nanoTime();

        List<TagCounter> fetchedData = new Tester().runSequental();
        long end = System.nanoTime();
        timeSequental = end - start;
        System.out.println("Time Sequential: " + ((timeSequental) / 1_000_000) + " ms.");

        for (TagCounter tc : fetchedData) {
            System.out.println("Title: " + tc.getTitle());
            System.out.println("Div's: " + tc.getDivCount());
            System.out.println("Body's: " + tc.getBodyCount());
            System.out.println("----------------------------------");
        }

        start = System.nanoTime();
        //TODO Add your parrallel calculation here     
        List<TagCounter> fetchedParrallelData = new Tester().runParrallel();
        long timeParallel = System.nanoTime() - start;
        System.out.println("Time Parallel: " + ((timeParallel) / 1_000_000) + " ms.");
        System.out.println("Paralle was " + timeSequental / timeParallel + " times faster");

        for (TagCounter tc : fetchedParrallelData) {
            System.out.println("Title: " + tc.getTitle());
            System.out.println("Div's: " + tc.getDivCount());
            System.out.println("Body's: " + tc.getBodyCount());
            System.out.println("----------------------------------");
        }
        
    }
}
