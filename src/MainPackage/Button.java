package MainPackage;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class Button extends ClickButton {
    public static Color buttonColor = new Color(46, 155, 238);
    private Border solidBorder = BorderFactory.createLineBorder(UserPlayer.backgroundColor, 2);

    boolean clicked = false;
    public int index = -1;
    public int row, column;
    boolean isABoat;
    public static boolean paintGradient = false;
    public boolean hit, miss, sunk;
    public boolean shouldPaintBoat;
    public int boatType;
    public static int numberOf = 0;

    final static Color sunkColor = new Color(30, 230, 30, 230);
    final static Color boatColor = new Color(20, 20, 20, 235);
    final static Color missColor = new Color(255, 0, 0, 200);
    final static Color stampColor = new Color(30, 30, 230, 230);
    final static Color hitColor = new Color(255, 255,0, 230);


    public Button(String title) {
        super(title);
        init();
    }

    public Button(int index, int rows) {
        super("" + index);
        setIndex(index, rows);
        init();

    }

    public void init() {
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setFont(new Font("Times New Roman", Font.BOLD, 20));

        colors = new ArrayList<>();
        addColor(
                new ModelColor(new Color(255, 0, 0), 0 / 24f),
                new ModelColor(new Color(255, 64, 0), 1 / 24f),
                new ModelColor(new Color(255, 128, 0), 2 / 24f),
                new ModelColor(new Color(255, 192, 0), 3 / 24f),
                new ModelColor(new Color(255, 255, 0), 4 / 24f),
                new ModelColor(new Color(192, 255, 0), 5 / 24f),
                new ModelColor(new Color(128, 255, 0), 6 / 24f),
                new ModelColor(new Color(64, 255, 0), 7 / 24f),
                new ModelColor(new Color(0, 255, 0), 8 / 24f),
                new ModelColor(new Color(0, 255, 64), 9 / 24f),
                new ModelColor(new Color(0, 255, 128), 10 / 24f),
                new ModelColor(new Color(0, 255, 192), 11 / 24f),
                new ModelColor(new Color(0, 255, 255), 12 / 24f),
                new ModelColor(new Color(0, 192, 255), 13 / 24f),
                new ModelColor(new Color(0, 128, 255), 14 / 24f),
                new ModelColor(new Color(0, 64, 255), 15 / 24f),
                new ModelColor(new Color(0, 0, 255), 16 / 24f),
                new ModelColor(new Color(64, 0, 255), 17 / 24f),
                new ModelColor(new Color(128, 0, 255), 18 / 24f),
                new ModelColor(new Color(192, 0, 255), 19 / 24f),
                new ModelColor(new Color(255, 0, 255), 20 / 24f),
                new ModelColor(new Color(255, 0, 192), 21 / 24f),
                new ModelColor(new Color(255, 0, 128), 22 / 24f),
                new ModelColor(new Color(255, 0, 64), 23 / 24f),
                new ModelColor(new Color(255, 0, 0), 24 / 24f)
        );
    }

    private ArrayList<ModelColor> colors;

    public void addColor(ModelColor... color) {
        Collections.addAll(colors, color);
    }

    @Override
    public void paintComponent(Graphics g) {
        numberOf++;


        if (paintGradient) {
            paintGradient(g);
        } else {
            paintSolid(g);
        }

        if (clicked) {
            paintClick(g);
        }

        if (isABoat && shouldPaintBoat) {
            paintBoat(g);
        }

        if (hit) {
            paintGuess(g, hitColor);
        }

        if (miss) {
            paintGuess(g, missColor);
        }

        if (sunk) {
            paintGuess(g, sunkColor);
        }

        if (!isEnabled()) {
            paintDisabled(g);
        }

        super.paintComponent(g);
    }

    private void paintDisabled(Graphics g) {
        g.setColor(Color.red);
        final int thickness = 4;
        int[] xFill1 = {thickness, 0, 0, getWidth() - thickness, getWidth(), getWidth()};
        int[] yFill1 = {0, 0, thickness, getHeight(), getHeight(), getHeight() - thickness};
        int[] xFill2 = {getWidth() - thickness, getWidth(), getWidth(), thickness, 0, 0};
        int[] yFill2 = {0, 0, thickness, getHeight(), getHeight(), getHeight() - thickness};

        g.fillPolygon(xFill1, yFill1, xFill1.length);
        g.fillPolygon(xFill2, yFill2, xFill2.length);
    }

    private void paintGuess(Graphics g, Color c) {
        g.setColor(c);
        g.fillOval(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2);
    }

    private void paintBoat(Graphics g) {
        g.setColor(boatColor);
        g.fillOval(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2);
    }

    private void paintSolid(Graphics g) {
        g.setColor(buttonColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        setBackground(buttonColor);
        setBorder(solidBorder);
        System.out.println("paintSolid");
    }

    private void paintGradient(Graphics g) {
        setBackground(buttonColor);
        if (!colors.isEmpty()) {
            int width = getWidth();
            int height = getHeight();
            Graphics2D g2 = (Graphics2D) g;
            Color[] color = new Color[colors.size()];
            float[] position = new float[colors.size()];
            for (int i = 0; i < colors.size(); i++) {
                color[i] = colors.get(i).getColor();
                position[i] = colors.get(i).getPosition();
            }
            int sx = 0;
            int sy = 0;
            int ex = width;
            int ey = height;
            LinearGradientPaint gp = new LinearGradientPaint(sx, sy, ex, ey, position, color);
            g2.setPaint(gp);
            g2.fillRoundRect(2, 2, width - 4, height - 4, 10, 10);
        }
        System.out.println("paint");
    }


    private void paintClick(Graphics g) {
        g.setColor(stampColor);
        g.fillOval(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2);
    }

    public void setIndex(int index, int rows) {
        this.index = index;
        row = index / rows;
        column = index % rows;
    }


    public void addButton(JPanel p) {
        p.add(this);
    }

    public static int calculateIndex(int row, int column) {
        String sIndex = "" + row + column;
        return Integer.parseInt(sIndex);
    }
}
