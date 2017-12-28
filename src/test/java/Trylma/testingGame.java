package Trylma;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testingGame {

    @Test
    public void movingAlgorithmWorks() {
        Game game = new Game(2, 1);
        try {

            game.moving(9, 13, 10, 12);
        } catch (RuntimeException r) {
            System.out.print(r.getMessage());
            System.out.print(game.board.board[17][4].getState());
        }
    }

}
