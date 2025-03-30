package MainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Leaderboard extends JPanel implements miscBoardInterface{

    public Color mainColor;
    private final ProfileDropDownList profileDropDownList;
    private final TextField txf;
    private final TextArea txa;   // I adore penis
    private final NewPlayerButton newPlayerButton;
    public static boolean profileChosen = false;
    static Profile currentProfile;
    public static ArrayList<Profile> profiles = new ArrayList<>();
    UserPlayer userPlayer;


    public Leaderboard(UserPlayer userPlayer) {
        this.userPlayer = userPlayer;
        txf = new TextField();
        txf.setVisible(false);
        mainColor = Button.buttonColor;
        setBackground(mainColor);
        setVisible(true);
        profiles = getPlayersFromFile();
        profileDropDownList = new ProfileDropDownList();
        txa = new TextArea();
        newPlayerButton = new NewPlayerButton();
        addComponents();
        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    public void changeColorScheme(Color c){
        mainColor = c;
        profileDropDownList.changeColor(c);
        txf.changeColor(c);
        txa.changeColor(c);
        newPlayerButton.changeColor(c);
//        revalidate();

    }

    @Override
    public void addToPanel(JPanel p, GridBagConstraints c) {
        p.add(this, c);
    }

    @Override
    public void showNextBoat(int currentBoatIndex) {

    }

    @Override
    public void enableBoard(boolean enabled) {
        setEnabled(enabled);
    }

    @Override
    public void showBoard(boolean visible) {
        setVisible(visible);
    }

    public void setVisibility(boolean visible) {
        setVisible(visible);
    }

    public void addToGridBagLayout(JPanel p, GridBagConstraints c) {
        p.add(this, c);
    }

    private void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;

        add(profileDropDownList, c);

        c.gridwidth = 3;
        c.weightx = 0.75;
        add(txf, c);

        c.gridx = 3;
        c.gridwidth = 1;
        c.weightx = 0.25;
        add(new NewPlayerButton(), c);


        c.weighty = 1;
        c.weightx = 1;
        c.gridy = 1;
        c.gridwidth = 4;
        c.gridx = 0;

        JScrollPane scrollPane = new JScrollPane(txa);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        add(scrollPane, c);
    }

    protected void profileChosen(Profile p) {
        profileChosen = true;
        currentProfile = p;
        writeToFile();
        userPlayer.bh.buttons[0].setEnabled(true);
    }

    public static void writeToFile()  {
        FileWriter fl;
        File leaderboardFile = new File("src/MainPackage/Leaderboard.txt");

        try {
            fl = new FileWriter(leaderboardFile);
            for(Profile p : profiles){
                fl.write(p +"\n");
            }
            fl.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private ArrayList<Profile> sortProfileList(ArrayList<Profile> list) {
        if (list.size() <= 1) {
            return list;
        }
        for (int i = 1; i < list.size(); i++) {
            Profile temp = list.get(i);
            int index = i - 1;
            while (index >= 0 && list.get(index).score < temp.score) {
                list.set(index + 1, list.get(index));
                index -= 1;
            }
            list.set(index + 1, temp);
        }
        return list;
    }

    private ArrayList<Profile> getPlayersFromFile() {
        Scanner fr = createScanner();
        if (fr == null) {
            return null;
        }
        ArrayList<Profile> profileList = new ArrayList<>();
        while (fr.hasNext()) {
            profileList.add(convertPlayer(fr.nextLine()));
        }
        return sortProfileList(profileList);
    }

    private Scanner createScanner() {
        try {
            File leaderboardFile = new File("src/MainPackage/Leaderboard.txt");
            return new Scanner(leaderboardFile);

        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        }
        return null;
    }

    private Profile convertPlayer(String s) {
        String userName = s.substring(0, s.lastIndexOf(":") + 1).trim();
        int playerScore = Integer.parseInt(s.substring(s.lastIndexOf(":") + 1).trim());
        return new Profile(userName, playerScore);
    }


    private class NewPlayerButton extends ClickButton {

        protected NewPlayerButton() {
            super("Add");
            changeColor(Button.buttonColor);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {     // I love men
                    try {
                        addPlayer();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        public void changeColor(Color c){
            setBackground(c);
            setForeground(UserPlayer.backgroundColor);
        }

        private void addPlayer() throws IOException {
            String profileName = txf.getText();
            int profilesIndex = profileExists(profileName);
            if (profilesIndex != -1) {
                profileChosen(profiles.get(profilesIndex));
                return;
            }
            Profile p = new Profile(txf.getText() + ":", 0);
            profileDropDownList.addItem(p);
            profiles.add(p);
            profileDropDownList.setVisible(true);
            profileDropDownList.setEnabled(false);
            txf.setVisible(false);
            setVisible(false);
            txa.writeToTextArea();
            profileChosen(p);
        }

        private int profileExists(String profileName) {
            for (int i = 0; i < profiles.size(); i++) {
                Profile p = profiles.get(i);
                if (profileName.equals(p.profileName)) {
                    return i;
                }
            }
            return -1;
        }
    }


    private class ProfileDropDownList extends JComboBox {
        private final Object item = "New Player:";

        protected ProfileDropDownList() {
            setBackground(mainColor);
            setForeground(UserPlayer.backgroundColor);
            addPlayers();
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    listItemChosen();
                }
            });
        }

        public void changeColor(Color c){
            setBackground(c);
        }

        private void listItemChosen() {
            if (profileChosen) {
                return;
            }
            if (getSelectedItem() == item) {
                this.setVisible(false);
                txf.setVisible(true);
                return;
            } else {
                System.out.println(currentProfile);
            }
            profileChosen((Profile)getSelectedItem());
        }

        private void addPlayers() {
            for (Profile p : profiles) {
                addItem(p);
            }
            addItem(item);
        }


    }


}