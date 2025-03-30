package MainPackage;

import javax.swing.*;
import java.awt.*;

public class ClickButton extends JButton  {
    Integer width, height;

    public ClickButton(String title) {
        super(title);
        init();

    }

    public ClickButton() {
        super("");
        init();
    }

    private void init(){
        setBorder(BorderFactory.createLineBorder(UserPlayer.backgroundColor, 2, true));
    }


    public void setCustomSize(int newWidth, int newHeight){
        width = newWidth;
        height = newHeight;
        changeSize(width, height);
    }

    public void setWidth(int newWidth){
        width = newWidth;
        changeSize(width, height);
    }

    public void setHeight(int newHeight){
        height = newHeight;
        changeSize(width, height);
    }

    private void changeSize(Integer width, Integer height){
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

}
