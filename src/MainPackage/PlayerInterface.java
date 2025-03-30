package MainPackage;

public interface PlayerInterface {
    /*
    Boat[] boats = {new Boat(1,1)};
     */
    Boat[] boats = {new Boat(3, 2),
            new Boat(3, 1),
            new Boat(1,  4),
            new Boat(2, 1),
            new Boat(1, 1)};
    void makeGuess();
}
