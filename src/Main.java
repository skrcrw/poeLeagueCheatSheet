import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener{
    public JFrame mMainWindow;
    JPanel mMainPanel, mButtonPanel, mCurrencyUI, mFossilUI;
    JButton mCurrency, mFossil, mBetrayal, mTemple;
    String mCurrentDisplay = "currency", mCurrentLeague;
    JLabel mLoadingIcon;

    public static void main(String arg[]){
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
        mCurrency = new JButton("Currency");
        mCurrency.addActionListener(this);
        mFossil = new JButton("Fossil");
        mFossil.addActionListener(this);
        mBetrayal = new JButton("Betrayal");
        mTemple = new JButton("Temple");
        mButtonPanel.add(mCurrency);
        mButtonPanel.add(mBetrayal);
        mButtonPanel.add(mFossil);
        mButtonPanel.add(mTemple);

        return mButtonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JComponent source = (JComponent) actionEvent.getSource();

        if(source == mCurrency){
            if(mCurrentDisplay == "currency"){
                mMainPanel.remove(mCurrencyUI);
            }else if(mCurrentDisplay == "fossil"){
                mMainPanel.remove(mFossilUI);
            }

            mCurrencyUI = new CurrencyUI(mCurrentLeague);
            mMainPanel.add(mCurrencyUI);
            mMainWindow.revalidate();
            mMainWindow.repaint();
            mCurrentDisplay = "currency";
        }else if(source == mFossil){
            if(mCurrentDisplay == "currency"){
                mMainPanel.remove(mCurrencyUI);
            }else if(mCurrentDisplay == "fossil"){
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