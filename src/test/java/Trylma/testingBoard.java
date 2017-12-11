package Trylma;

import static org.junit.Assert.*;

import org.junit.Test;

public class testingBoard {

    @Test
    public void testUnsupportedAmountOfSets() {
        try {
            Board board = new Board(6, 3);
        } catch (RuntimeException r) {
            assertEquals("numberOfSetsUnavailableForSixPlayers", r.getMessage());
        }
    }

    @Test
    public void testUnsupportedAmountOfPlayers() {
        try {
            Board board = new Board(5, 3);
        } catch (RuntimeException r) {
            assertEquals("numberOfPlayersIncorrect", r.getMessage());
        }
    }

    @Test
    public void creatingTwoObjectsCreatesDifferentObjects() {
        try {
            Board board = new Board(4, 1);
            Board board2 = new Board(4, 1);
            assertNotEquals(board, board2);
        } catch (RuntimeException r) {
        }
    }
}
