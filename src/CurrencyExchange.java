import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CurrencyExchange{
    //The following hashmap hold the payload for the post request for each currency type
    private HashMap<String, String> mCurrencyPostPayload = new HashMap<>();
    //The following array holds the list of currency types
    final private String[] mCurrencyList = {"alt", "fusing", "alch", "gcp", "exalted", "chrome", "jewellers", "chance", "chisel", "scour", "divine", "vaal", "apprentice-sextant", "journeyman-sextant", "master-sextant"};
    //This hashmap holds the currency exchange rate
    private ConcurrentHashMap mCurrencyConversion = new ConcurrentHashMap();

    public static void main(String arg[]){
        for (String i: LeagueNames.getLeagueNames()) {
            System.out.println(i + " currency conversion rates");
            new CurrencyExchange(i);
            System.out.println("\n\n");
        }
    }

    CurrencyExchange(String league){
        loadHashmap();
        //Creating 12 threads to send Http requests
        ExecutorService es = Executors.newFixedThreadPool(12);

        if(league.contains(" ")){
            String temp[] = league.split(" ");
            league = temp[0] + "%20" + temp[1];
        }

        for(String i: mCurrencyList){
            try {
                //There is a limit 2 Post requests per second, each thread takes slightly less than 2 seconds to finish
                //so with 4 threads we need to put a slight delay between threads
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            es.execute(new MyThread(mCurrencyPostPayload.get(i), i, mCurrencyConversion, league));
        }

        //Will free up threads after they finish executing
        es.shutdown();

        try {
            //Forces the thread calling this constructor to hang until all threads are finished
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //System.out.println(mCurrencyConversion);
    }

    private void loadHashmap(){
        for(String i: mCurrencyList){
            mCurrencyPostPayload.put(i, "{\"exchange\":{\"status\":{\"option\":\"online\"},\"have\":[\"chaos\"],\"want\":[\"" + i + "\"]}}");
        }
    }

    public HashMap<String, String> getCurrencyPostPayload() {
        return mCurrencyPostPayload;
    }

    public ConcurrentHashMap getCurrencyConversion(){
        return mCurrencyConversion;
    }
}

class MyThread implements Runnable{
    String mPostPayload, mCurrency, mLeague;
    ConcurrentHashMap mCurrencyConversion;

    MyThread(String payload, String currency, ConcurrentHashMap conversionHash, String league){
        mPostPayload = payload;
        mCurrency = currency;
        mCurrencyConversion = conversionHash;
        mLeague = league;
    }

    @Override
    public void run(){
        String postRequest, getRequest, searchID, priceMessage;
        ArrayList<String> postRequestParsed, searchItems;

        postRequest = HttpRequest.httpPostRequestJSON(mLeague, mPostPayload, "exchange");
        postRequestParsed = HttpRequest.parsePostRequest(postRequest);
        searchID = postRequestParsed.get(0);
        postRequestParsed.remove(0);
        searchItems = postRequestParsed;

        if(mLeague.contains("Hardcore")){
            getRequest = HttpRequest.httpGetRequest(searchID, searchItems.get(0));
        }else {
            getRequest = HttpRequest.httpGetRequest(searchID, searchItems.get(19));
        }
        priceMessage = HttpRequest.parseGetRequest(getRequest);

        String[] price = priceMessage.split(" ");

        mCurrencyConversion.put(mCurrency, price[price.length - 2]);
        System.out.println(priceMessage);
    }
}