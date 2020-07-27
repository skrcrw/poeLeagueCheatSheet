import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Webscraptest {
    public static void main(String[] arg){
        try{
            //Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect("https://poe.trade").get();
            String[] leagueNames = LeagueNames.getLeagueNames();
            for(String i: leagueNames){
                System.out.println(i);
            }

            Document doc2 = Jsoup.connect("https://poe.trade/search/sarinokutasimi").get();
            Element resultSet = doc2.getElementById("item-container-8");
            //Elements resultTable = resultSet.getElementsByTag("tbody");
            String text = resultSet.toString();
            String [] words = text.split(" ");
            //System.out.println(text);
            //System.out.println(words[5] + words[6]);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
