package MainPackage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class miscBoardSelector extends JButton {

    final Border border = BorderFactory.createLineBorder(UserPlayer.backgroundColor, 2, true);

    public miscBoardSelector(String text, MiscPanel mp  ){
        super(text);
        setFocusPainted(false);
        changeColorScheme(Button.buttonColor);

    }

    public void changeColorScheme(Color c) {
        setBackground(c);
        setBorder(border);
        revalidate();
    }

    public void addToPanel(JPanel p, GridBagConstraints c) {
        p.add(this, c);
    }

    public void showSelectedBoard(miscBoardInterface miscBoard, miscBoardInterface[] boards) {
        for(miscBoardInterface mbi : boards){
            mbi.showBoard(false);
        }
        miscBoard.enableBoard(true);
        miscBoard.showBoard(true);
    }
}
