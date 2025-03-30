package MainPackage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class UserPlayer extends JPanel implements PlayerInterface {
    final static Color backgroundColor = new Color(25, 25, 25);
    protected int colorIndex = 0;
    protected int gridWidth, gridHeight, panelHeight, panelWidth;
    final static Border emptyBorder = BorderFactory.createEmptyBorder();
    private final PlayerBoardInterface playerBoard;
    private final OpponentBoardInterface opponentBoard;
    private int boatType;
    public static Button currentButton;
    public static boolean hasChosenPlayer;
    PlayerInterface computerPlayer;
    MiscPanel miscPanel;
    miscBoardInterface nextBoatBoard;
    ArrayList<Button> guessableSquares;
    ButtonHolder bh;



    public UserPlayer(int gridWidth, int gridHeight, PlayerBoardInterface playerBoard, OpponentBoardInterface opponentBoard, PlayerInterface computerPlayer) {
        panelWidth = ((gridWidth * 5) / 3);
        this.gridHeight = gridHeight;
        panelHeight = gridHeight;
        this.playerBoard = playerBoard;
        this.opponentBoard = opponentBoard;
        this.computerPlayer = computerPlayer;
        miscPanel = new MiscPanel(this);
        nextBoatBoard = miscPanel.nextBoatBoard;
        bh = new ButtonHolder();
        opponentBoard.setSize(gridWidth, gridHeight);
        playerBoard.setSize(gridWidth, gridHeight);
        Dimension size = new Dimension(panelWidth, gridHeight);
        setAppearance(size);
        setColorScheme();
        createGrid();
    }

    private void setAppearance(Dimension size) {
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setBackground(backgroundColor);
        setFocusable(true);
        setBorder(emptyBorder);
    }

    private void createGrid() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        playerBoard.addToBoard(this, c);


        c.gridy = 1;
        opponentBoard.addToBoard(this, c);

        c.gridy = 0;
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1 / 3f;
        c.weighty = 0.5;
        add(bh, c);


        c.gridy = 1;
        add(miscPanel, c);
    }

    public int placeBoat() {
        if (boatType > boats.length - 1) {
            return boatType;
        }
        Button cornerSquare = playerBoard.getCurrentSquare();
        Button[] squaresInBoat = createArrayOfBoatSquares(cornerSquare, playerBoard.getAllSquares(), boats[boatType]);
        if (!boatLocationIsValid(squaresInBoat)) {
            return boatType;
        }
        playerBoard.placeBoat(squaresInBoat);
        boatType++;
        nextBoatBoard.showNextBoat(boatType);
        return boatType;
    }

    private Button[] createArrayOfBoatSquares(Button cornerSquare, Button[] squares, Boat boat) {
        int rows = boat.covRows;
        int columns = boat.covCols;
        int covSquares = rows * columns;
        Button[] boatSquares = new Button[covSquares];
        int i = 0;
        for (int j = 0; j < rows; j++) {
            for (int k = 0; k < columns; k++) {
                try {
                    boatSquares[i] = squares[Button.calculateIndex(j + cornerSquare.row, k + cornerSquare.column)];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return null;
                }
                i++;
            }
        }
        return boatSquares;
    }

    private boolean boatLocationIsValid(Button[] squares) {
        try {
            for (Button s : squares) {
                if (s.isABoat) {
                    return false;
                }
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public void makeGuess() {
        Button guessedSquare = opponentBoard.getCurrentSquare();
        if (guessedSquare.hit || guessedSquare.miss) {
            return;
        }
        opponentBoard.checkGuess(guessedSquare);
        repaintAllButtons();
        if (!opponentBoard.gameIsOver()) {
            computerPlayer.makeGuess();
        }
    }

    private void makeDebugGuess() {
        Random random = new Random();
        int guessedSquareIndex = random.nextInt(guessableSquares.size());
        Button s = guessableSquares.get(guessedSquareIndex);
        guessableSquares.remove(guessedSquareIndex);
        opponentBoard.checkGuess(s);
        repaintAllButtons();
    }

    private void setColorScheme(){
        Button.paintGradient = false;
        switch(colorIndex % 3){
            case 0:
                Button.buttonColor = new Color(46, 155, 238);
                break;
            case 1:
                Button.buttonColor = new Color(230, 230, 230);
                break;
            case 2:
                Button.buttonColor = new Color(230, 20, 240, 200);
                Button.paintGradient = true;
                break;
            default:
                break;
        }
        repaintAllButtons();
    }

    private void repaintAllButtons(){
        for(Button b : playerBoard.getAllSquares()){
            b.repaint();
        }
        for(Button b : opponentBoard.getAllSquares()){
            b.repaint();
        }
        for(Button b : bh.buttons){
            b.repaint();
        }

        miscPanel.changeColor(Button.buttonColor);

    }



    public class ButtonHolder extends JPanel {
       public Button[] buttons = { createPlaceBoatButton(), createGuessButton(), createPaintGradientButton()};
        //Button[] buttons = {createDebugButton(), createPlaceBoatButton(), createGuessButton(), createPaintGradientButton()};



        public ButtonHolder() {
            setLayout(new GridLayout(buttons.length, 1));
            for (Button b : buttons) {
                add(b);
            }
        }

        private Button createDebugButton() {
            Button b = new Button("test button");
            makeGuessableSquaresArray();
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    makeDebugGuess();
                    computerPlayer.makeGuess();
                }
            });
            return b;
        }

        private void makeGuessableSquaresArray() {
            guessableSquares = new ArrayList<>();
            guessableSquares.addAll(Arrays.asList(opponentBoard.getAllSquares()));
        }


        private Button createPlaceBoatButton() {
            Button b = new Button("Place Boat");
            b.setEnabled(false);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (placeBoat() > boats.length - 1) {
                        b.setEnabled(false);
                        miscPanel.nextBoatBoard.enableBoard(true);
                        buttons[1].setEnabled(true);
                        System.out.println("placeboat");
                        b.repaint();
                    }

                }
            });
            return b;
        }

        private Button createGuessButton() {
            Button b = new Button("guess");
            b.setEnabled(false);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    makeGuess();
                }
            });
            return b;
        }

        private Button createPaintGradientButton() {
            Button b = new Button("change color scheme");
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    colorIndex++;
                    setColorScheme();


//                    playerBoard.changeColorScheme();
//                    opponentBoard.changeColorScheme();
//                    for (Button tempButton : buttons) {
//                        if (tempButton.paintGradient) {
//                            tempButton.paintGradient = false;
//                        } else {
//                            tempButton.paintGradient = true;
//                        }
//                        tempButton.repaint();
//                    }
                }
            });
            return b;
        }



    }

}
