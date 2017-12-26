package Trylma.server;

import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class ServerTest {

    @Test
    public void joinlobby() throws Exception {
        Server s = new Server();
        try {
            s.gamelobby.addplayer(new Player(s.listener.accept(), 0));
            s.gamelobby.addplayer(new Player(s.listener.accept(), 0));
            s.gamelobby.addplayer(new Player(s.listener.accept(), 0));
            s.gamelobby.addplayer(new Player(s.listener.accept(), 0));
            s.gamelobby.addplayer(new Player(s.listener.accept(), 0));
            s.gamelobby.addplayer(new Player(s.listener.accept(), 0));
            s.gamelobby.addplayer(new Player(s.listener.accept(), 0));
            s.shoutdown();
        } catch (RuntimeException r) {
            assertEquals("numberOfPlayersIncorrect", r.getMessage());
        }




    }

}