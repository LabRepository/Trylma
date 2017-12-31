package Trylma.server;


import Trylma.Gamelobby;
import org.junit.Test;

import java.net.Socket;

import static junit.framework.TestCase.assertNotNull;

public class testingPlayer {

    @Test
    public void playerconstructortest(){

        Player p = new Player(new Socket(), 1);
        assertNotNull(p);
    }

}
