package Trylma.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class testBoardGUI {
    @Test
    public void testUnsupportedAmountOfSets() {
        try {
            BoardGUI board = new BoardGUI(6, 3);
        } catch (RuntimeException r) {
            assertEquals("numberOfSetsUnavailableForSIXPlayers", r.getMessage());
        }
    }

    @Test
    public void testUnsupportedAmountOfPlayers() {
        try {
            BoardGUI board = new BoardGUI(5, 3);
        } catch (RuntimeException r) {
            assertEquals("numberOfPlayersIncorrect", r.getMessage());
        }
    }

    @Test
    public void creatingTwoObjectsCreatesDifferentObjects() {
        try {
            BoardGUI board = new BoardGUI(4, 1);
            BoardGUI board2 = new BoardGUI(4, 1);
            assertNotEquals(board, board2);
        } catch (RuntimeException r) {
        }
    }
}