import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

//This will be constructed with a main jpanel where everything goes into
//The main jpanel will have x jpanels stacked vertically, where x = number of currency types from CurrencyExchange class
//Within these jpanels there will be 2 textfields and 2 jpanels, jpanels will house the icons for the currency and
//textfield will have the conversion rates
public class CurrencyUI extends JPanel{
    public static final String[] mCurrencyList = {"Mirror of Kalandra","Orb of Alteration","Orb of Fusing","Orb of Alchemy","Gemcutter's Prism","Exalted Orb","Chromatic Orb","Jeweller's Orb","Orb of Chance","Cartographer's Chisel","Orb of Scouring",
            "Divine Orb","Vaal Orb","Simple Sextant","Prime Sextant","Awakened Sextant"};
    public static String[] mSortedCurrencyList;

    CurrencyUI(String league){
        HashMap<String, Double> price = exchangeRate(league);

        this.setLayout(new GridLayout(mCurrencyList.length,1));
        mSortedCurrencyList = MiscAlgorithms.sortByPrice(price, mCurrencyList);

        for(String i: mSortedCurrencyList){
            addCurrencyStrip(i, price.get(i).toString());
        }
    }

    private void addCurrencyStrip(String currency, String price){
        JPanel container = new JPanel();
        JTextField chaos = new JTextField();
        JTextField convertedCurrency = new JTextField();
        BufferedImage chaosImage = null, currencyImage = null;

        try {
            chaosImage = ImageIO.read(new File("assets/currency/chaos.png"));
            currencyImage = ImageIO.read(new File("assets/currency/" + currency + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel chaosIcon = new JLabel(new ImageIcon(chaosImage));
        chaosIcon.setToolTipText("Chaos Orb");
        JLabel currencyIcon = new JLabel(new ImageIcon(currencyImage));
        currencyIcon.setToolTipText(currency);

        container.add(convertedCurrency);
        container.add(currencyIcon);
        container.add(chaos);
        container.add(chaosIcon);

        if(Double.parseDouble(price) >= 1) {
            convertedCurrency.setText("You can convert 1 x");
            chaos.setText("to " + MiscAlgorithms.round(Double.parseDouble(price)) + " x");
        }else{
            convertedCurrency.setText("You can convert " + MiscAlgorithms.round(1/Double.parseDouble(price)) + " x");
            chaos.setText("to 1 x");
        }

        convertedCurrency.setEditable(false);
        convertedCurrency.setBorder(BorderFactory.createEmptyBorder());
        chaos.setEditable(false);
        chaos.setBorder(BorderFactory.createEmptyBorder());

        this.add(container);
    }

    private HashMap<String, Double> exchangeRate(String league){
        String response = HttpRequest.poeNinjaGetRequest(league, "Currency");

        return HttpRequest.parsePoeNinjaRequest(response, "Currency");
    }
}