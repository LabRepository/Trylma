package Trylma.server;

import Trylma.Gamelobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

// TODO ADD CONNECTION RESET HANDLING

public class Server {
    /**
     * Server Port
     */
    private static final int port = 12345;
    private boolean isrunning = false;
    /**
     * ArrayList of Players
     */
    ArrayList<Player> players = new ArrayList<>();
    /**
     * Server Listener
     */
    public ServerSocket listener;
    /**
     * Game Lobby of current game
     */
    static Gamelobby gamelobby;
    /**
     * Unique number of player
     */
    private volatile int NoPlayers = 0;

    Server() {
        try {
            listener = new ServerSocket(port);
            isrunning = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Chinese checkers server is running on " + listener);
    }

    public static void main(String[] args) {
        Server s = new Server();
        try {
            s.listening();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void listening() throws IOException {
        while (isrunning) {
            if(players.size() < 6) {
                players.add(new Player(listener.accept(),NoPlayers));
                players.get(players.size() - 1).start();
                players.get(players.size() - 1).send("Welcome on game Server");
                NoPlayers++;
            }

        }
    }

    public void shoutdown(){
        isrunning = false;
        System.out.println("BYE");
    }

}
