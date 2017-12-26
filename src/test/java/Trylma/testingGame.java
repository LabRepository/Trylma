package Trylma;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testingGame {

    @Test
    public void movingAlgorithmWorks() {
        try {
            Game game = new Game(6, 1);
            game.board.board[12][12].setState("BLACKPAWN");
            game.board.board[10][12].setState("BLACKPAWN");
            assertEquals(true, game.legalmove(12, 12, 8, 12));
        } catch (RuntimeException r) {
            System.out.print(r.getMessage());
        }
    }

}
