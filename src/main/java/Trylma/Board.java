package Trylma;

/**
 * Class initializing and holding basically all the information about state of the board.
 * Contains an array of Fields, methods for building the map, and setting up appropriate amount of pawns.
 *
 * @author Michał Budnik
 */
public class Board {
    int height = 17;
    int width = 25;
    //Length 4 - 10 pawns, length 3 - 6 pawns, length 2 - 3 pawns
    private int lengthOfPawnFields = 4;

    public Fields[][] board = new Fields[width][height];

    Board(int NoPlayers, int sets) {
        setupBoard();
        setupPlayers(NoPlayers, sets);
    }
    /**
     * Initialize fields as "BLOCKED", then call methods to appropriately open playable fields.
     */
    private void setupBoard() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = new Fields("BLOCKED");
            }
        }
        constructTriangle(0, 13, 12, "EMPTY", 't');
        constructTriangle(16, 13, 12, "EMPTY", 'r');
    }

    /**
     * Method constructing triangle on the map.
     *
     * @param top    Sets the row where triangle's top is.
     * @param length Sets height of the triangle.
     * @param mid    Sets the middle column of the triangle.
     * @param state  Sets the state of a given field.
     * @param mode   Sets whether triangle should look like /\ or \/.
     */
    private void constructTriangle(int top, int length, int mid, String state, char mode) {
        switch (mode) {
            case 't':
                //Grows from row = top, with every next row adds 1 to length of constructed line
                for (int row = 0; row < length; row++) {
                    constructLine(mid, row + top, row, state);
                }
                break;

            case 'r':
                //Gets smaller from row = top-length, with every next row subtracts 1 to length of constructed line
                for (int row = length - 1; row >= 0; row--) {
                    constructLine(mid, top - row, row, state);
                }
                break;

            default:
                throw new RuntimeException("modeForTriangleBuildingNotSupported");
        }
    }

    /**
     * Method constructing one row of a triangle.
     * Recursive because of repeating shapes of triangle every other line.
     *
     * @param mid   Setting mid column of a triangle.
     * @param row   Setting currently working row on.
     * @param i     Current 'offset' from the middle row, repeats offsets from 2 lines before (because of repeating shapes).
     * @param state Setting states of a field.
     */
    private void constructLine(int mid, int row, int i, String state) {
        try {
            board[mid + i][row].setState(state);
            board[mid - i][row].setState(state);
        } catch (RuntimeException r) {
            r.getMessage();
        }
        if (i - 2 >= 0) {
            constructLine(mid, row, i - 2, state);
        }
    }

    /**
     * Based on number of players and sets they want to play with, initialises appropriate amount of pawn colors.
     * Throws exceptions if incorrect data provided.
     *
     * @param NoPlayers Number of players.
     * @param sets      Number of sets.
     */
    private void setupPlayers(int NoPlayers, int sets) {
        switch (NoPlayers) {
            case 2:
                if (sets == 1) {
                    initiateTwoColors();
                } else if (sets == 2) {
                    initiateFourColors();
                } else if (sets == 3) {
                    initiateSixColors();
                } else {
                    throw new RuntimeException("numberOfSetsUnavailableForTWOPlayers");
                }
                break;
            case 3:
                if (sets == 1) {
                    initiateThreeColors();
                } else if (sets == 3) {
                    initiateSixColors();
                } else {
                    throw new RuntimeException("numberOfSetsUnavailableForTHREEPlayers");
                }
                break;
            case 4:
                if (sets == 1) {
                    initiateFourColors();
                } else {
                    throw new RuntimeException("numberOfSetsUnavailableForFOURPlayers");
                }
                break;
            case 6:
                if (sets == 1) {
                    initiateSixColors();
                } else {
                    throw new RuntimeException("numberOfSetsUnavailableForSIXPlayers");
                }
                break;
            default:
                throw new RuntimeException("numberOfPlayersIncorrect");
        }
    }


    private void initiateTwoColors() {
        constructTriangle(0, lengthOfPawnFields, 12, "BLACKPAWN", 't');
        constructTriangle(16, lengthOfPawnFields, 12, "WHITEPAWN", 'r');
    }

    private void initiateThreeColors() {
        constructTriangle(7, lengthOfPawnFields, 3, "YELLOWPAWN", 'r');
        constructTriangle(7, lengthOfPawnFields, 21, "REDPAWN", 'r');
        constructTriangle(16, lengthOfPawnFields, 12, "WHITEPAWN", 'r');
    }

    private void initiateFourColors() {
        constructTriangle(7, lengthOfPawnFields, 3, "YELLOWPAWN", 'r');
        constructTriangle(7, lengthOfPawnFields, 21, "REDPAWN", 'r');
        constructTriangle(9, lengthOfPawnFields, 3, "GREENPAWN", 't');
        constructTriangle(9, lengthOfPawnFields, 21, "BLUEPAWN", 't');

    }

    private void initiateSixColors() {
        initiateFourColors();
        initiateTwoColors();
    }
}