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
    /**
     * Server State:  true == running, false == stopped.
     */
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
    static Gamelobby gamelobby = new Gamelobby();
    /**
     * Unique number of player
     */
    public volatile int NoPlayers = 0;
    /**
     * Limit Player on server
     */
    private static final int PLAYER_LIMIT = 6;

    /**
     * Default constructor
     */
    public Server() {
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

    /**
     * Starts listening socket and add new players to Array List.
     * @throws IOException
     */
    public void listening() throws IOException {
        while (isrunning) {
            if(players.size() < PLAYER_LIMIT) {
                players.add(new Player(listener.accept(),NoPlayers));
                players.get(players.size() - 1).start();
                players.get(players.size() - 1).send("Welcome on game Server");
                //auto joining to gameloobby
                gamelobby.addplayer(players.get(players.size() - 1));
                NoPlayers++;
            } else
            {
                Player p = new Player(listener.accept(),99);
                p.send("Too many players, Please come later!");
                p.s.close();
            }

        }
    }

}
