package Trylma;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

}
