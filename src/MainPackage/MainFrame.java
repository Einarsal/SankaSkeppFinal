/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;

import javax.swing.*;
import java.awt.*;

/**
 * @author Einar
 */
public class MainFrame extends JFrame {
    final static int PANEL_WIDTH = 600;
    final static int PANEL_HEIGHT = 600;
    public static MainFrame mainFrame;

    public static void main(String[] args) {
        mainFrame =new MainFrame("Battleship");
    }

    public MainFrame(String title) {
        super(title);
        GridPanel computerBoard = new GridPanel( false);
        //GridPanel computerBoard = new GridPanel(true);
        GridPanel userBoard = new GridPanel(true);
        userBoard.requestFocus();
        computerBoard.requestFocus();
        ComputerPlayer cp = new ComputerPlayer(computerBoard, userBoard);
        UserPlayer up = new UserPlayer(PANEL_WIDTH, PANEL_HEIGHT, userBoard, computerBoard, cp);
        add(up);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        up.requestFocus();
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);


    }

    private void createGrid() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
    }
}
