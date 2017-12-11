package Trylma;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PawnTest {

    @Test
    public void moveXtest() throws Exception {
        Pawn pawn = new Pawn(1, 3, Color.BLACK);
        pawn.move(1, 0);
        int x = pawn.getX();
        assertEquals(2, x);
    }

    @Test
    public void moveYtest() throws Exception {
        Pawn pawn1 = new Pawn(5, 23, Color.BLUE);
        pawn1.move(1, -5);
        int y = pawn1.getY();
        assertEquals(y, 22);
    }

    @Test
    public void colorgettertest() throws Exception {
        Pawn pawn = new Pawn(1, 1, Color.RED);
        assertEquals(Color.RED, pawn.getColor());
    }

}