package MainPackage;

import javax.swing.*;
import java.awt.*;

class TextField extends JTextField {

    public TextField() {
        changeColor(Button.buttonColor);
        setBackground(UserPlayer.backgroundColor);

    }

    public void changeColor(Color c){
        setForeground(c);
        setCaretColor(c);
        setBorder(BorderFactory.createLineBorder(Button.buttonColor, 2));
    }



}
