import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class FossilUI extends JPanel{
    public static final String[] mFossilList= {"Fractured Fossil","Faceted Fossil","Glyphic Fossil","Hollow Fossil","Enchanted Fossil","Perfect Fossil","Sanctified Fossil","Bloodstained Fossil","Gilded Fossil","Bound Fossil","Corroded Fossil",
            "Shuddering Fossil","Tangled Fossil","Encrusted Fossil","Dense Fossil","Lucent Fossil","Jagged Fossil","Aetheric Fossil","Aberrant Fossil","Scorched Fossil","Frigid Fossil","Metallic Fossil","Prismatic Fossil","Pristine Fossil","Serrated Fossil"};
    public static String[] mSortedFossilList;
    private JPanel mColumn1 = new JPanel();
    private JPanel mColumn2 = new JPanel();
    private String mLeague;

    public static void main(String[] args) {
    }

    FossilUI(String league){
        mLeague = league;
        HashMap<String, Double> price = exchangeRate(mLeague);
        int n = 1;

        this.setLayout(new GridLayout(1,2));
        mColumn1.setLayout(new GridLayout(mFossilList.length / 2 + 1, 1));
        mColumn2.setLayout(new GridLayout(mFossilList.length / 2 + 1, 1));
        this.add(mColumn1);
        this.add(mColumn2);
        mSortedFossilList = MiscAlgorithms.sortByPrice(price, mFossilList);

        for(String i: mSortedFossilList){
            addFossilStrip(i, price.get(i).toString(), n);
            n++;
        }
    }

    private void addFossilStrip(String fossil, String price, int stripNumber){
        JPanel container = new JPanel();
        JTextField chaos = new JTextField();
        JTextField fossilText = new JTextField();
        BufferedImage chaosImage = null, fossilImage = null;

        try {
            chaosImage = ImageIO.read(new File("assets/currency/chaos.png"));
            fossilImage = ImageIO.read(new File("assets/fossil/" + fossil + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(fossil);
        }

        JLabel chaosIcon = new JLabel(new ImageIcon(chaosImage));
        chaosIcon.setToolTipText("Chaos Orb");

        JLabel fossilIcon = new JLabel(new ImageIcon(fossilImage));
        fossilIcon.setToolTipText("<html>" + fossil + "<br>Click to search this fossil on the official trade site</html>");
        fossilIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event){
                String response = HttpRequest.tradePostRequest(mLeague, "{\"exchange\":{\"status\":{\"option\":\"online\"},\"have\":[\"chaos\"],\"want\":[\"" + fossil.split(" ")[0] + "-fossil\"]}}", "exchange");
                String searchId = HttpRequest.parseTradePostRequest(response).get(0);

                try {
                    Desktop.getDesktop().browse(new URI("https://www.pathofexile.com/trade/exchange/" + mLeague + "/" + searchId));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        
        container.add(fossilText);
        container.add(fossilIcon);
        container.add(chaos);
        container.add(chaosIcon);

        if(Double.parseDouble(price) >= 1) {
            fossilText.setText("You can buy 1 x");
            chaos.setText("for " + MiscAlgorithms.round(Double.parseDouble(price)) + " x");
        }else{
            fossilText.setText("You can buy " + MiscAlgorithms.round(1/Double.parseDouble(price)) + " x");
            chaos.setText("for 1 x");
        }

        chaos.setEditable(false);
        chaos.setBorder(BorderFactory.createEmptyBorder());
        fossilText.setEditable(false);
        fossilText.setBorder(BorderFactory.createEmptyBorder());

        if(stripNumber <= (mFossilList.length / 2 + 1)) {
            mColumn1.add(container);
        }else{
            mColumn2.add(container);
        }
    }

    private HashMap<String, Double> exchangeRate(String league){
        String response = HttpRequest.poeNinjaGetRequest(league, "Fossil");

        return HttpRequest.parsePoeNinjaRequest(response, "fossil");
    }
}
