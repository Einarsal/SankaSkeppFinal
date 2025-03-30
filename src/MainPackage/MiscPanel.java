package MainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiscPanel extends JPanel {
    private final UserPlayer userPlayer;

    public boolean showLeaderboard;
    public boolean showNextBoat;
    public miscBoardInterface nextBoatBoard;
    public miscBoardInterface leaderBoard;
    miscBoardSelector leaderboardSelector;
    miscBoardSelector nextBoatBoardSelector;
    //final static Color miscBackground = new Color(230, 230, 230);
    final static Color miscBackground = Button.buttonColor;
    private miscBoardInterface[] boards;


    public MiscPanel(UserPlayer userPlayer) {
        showNextBoat = true;
        showLeaderboard = false;
        nextBoatBoard = new NextBoatBoard( Button.buttonColor);
        leaderBoard = new MainPackage.Leaderboard(userPlayer);
        boards = new miscBoardInterface[]{nextBoatBoard, leaderBoard};
        nextBoatBoardSelector = createBoardSelectors("Next Boat", nextBoatBoard);
        leaderboardSelector = createBoardSelectors("Leaderboard", leaderBoard);
        this.userPlayer = userPlayer;
        setBackground(miscBackground);
        addComponents();
    }

    public void changeColor(Color c){
        leaderBoard.changeColorScheme(c);
        nextBoatBoard.changeColorScheme(c);
        leaderboardSelector.changeColorScheme(c);
        nextBoatBoardSelector.changeColorScheme(c);
    }


    private void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 1;
        c.weightx = 0.1;
        c.weighty = 0.01;
        c.gridwidth = 1;
        c.gridy = 0;
        c.gridx = 0;
        nextBoatBoardSelector.addToPanel(this, c);

        c.gridx = 1;
        leaderboardSelector.addToPanel(this, c);

        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 2;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        leaderBoard.addToPanel(this, c);
        nextBoatBoard.addToPanel(this, c);

    }

    private miscBoardSelector createBoardSelectors (String text, miscBoardInterface miscBoard){
        miscBoardSelector mbs = new miscBoardSelector(text, this);
        mbs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mbs.showSelectedBoard(miscBoard, boards);
            }
        });
        return mbs;
    }



}
