import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main implements ActionListener{
    public JFrame mMainWindow;
    JPanel mMainPanel, mButtonPanel, mCurrencyUI, mFossilUI;
    JButton mCurrencyButton, mFossilButton, mBetrayalButton, mTempleButton;
    String mCurrentDisplay = "currency", mCurrentLeague;
    JLabel mLoadingIcon;

    public static void main(String[] arg){
        new Main();
    }

    Main(){
        mCurrentLeague = LeagueNames.getLeagueNames()[0];

        mMainWindow = new JFrame("League Cheat Sheet");
        mMainPanel = new JPanel();

        ImageIcon loadingGif = new ImageIcon("assets/ajax-loader.gif");
        mLoadingIcon = new JLabel(loadingGif, JLabel.CENTER);

        mMainWindow.setSize(800,60 * CurrencyUI.mCurrencyList.length);
        mMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mMainWindow.add(mMainPanel);

        mMainPanel.add(buttonPanel());
        mMainPanel.add(mLoadingIcon);

        mMainWindow.setVisible(true);

        mCurrencyUI = new CurrencyUI(mCurrentLeague);

        mMainPanel.add(mCurrencyUI);
        mMainPanel.remove(mLoadingIcon);
        mMainWindow.revalidate();
    }

    public JPanel buttonPanel(){
        mButtonPanel = new JPanel();
        mButtonPanel.setLayout(new GridLayout(4,1));

        mCurrencyButton = new JButton("Currency");
        mCurrencyButton.addActionListener(this);
        mCurrencyButton.setHorizontalAlignment(SwingConstants.LEFT);

        mFossilButton = new JButton("Fossil");
        mFossilButton.addActionListener(this);
        mFossilButton.setHorizontalAlignment(SwingConstants.LEFT);

        mBetrayalButton = new JButton("Betrayal");
        mBetrayalButton.setHorizontalAlignment(SwingConstants.LEFT);

        mTempleButton = new JButton("Temple");
        mTempleButton.setHorizontalAlignment(SwingConstants.LEFT);

        try {
            Image currencyIcon = ImageIO.read(new File("assets/currency/Mirror of Kalandra.png"));
            Image fossilIcon = ImageIO.read(new File("assets/fossil/Serrated Fossil.png"));

            mCurrencyButton.setIcon(new ImageIcon(currencyIcon));
            mFossilButton.setIcon(new ImageIcon(fossilIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mButtonPanel.add(mCurrencyButton);
        mButtonPanel.add(mBetrayalButton);
        mButtonPanel.add(mFossilButton);
        mButtonPanel.add(mTempleButton);

        return mButtonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JComponent source = (JComponent) actionEvent.getSource();

        if(source == mCurrencyButton){
            if(mCurrentDisplay.equals("currency")){
                mMainPanel.remove(mCurrencyUI);
            }else if(mCurrentDisplay.equals("fossil")){
                mMainPanel.remove(mFossilUI);
            }

            mCurrencyUI = new CurrencyUI(mCurrentLeague);
            mMainPanel.add(mCurrencyUI);
            mMainWindow.revalidate();
            mMainWindow.repaint();
            mCurrentDisplay = "currency";
        }else if(source == mFossilButton){
            if(mCurrentDisplay.equals("currency")){
                mMainPanel.remove(mCurrencyUI);
            }else if(mCurrentDisplay.equals("fossil")){
                mMainPanel.remove(mFossilUI);
            }

            mFossilUI = new FossilUI(mCurrentLeague);
            mMainPanel.add(mFossilUI);
            mMainWindow.revalidate();
            mMainWindow.repaint();
            mCurrentDisplay = "fossil";
        }
    }
}