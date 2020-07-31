import javax.swing.*;
import java.awt.*;

public class Main implements Runnable{
    private static volatile boolean threadRunning = false;

    public static void main(String arg[]){
//        Main temp1 = new Main();
//        Main temp2 = new Main();
//
////        temp1.test();
////        temp2.test();
//        new Thread(temp1).start();
//        new Thread(temp2).start();
        String currentLeague = LeagueNames.getLeagueNames()[0];

        //System.out.println(currentLeague + " currency conversion rates");


        JFrame mainWindow = new JFrame("das");
        JPanel mainPanel = new JPanel();

        ImageIcon loadingGif = new ImageIcon("assets/ajax-loader.gif");
        JLabel loadingIcon = new JLabel(loadingGif, JLabel.CENTER);
        //mainWindow.add(new JButton("das"));

        mainWindow.setSize(800,50 * CurrencyUI.mCurrencyList.length);
        //mainWindow.setSize(2560,1440);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.add(mainPanel);
        //mainWindow.pack();

        mainPanel.add(Main.buttonPanel());
        mainPanel.add(loadingIcon);

        mainWindow.setVisible(true);

        JPanel currencyUI = new CurrencyUI();
        mainPanel.add(currencyUI);
        mainPanel.remove(loadingIcon);
        mainWindow.revalidate();
    }

    public static JPanel buttonPanel(){
        JPanel buttonPanel;
        JButton currency, fossil, betrayal, temple;

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1));
        currency = new JButton("Currency");
        fossil = new JButton("Fossil");
        betrayal = new JButton("Betrayal");
        temple = new JButton("Temple");
        buttonPanel.add(currency);
        buttonPanel.add(betrayal);
        buttonPanel.add(fossil);
        buttonPanel.add(temple);

        return buttonPanel;
    }

    public void test(){
        if(!threadRunning){
            threadRunning = true;
            System.out.println("Doing calculations");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadRunning = false;
        }else{
            System.out.println("Thread is running");
        }
    }

    @Override
    public void run() {
        if(!threadRunning){
            threadRunning = true;
            System.out.println("Doing calculations");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadRunning = false;
        }else{
            System.out.println("Thread is running");
        }
    }
}
