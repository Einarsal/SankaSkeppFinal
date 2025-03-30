package MainPackage;

public interface OpponentBoardInterface extends BoardInterface {
    void checkGuess(Button s);

    Button getButton(int index);
}