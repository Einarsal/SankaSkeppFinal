package MainPackage;

import javax.swing.*;
import java.awt.*;

class TextArea extends JTextArea {

    protected TextArea() {
        changeColor(Button.buttonColor);
        setBackground(UserPlayer.backgroundColor);

        writeToTextArea();
    }

    public void writeToTextArea() {
        setText("");
        for (Profile p : Leaderboard.profiles) {
            append(p + "\n");
        }
    }

    public void changeColor(Color c){
        setForeground(c);
        setCaretColor(c);
        setBorder(BorderFactory.createLineBorder(Button.buttonColor, 2));
    }
}
