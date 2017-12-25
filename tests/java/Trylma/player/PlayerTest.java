//package Trylma.player;
//
//import Trylma.Color;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.net.Socket;
//
//import static org.junit.Assert.assertEquals;
//
//public class PlayerTest {
//    Player player;
//
//    @Before
//    public void setUp() throws Exception {
//        player = new Player(1, new Socket());
//    }
//
//    @Test
//    public void getID() throws Exception {
//        setUp();
//        assertEquals(player.getID(), 1);
//
//    }
//
//    @Test
//    public void getColor() throws Exception {
//        setUp();
//        player.setColor(Color.BLACK);
//        assertEquals(player.getColor(), Color.BLACK);
//    }
//
//
//    @Test
//    public void setID() throws Exception {
//        setUp();
//        player.setID(3);
//        assertEquals(player.getID(), 3);
//    }
//
//}