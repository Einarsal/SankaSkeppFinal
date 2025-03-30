package MainPackage;

import javax.swing.*;
import java.awt.*;

public interface BoardInterface {
    int rows = 10;
    int columns = 10;
    int totalSquares = rows * columns;
    void setSize(int width, int height);
    void addToBoard(JPanel board, GridBagConstraints c);
    Button[] getAllSquares();
    Button getCurrentSquare();
    void changeColorScheme();
    boolean gameIsOver();

}
