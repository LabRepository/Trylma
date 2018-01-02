package Trylma;

import org.junit.Test;

import static org.junit.Assert.*;

public class testingGame {

    @Test
    public void movingAlgorithmWorks() {
        Game game = new Game(2, 1);
        try {
            game.moving(9, 13, 13, 12);
        } catch (RuntimeException e){
            assertEquals("Move not legal!",e.getMessage());
        }
    }
    @Test
    public void botMovesInALogicalWay() {
        Game game = new Game(2, 1);
        try {
            int[] result = game.moveBot("BLACKPAWN");
            assertEquals(game.board.board[result[2]][result[3]].getState(), "BLACKPAWN");
        } catch (RuntimeException e){
            System.out.print("Move not legal!");
        }
    }
    @Test
    public void testSetWinWhenEnteringWinZone() {
        Game game = new Game(2, 1);
        game.board.board[16][4] = new Fields("WHITEPAWN");
        game.board.board[15][3] = new Fields();
        try {
            game.moving(16, 4, 15, 3);
            assertTrue(game.board.board[15][3].getAtFinish());
        } catch (RuntimeException e){
            System.out.print("Move not legal!");
        }
    }

}
