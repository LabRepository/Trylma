package Trylma.server;

import org.junit.Test;

import java.net.Socket;

public class ServerTest {

    Server s = new Server();
    @Test
    public void playerexit() throws Exception {
        s.exit(new Player(new Socket(),1));
    }

}