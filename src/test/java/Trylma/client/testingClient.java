package Trylma.client;


import Trylma.server.Server;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testingClient {


    @Test
    public void testmoveillegal(){
        Server s = new Server(12345);
        Client c = new Client();
        try {
            c.sendmove(2,1,0,3);
        } catch (RuntimeException r) {
            assertEquals("InvalidMove", r.getMessage());
        }
        s.shoutdown();
    }

    @Test
    public void testcastwrong(){
        Server s = new Server(12345);
        Client c = new Client();
        c.castcolor("ajdsbjhabhd");
        assertEquals(null,c.getColor());
        s.shoutdown();
    }


}
