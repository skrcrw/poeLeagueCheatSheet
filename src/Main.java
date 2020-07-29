import javax.swing.*;

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

        System.out.println(currentLeague + " currency conversion rates");
        CurrencyExchange currencyExchange = new CurrencyExchange(currentLeague);


        JFrame jframe = new JFrame("das");
        JPanel currencyUI = new CurrencyUI(currencyExchange.mCurrencyList, currencyExchange.getCurrencyConversion());

        jframe.setSize(800,600);
        jframe.add(currencyUI);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);


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
