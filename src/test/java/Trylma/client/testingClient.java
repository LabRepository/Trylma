package Trylma.client;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
//RUN Server!
public class testingClient {
    Client c = new Client();
    @Test
    public void testmovelegal(){
        c.sendmove(1,1,0,0);
    }
    @Test
    public void testmoveillegal(){
        try {
            c.sendmove(2,1,0,3);
        } catch (RuntimeException r) {
            assertEquals("InvalidMove", r.getMessage());
        }
    }

    @Test
    public void testcastcolor(){
        c.castcolor("BLACKPAWN");
        assertEquals(java.awt.Color.BLACK,c.getColor());
    }
}
