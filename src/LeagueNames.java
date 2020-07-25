import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LeagueNames {
    static public String[] getLeagueNames(Document documentJsoup){
        String[] leagueNames = new String[4];
        //Getting league names, there are two identical select tags with class="league chosen"
        //so we just take the first one
        Element leagueNamesSelect = documentJsoup.getElementsByClass("league chosen").first();
        //Extracting the league name options from within the select tag
        Elements leagueNamesElements = leagueNamesSelect.getElementsByTag("Option");

        int i = 0;
        //Looping through each option tag and adding the text in the option tag to the set
        for(Element league: leagueNamesElements){
            if(i == 4){
                break;
            }
            leagueNames[i] = league.text();
            i++;
        }

        return leagueNames;
    }
}
