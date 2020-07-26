import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class HttpRequest {
    public static void main(String arg[]){
//        HttpClient client = HttpClient.newHttpClient();
//        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
//                .uri(URI.create("https://www.pathofexile.com/api/trade/search/Standard"))
//                .header("Content-Type", "application/json")
//                .POST(java.net.http.HttpRequest.BodyPublishers.ofString("{\"query\":{\"filters\":{\"trade_filters\":{\"disabled\":false,\"filters\":{\"price\":{\"min\":1,\"max\":30}}}},\"status\":{\"option\":\"online\"},\"stats\":[{\"type\":\"and\",\"filters\":[]}],\"name\":\"Tabula Rasa\",\"type\":\"Simple Robe\"},\"sort\":{\"price\":\"asc\"}}"))
//                .build();
//
//        final String[] requestResult = new String[1];
//
//        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenAccept(s -> requestResult[0] = s)
//                .join();

        String postRequest = httpPostRequestJSON("Harvest", "{\"exchange\":{\"status\":{\"option\":\"online\"},\"have\":[\"chaos\"],\"want\":[\"exalted\"]}}", "exchange");

        //Parse the result returned from the post request
        ArrayList<String> postRequestResult = parsePostRequest(postRequest);
        //SearchID is the first element of the returned array
        String searchID = postRequestResult.get(0);
        //Remove the first element and load the array of itemcodes into a new array
        postRequestResult.remove(0);
        ArrayList<String> searchItems = postRequestResult;

        //Send a get request to find the price of the first item from the post request
        String getRequest = httpGetRequest(searchID, searchItems.get(19));
        //Parse the get request to get price of item
        String price = parseGetRequest(getRequest);

        System.out.println(price);
        //System.out.println(getRequest);
        //System.out.println(postRequest);
        //System.out.println(parsePostRequest(postRequest));
    }

    //Sends a POST request to the poe trade api
    //searchType is either "search" for normal search or "exchange" for bulk exchange search
    public static String httpPostRequestJSON(String leagueName, String jsonPayload, String searchType){
        HttpClient client = HttpClient.newHttpClient();
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("https://www.pathofexile.com/api/trade/" + searchType + "/" + leagueName))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

//        final String[] requestResult = new String[1];
//
//        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenAccept(s -> requestResult[0] = s)
//                .join();
//
//        return requestResult[0];

        String requestResult = null;

        try {
            requestResult = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return requestResult;
    }

    //This sends a get request using the id and itemcode from the post request
    //Returns a string in JSON format giving the information of that item
    public static String httpGetRequest(String idFromPost, String itemCodeFromPost){
        String url = "https://www.pathofexile.com/api/trade/fetch/" + itemCodeFromPost + "?query=" + idFromPost;
        //Method 2 HTTP GET request
        //Code from https://www.youtube.com/watch?v=qzRKa8I36Ww
        HttpClient client = HttpClient.newHttpClient();
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        String requestResult=null;

        try {
            requestResult = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return requestResult;
    }

    //This extracts the price of the item from the HTTP GET request
    public static String parseGetRequest(String responseBody){
        //Load the text into a JSONObject
        //Inside the result field is a 1 element array
        //Extract that element into a JSONObject
        JSONObject result = (new JSONObject(responseBody)).getJSONArray("result").getJSONObject(0);
        //Find the location of where the price of the item is stored
        JSONObject resultPrice = result.getJSONObject("listing").getJSONObject("price");
        //Putting the price into a readable format as follows "number currency type" e.g. "0.1 chaos"
        String price = resultPrice.getDouble("amount") + " " + resultPrice.getString("currency");

        String item = result.getJSONObject("item").getString("typeLine");
        //return price;
        return "1 " + item + " costs " + price;
    }

    //This extracts the result from the POST request into a string arraylist containing the id and itemcodes required
    //for the HTTP get request
    //the first element of this arraylist is the id, the rest are the itemcodes
    public static ArrayList<String> parsePostRequest(String responseBody){
        JSONObject requestResult = new JSONObject(responseBody);
        String id = requestResult.getString("id");
        JSONArray resultJSONArray = requestResult.getJSONArray("result");
        ArrayList<String> result = new ArrayList<>();
        result.add(id);

        for(int i = 0; i < resultJSONArray.length(); i ++){
            result.add(resultJSONArray.getString(i));
        }

        return result;
    }
}
