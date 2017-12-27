package Trylma.client;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
//TODO implement mojito framework (later)
//RUN SERVER !!!
public class testingClient {
    Client c = new Client();

    @Test
    public void testmovelegal(){
        c.move(1,1,0,0);
    }
    @Test
    public void testmoveillegal(){
        try {
            c.move(2,1,0,3);
        } catch (RuntimeException r) {
            assertEquals("InvalidMove", r.getMessage());
        }
    }
}
