import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

//This will be constructed with a main jpanel where everything goes into
//The main jpanel will have x jpanels stacked vertically, where x = number of currency types from CurrencyExchange class
//Within these jpanels there will be 2 textfields and 2 jpanels, jpanels will house the icons for the currency and
//textfield will have the conversion rates
public class CurrencyUI extends JPanel{
    CurrencyUI(String[] currencyList, ConcurrentHashMap conversionRates){
        this.setLayout(new GridLayout(currencyList.length,1));

        for(String i: currencyList){
            addCurrencyStrip(i, (String) conversionRates.get(i));
            //System.out.println(i+conversionRates.get(i));
        }
    }

    private void addCurrencyStrip(String currency, String price){
        JPanel container = new JPanel();
        JTextField chaos = new JTextField();
        JTextField convertedCurrency = new JTextField();
        BufferedImage chaosImage = null, currencyImage = null;

        try {
            chaosImage = ImageIO.read(new File("assets/currencyIcon/chaos.png"));
            currencyImage = ImageIO.read(new File("assets/currencyIcon/" + currency + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel chaosIcon = new JLabel(new ImageIcon(chaosImage));
        JLabel currencyIcon = new JLabel(new ImageIcon(currencyImage));

        container.add(convertedCurrency);
        container.add(currencyIcon);
        container.add(chaos);
        container.add(chaosIcon);

        if(Double.parseDouble(price) >= 1) {
            convertedCurrency.setText("You can convert 1 x");
            chaos.setText("to " + round(Double.parseDouble(price)) + " x");
        }else{
            convertedCurrency.setText("You can convert " + round(1/Double.parseDouble(price)) + " x");
            chaos.setText("to 1 x");
        }

        convertedCurrency.setEditable(false);
        convertedCurrency.setBorder(BorderFactory.createEmptyBorder());
        chaos.setEditable(false);
        chaos.setBorder(BorderFactory.createEmptyBorder());

        this.add(container);
    }

    private String round(double n){
        double temp = Math.round(n*10)/10.0;

        if((temp - (int)temp) < 0.1){
            return Integer.toString((int) temp);
        }else{
            return Double.toString(temp);
        }
    }
}
