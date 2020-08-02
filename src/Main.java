import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener{
    public JFrame mainWindow;
    JPanel mainPanel, buttonPanel, currencyUI, fossilUI;
    JButton currency, fossil, betrayal, temple;
    String currentUI = "currency", currentLeague;
    JLabel loadingIcon;

    public static void main(String arg[]){
        new Main();
    }

    Main(){
        currentLeague = LeagueNames.getLeagueNames()[0];

        mainWindow = new JFrame("League Cheat Sheet");
        mainPanel = new JPanel();

        ImageIcon loadingGif = new ImageIcon("assets/ajax-loader.gif");
        loadingIcon = new JLabel(loadingGif, JLabel.CENTER);

        mainWindow.setSize(800,60 * CurrencyUI.mCurrencyList.length);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.add(mainPanel);

        mainPanel.add(buttonPanel());
        mainPanel.add(loadingIcon);

        mainWindow.setVisible(true);

        currencyUI = new CurrencyUI(currentLeague);

        mainPanel.add(currencyUI);
        mainPanel.remove(loadingIcon);
        mainWindow.revalidate();
    }

    public JPanel buttonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1));
        currency = new JButton("Currency");
        currency.addActionListener(this);
        fossil = new JButton("Fossil");
        fossil.addActionListener(this);
        betrayal = new JButton("Betrayal");
        temple = new JButton("Temple");
        buttonPanel.add(currency);
        buttonPanel.add(betrayal);
        buttonPanel.add(fossil);
        buttonPanel.add(temple);

        return buttonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JComponent source = (JComponent) actionEvent.getSource();

        if(source == currency){
            if(currentUI == "currency"){
                mainPanel.remove(currencyUI);
            }else if(currentUI == "fossil"){
                mainPanel.remove(fossilUI);
            }

            currencyUI = new CurrencyUI(currentLeague);
            mainPanel.add(currencyUI);
            mainWindow.revalidate();
            mainWindow.repaint();
            currentUI = "currency";
        }else if(source == fossil){
            if(currentUI == "currency"){
                mainPanel.remove(currencyUI);
            }else if(currentUI == "fossil"){
                mainPanel.remove(fossilUI);
            }

            fossilUI = new FossilUI(currentLeague);
            mainPanel.add(fossilUI);
            mainWindow.revalidate();
            mainWindow.repaint();
            currentUI = "fossil";
        }
    }
}