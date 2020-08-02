import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

//This class contains Algorithms that are used in multiple locations
public class MiscAlgorithms {
    //This method takes in a hashmap and an array of keys for the hashmap
    //then returns a sorted array of keys for the hashmap such that
    //the first key corresponds to the most expensive item, second key corresponds to the second most expensive item and so on
    public static String[] sortByPrice(HashMap<String, Double> exchangeRate, String[] keyArray){
        StringDoubleContainer[] temp = new StringDoubleContainer[keyArray.length];
        String[] sortedCurrencyList = new String[keyArray.length];

        for(int i = 0; i < keyArray.length; i ++){
            temp[i] = new StringDoubleContainer(keyArray[i], exchangeRate.get(keyArray[i]));
        }

        Arrays.sort(temp, Collections.reverseOrder());

        for(int i = 0; i < keyArray.length; i ++){
            sortedCurrencyList[i] = temp[i].mString;
        }

        return sortedCurrencyList;
    }

    //Rounds a double to 1dp, if the rounded value ends in x.0 then round to an int then return rounded value as a string
    //eg 1.1111111111 rounds to 1.1
    //   5.9999999999 rounds to 6
    //   13.00004 rounds to 13
    public static String round(double n){
        double temp = Math.round(n*10)/10.0;

        if((temp - (int)temp) < 0.1){
            return Integer.toString((int) temp);
        }else{
            return Double.toString(temp);
        }
    }
}

class StringDoubleContainer implements Comparable{
    public String mString;
    public double mDouble;

    StringDoubleContainer(String x, double y){
        mString = x;
        mDouble = y;
    }

    public int compareTo(Object obj){
        StringDoubleContainer c = (StringDoubleContainer)obj;

        if(c.mDouble == mDouble){
            return 0;
        }else if(c.mDouble > mDouble){
            return -1;
        }else{
            return 1;
        }
    }

}