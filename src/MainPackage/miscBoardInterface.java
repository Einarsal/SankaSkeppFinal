package MainPackage;

import javax.swing.*;
import java.awt.*;

public interface miscBoardInterface {
    public void changeColorScheme(Color c);
    public void addToPanel(JPanel p, GridBagConstraints c);
    public void showNextBoat(int currentBoatIndex);
    public void showBoard(boolean visible);
    public void enableBoard(boolean disabled);

}
