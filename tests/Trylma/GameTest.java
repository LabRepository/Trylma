package Trylma;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {
    Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game(0);
    }

    @Test
    public void getGameId() throws Exception {
        setUp();
        assertEquals(game.getGameid(), 0);
    }

}