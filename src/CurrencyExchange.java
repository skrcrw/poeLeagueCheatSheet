import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CurrencyExchange{
    //The following hashmap hold the payload for the post request for each currency type
    private HashMap<String, String> mCurrencyPostPayload = new HashMap<>();
    //The following array holds the list of currency types
    static private String[] mCurrencyList = {"alt", "fusing", "alch", "gcp", "exalted", "chrome", "jewellers", "chance", "chisel", "scour", "divine", "vaal", "apprentice-sextant", "journeyman-sextant", "master-sextant"};
    //This hashmap holds the currency exchange rate
    private ConcurrentHashMap mCurrencyConversion = new ConcurrentHashMap();

    public static void main(String arg[]){
        CurrencyExchange test = new CurrencyExchange();
        HashMap<String, String> currencyPostPayload = test.getCurrencyPostPayload();
        ExecutorService es = Executors.newFixedThreadPool(15);
//        String postRequest, getRequest, searchID, price;
//        ArrayList<String> postRequestParsed, searchItems;
        int requestCount = 0;

        for(Map.Entry<String, String> i: currencyPostPayload.entrySet()){
//            postRequest = HttpRequest.httpPostRequestJSON("Harvest", i.getValue(), "exchange");
//            postRequestParsed = HttpRequest.parsePostRequest(postRequest);
//            searchID = postRequestParsed.get(0);
//            postRequestParsed.remove(0);
//            searchItems = postRequestParsed;
//
//            getRequest = HttpRequest.httpGetRequest(searchID, searchItems.get(19));
//            price = HttpRequest.parseGetRequest(getRequest);
//            System.out.println(price);
            es.execute(new MyThread(i.getValue(), i.getKey(), test.getCurrencyConversion()));
            requestCount++;
            //if(requestCount == 12){
                requestCount = 0;
                try {
                    Thread.sleep(550);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           // }
        }

        es.shutdown();

        try {
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(test.getCurrencyConversion());
    }

    CurrencyExchange(){
        loadHashmap();
    }

    private void loadHashmap(){
        for(String i: mCurrencyList){
            mCurrencyPostPayload.put(i, "{\"exchange\":{\"status\":{\"option\":\"online\"},\"have\":[\"chaos\"],\"want\":[\"" + i + "\"]}}");
        }
    }

    public HashMap<String, String> getCurrencyPostPayload() {
        return mCurrencyPostPayload;
    }

    public String[] getCurrencyList() {
        return mCurrencyList;
    }

    public ConcurrentHashMap getCurrencyConversion(){
        return mCurrencyConversion;
    }
}

class MyThread implements Runnable{
    String mPostPayload, mCurrency;
    ConcurrentHashMap mCurrencyConversion;

    MyThread(String payload, String currency, ConcurrentHashMap conversionHash){
        mPostPayload = payload;
        mCurrency = currency;
        mCurrencyConversion = conversionHash;
    }

    @Override
    public void run(){
        String postRequest, getRequest, searchID, priceMessage;
        ArrayList<String> postRequestParsed, searchItems;

        postRequest = HttpRequest.httpPostRequestJSON("Harvest", mPostPayload, "exchange");
        postRequestParsed = HttpRequest.parsePostRequest(postRequest);
        searchID = postRequestParsed.get(0);
        postRequestParsed.remove(0);
        searchItems = postRequestParsed;

        getRequest = HttpRequest.httpGetRequest(searchID, searchItems.get(19));
        priceMessage = HttpRequest.parseGetRequest(getRequest);

        String[] price = priceMessage.split(" ");

        mCurrencyConversion.put(mCurrency, price[price.length - 2]);
        System.out.println(priceMessage);
    }
}