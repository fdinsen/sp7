package parallel;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Perhaps this you go into a separate file
class PingURL implements Callable<String> {
  String url;
  PingURL(String url) {
    this.url = url;
  }
  @Override
  public String call() throws Exception {
    String result = "Error";
    try {
      URL siteURL = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) siteURL
              .openConnection();
      connection.setRequestMethod("GET");
      connection.connect();

      int code = connection.getResponseCode();
      if (code == 200) {
        result = "GREEN";
      }
      if (code == 301) {
        result = "REDIRECT";
      }
    } catch (Exception e) {
      result = "RED";
    }
    return result;
  }
}

public class ParallelPinger {
  private static String[] hostList = {
    "https://crunchify.com",
    "https://yahoo.com",
    "https://www.ebay.com",
    "https://google.com",
    "https://www.example.com",
    "https://paypal.com",
    "https://bing.com/",
    "http://techcrunch.com/",
    "https://mashable.com/",
    "https://thenextweb.com/",
    "https://wordpress.com/",
    "https://cphbusiness.dk/",
    //"http://sjsu.edu/",
    "http://ebay.co.uk/", "http://google.co.uk/",
    "https://www.wikipedia.org/",
    "https://dr.dk", "https://pol.dk", "https://www.google.dk",
    "https://phoronix.com", "http://www.i-dont-exist-sorry.com/",
    "https://studypoint-plaul.rhcloud.com/", "http://stackoverflow.com",
    "http://docs.oracle.com",
    "http://imgur.com/", "http://www.imagemagick.org"
  };
    /*
    Create an executor service
    Get the list of URLs to contact from the static method in SequentalPinger
    Make a List of <Future<String>>
    Create your Callables, and start them via a method on the executor and add the returned future to the list
    Call a "relevant" method on all your futures to get the response, and to the List you eventually will return
    */
  public static List<String> getStatusFromAllServers() throws Exception{
    ExecutorService executor = Executors.newCachedThreadPool();
    List<Future<String>> futures = new ArrayList<>();
    for(String url : hostList){
      Callable<String> pinger = new PingURL(url);
      Future future = executor.submit(pinger);
      futures.add(future);
    }
    List<String> statusList = new ArrayList();
    //Get the results
    for(Future<String> future : futures){
      String status = future.get();
      statusList.add(status);
    }
    return statusList;
  }

  public static void main(String[] args) throws Exception {
    long timeStart = System.nanoTime();
    List<String> results = getStatusFromAllServers();
    for(String r: results){
      System.out.println(r);
    }
    long timeEnd = System.nanoTime();
    long total = (timeEnd - timeStart) / 1_000_000;
    System.out.println("Time to check URLS: " + total + "ms.");
  }
}
