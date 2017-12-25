package Trylma.server;

import Trylma.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

// TODO ADD CONNECTION RESET HANDLING

public class TestServer {
    private static final int port = 12345;
    static Vector<Player> playerlist = new Vector<>();
    // counter for clients
    static int i = 0;
    private static ServerSocket ss;
    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static Thread t;
    private static Player currentplayer;
    // Now i will test one game implementation,
    public Game currentgame;
    public static void main(String[] args) throws IOException {
        // server is listening on declared port
        ss = new ServerSocket(port);
        Socket s;
        // running infinite loop for getting
        // client request
        while (true) {
            // Accept the incoming request
            s = ss.accept();
            System.out.println("New client request received : " + s);

            // obtain input and output streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            // Create a new handler object for handling this request.
            currentplayer = new Player(s, "player " + i, dis, dos);

            // Create a new Thread with this object.
            t = new Thread(currentplayer);

            // add this player to active clients list
            playerlist.add(currentplayer);

            // start the thread.
            t.start();

            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            i++;

        }
    }

}
