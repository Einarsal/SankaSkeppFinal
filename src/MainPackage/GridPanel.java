package MainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GridPanel extends JPanel implements PlayerBoardInterface, OpponentBoardInterface {
    public boolean hasUserBoats;
    public Button currentButton;
    public Button[] squares;
    private final ArrayList<Button[]> fullBoats;
    private int boatType = 0;
    private int shipsSunk = 0;
    public boolean gameOver;


    public GridPanel(boolean isPlayerBoatsBoard) {
        System.out.println("konstruktor");
        if (isPlayerBoatsBoard) {
            hasUserBoats = true;
        }
        fullBoats = new ArrayList<>();
        squares = new Button[totalSquares];
        setBackground(UserPlayer.backgroundColor);
        Color borderColor = UserPlayer.backgroundColor;
        int colorIncrease = 50;
        setBorder(BorderFactory.createLineBorder(new Color(borderColor.getRed() + colorIncrease, borderColor.getGreen() + colorIncrease, borderColor.getBlue() + colorIncrease), 1));
        setLayout(new GridLayout(rows, columns));
        addButtons();
    }

    private void addButtons() {
        for (int i = 0; i < totalSquares; i++) {
            createButton(i);
        }
        System.out.println("knappar klara");
    }

    private void createButton(int index) {
        System.out.println("ny knapp");
        Button button = new Button(index, rows);
        button.setIndex(index, rows);
        button.addActionListener(e -> buttonClickDetected(e, button));
        squares[index] = button;
        button.shouldPaintBoat = hasUserBoats;
        button.addButton(this);
    }

    private void buttonClickDetected(ActionEvent e, Button button) {
        if (currentButton != null) {
            currentButton.clicked = false;
            currentButton.repaint();
        }
            //System.out.println(currentButton.index + "");
        button.clicked = true;
        currentButton = button;
        System.out.println("ritar om knappen ");
        button.repaint();
    }


    @Override
    public void checkGuess(Button s) {
        if (s.isABoat) {
            s.hit = true;
            handleSunk(s.boatType);
        } else {
            s.miss = true;
        }
        s.repaint();
    }

    @Override
    public Button getButton(int index) {
        return squares[index];
    }

    @Override
    public Button[] getAllSquares() {
        return squares;
    }

    @Override
    public Button getCurrentSquare() {
        return currentButton;
    }

    @Override
    public void changeColorScheme() {
        System.out.println("change color");
        for (Button s : squares) {
            Button.paintGradient = !Button.paintGradient;
             s.repaint();
        }
    }


    @Override
    public void placeBoat(Button[] boatSquares) {
        fullBoats.add(boatSquares);
        for (Button s : boatSquares) {
            s.isABoat = true;
            s.boatType = boatType;
            s.repaint();
        }
        boatType++;
    }

    private boolean isSunk(Button[] bs) {
        for (Button b : bs) {
            if (!b.hit) {
                return false;
            }
        }
        return true;
    }

    private void handleSunk(int index) {
        Button[] boat = fullBoats.get(index);
        if (!isSunk(boat)) {
            return;
        }

        for (Button b : boat) {
            b.sunk = true;
        }
        shipsSunk++;
        if (shipsSunk >= fullBoats.size()) {
            System.out.println("game over");
            gameOver();
        }
    }

    private void gameOver() {
        System.out.println("gameover");
        gameOver = true;
        String gameOverMSG;
        Profile p = Leaderboard.currentProfile;
        if (hasUserBoats) {
            gameOverMSG = "you lost";
            if (p.score > 0) {
                p.score--;
            }
        } else {
            gameOverMSG = "you won";
            p.score++;
        }
        Leaderboard.writeToFile();
        showGameOverMSG(gameOverMSG);
    }

    private void showGameOverMSG(String gameOverMSG) {
        System.out.println("showGame");
        for (Button s : squares) {
            s.setVisible(false);
            //s.revalidate();
        }
        JOptionPane optionPane = new JOptionPane();
        int startOver = JOptionPane.showConfirmDialog(this, gameOverMSG + "\n New game?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (startOver == JOptionPane.NO_OPTION) {
            System.exit(1);
        }
        MainFrame.mainFrame.dispose();
        MainFrame.mainFrame = new MainFrame("Battleship");
    }

    protected void paintComponent(Graphics g) {
//        System.out.println("gridPaintComponent " + hasUserBoats);
      //  super.paintComponent(g);
    }

    @Override
    public boolean gameIsOver() {
        return gameOver;
    }

    @Override
    public void setSize(int width, int height) {
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    @Override
    public void addToBoard(JPanel board, GridBagConstraints c) {
        board.add(this, c);
    }

}
   