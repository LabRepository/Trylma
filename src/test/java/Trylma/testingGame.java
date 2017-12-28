package Trylma;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testingGame {

    @Test
    public void movingAlgorithmWorks() {
        //TODO repair test
        try {
            Game game = new Game(2, 1);
            game.moving(9, 13, 10, 12);
            game.moving(9,13,10,12);
        } catch (RuntimeException r) {
            System.out.print(r.getMessage());
        }
    }

}
