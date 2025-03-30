package MainPackage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class NextBoatBoard extends JPanel implements  miscBoardInterface{

    Color mainColor;
    Boat[] boats;
    InformationText infoText;
    GridArea gridArea;

    public NextBoatBoard(Color mainColor) {
        this.mainColor = mainColor;
        this.boats = PlayerInterface.boats;
        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new BorderLayout());
        addComponents();
        showNextBoat(0);
        setVisible(false);
    }

    @Override
    public void changeColorScheme(Color c) {
        mainColor = c;
        setBackground(c);
        gridArea.setBackground(c);
    }

    @Override
    public void addToPanel(JPanel p, GridBagConstraints c) {
        p.add(this, c);
    }


    private void addComponents() {
        infoText = new InformationText();
        gridArea = new GridArea();
        changeColorScheme(mainColor);
        add(infoText, BorderLayout.PAGE_START);
        add(gridArea, BorderLayout.CENTER);
    }

    @Override
    public void showNextBoat(int currentBoatIndex) {
        gridArea.paintBoat(currentBoatIndex);
    }

    public void addToGridBagLayout(JPanel p, GridBagConstraints c) {
        p.add(this, c);
    }

    public void setVisibility(boolean visible) {
        setVisible(visible);
    }

    private void paintDisabled() {
        int red = getDarker(mainColor.getRed());
        int green = getDarker(mainColor.getGreen());
        int blue = getDarker(mainColor.getBlue());
        mainColor = new Color(red, green, blue, 170);
        changeColorScheme(mainColor);
        for (GridArea.Square s : gridArea.squares) {
            s.setBackground(mainColor);
            System.out.println("NBB setdisabled");
        }
    }

    private int getDarker(int colorValue) {

        final int colorChange = 50;

        if (colorValue - colorChange < 0) {
            return 0;
        }
        return colorValue + colorChange;
    }


    @Override
    public void enableBoard(boolean enabled){
        if(enabled) {
            setEnabled(true);
            changeColorScheme(mainColor);
            return;
        }
        paintDisabled();
    }

    @Override
    public void showBoard(boolean visible) {
        this.setVisible(visible);
    }


    private static class InformationText extends JLabel {

        protected InformationText() {
            super("Next Boat:", SwingConstants.CENTER);
            setFont(new Font("Times New Roman", Font.BOLD, 20));
        }


    }

    private class GridArea extends JPanel {

        final static int rows = 4;
        final static int cols = 4;
        ArrayList<Square> squares;

        protected GridArea() {
            setLayout(new GridLayout(rows, cols));
            createSquares();
        }

        private void createSquares() {
            System.out.println("NBB create");
            squares = new ArrayList<>();
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    squares.add(new Square());
                }
            }
            addSquares();
        }

        private void addSquares() {
            for (Square s : squares) {
                System.out.println("NBB addsquares");
                add(s);
            }
        }


        protected void paintComponent(Graphics g) {
            System.out.println("nextBoatBoard");
            super.paintComponent(g);
            g.setColor(UserPlayer.backgroundColor);

        }

        public void paintBoat(int currentBoatIndex) {
            clearAll();
            Boat b;
            try {
                b = boats[currentBoatIndex];
            } catch (ArrayIndexOutOfBoundsException e) {
                return;
            }
            ArrayList<Square> boatSquares = getRelevantSquares(b);
            for (Square s : boatSquares) {
                System.out.println("getrel");
                s.shouldPaintBoatMarker = true;
                s.repaint();
            }
            changeColorScheme(mainColor);
        }

        private ArrayList<Square> getRelevantSquares(Boat b) {
            ArrayList<Square> boatSquares = new ArrayList<>();
            for (int i = 0; i < b.covRows; i++) {
                for (int j = 0; j < b.covCols; j++) {
                    int centerCol = ((cols - b.covCols) / 2) + j;
                    boatSquares.add(squares.get((i + 1) * rows + centerCol));
                }
            }
            return boatSquares;
        }

        protected void clearAll() {
            for (Square s : squares) {
                System.out.println("nextBoatBoardclear");
                s.shouldPaintBoatMarker = false;
                s.repaint();
            }
        }

        protected class Square extends JPanel {

            protected boolean shouldPaintBoatMarker;

            protected Square() {
                setBorder(BorderFactory.createLineBorder(UserPlayer.backgroundColor, 2));
                setBackground(mainColor);
            }

            protected void paintComponent(Graphics g) {

                System.out.println("nextBoatBoard");
                if (shouldPaintBoatMarker) {
                    g.setColor(Button.boatColor);
                    g.fillOval(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2);
                }
            }
        }
    }
}
