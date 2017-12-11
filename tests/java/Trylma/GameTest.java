package Trylma;

import Trylma.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class GameTest {
    Gamelobby game;

    @Before
    public void setUp() throws Exception {
        game = new Gamelobby(0, new Player(3, new Socket()));
    }

    @Test
    public void getGameId() throws Exception {
        setUp();
        assertEquals(game.getGameid(), 0);
    }

}