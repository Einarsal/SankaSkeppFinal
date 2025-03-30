package MainPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ComputerPlayer implements PlayerInterface {
    private final PlayerBoardInterface playerBoard;
    OpponentBoardInterface opponentBoard;
    Button currentButton;
    ArrayList<Button> guessableSquares;

    public ComputerPlayer(PlayerBoardInterface playerBoard, OpponentBoardInterface opponentBoard) {
        this.playerBoard = playerBoard;
        this.opponentBoard = opponentBoard;
        placeBoats();
        makeGuessableSquaresArray();
    }

    private void makeGuessableSquaresArray() {
        guessableSquares = new ArrayList<>();
        guessableSquares.addAll(Arrays.asList(opponentBoard.getAllSquares()));
    }

    public void placeBoats() {
        Button[] squares = playerBoard.getAllSquares();
        for (Boat b : PlayerInterface.boats) {
            playerBoard.placeBoat(getRandomBoatSquares(squares, b));
            System.out.println("placeboats");
        }
    }

    private Button[] getRandomBoatSquares(Button[] squares, Boat boat) {
        Random random = new Random();
        Button[] boatSquares;
        do {
            int cornerIndex = random.nextInt(squares.length);
            Button cornerSquare = squares[cornerIndex];
            boatSquares = createArrayOfBoatSquares(cornerSquare, squares, boat);
        } while (!boatLocationIsValid(boatSquares));
        return boatSquares;
    }

    private Button[] createArrayOfBoatSquares(Button cornerSquare, Button[] squares, Boat boat) {
        int rows = boat.covRows;
        int columns = boat.covCols;
        int covSquares = rows * columns;
        Button[] boatSquares = new Button[covSquares];
        int i = 0;
        for (int j = 0; j < rows; j++) {
            for (int k = 0; k < columns; k++) {
                //System.out.println("" + Button.calculateIndex(j + cornerSquare.row, k + cornerSquare.column));
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
/*

 */
//    public void makeGuess() {
//        Random random = new Random();
//        int guessedSquareIndex = random.nextInt(guessableSquares.size());
//        Button s = guessableSquares.get(guessedSquareIndex);
//        guessableSquares.remove(guessedSquareIndex);
//        opponentBoard.checkGuess(s);
//    }


    //this guessing method is only for debugging
    public void makeGuess(){
        Button guessedSquare = guessableSquares.getLast();
        guessableSquares.removeLast();
        opponentBoard.checkGuess(guessedSquare);
    }


}
