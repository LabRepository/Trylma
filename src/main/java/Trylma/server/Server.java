package Trylma.server;

import Trylma.Gamelobby;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Basic Server Version to develop
 * @author Jakub Czyszczonik
 */
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
    volatile static ArrayList<Player> players = new ArrayList<>();
    /**
     * Server Listener
     */
    private ServerSocket listener;
    /**
     * Game Lobby of current game
     */
    static Gamelobby gamelobby = new Gamelobby();
    /**
     * Unique number of player
     */
    private volatile int NoPlayers = 0;
    /**
     * Limit Player on server
     */
    private static final int PLAYER_LIMIT = 6;

    /**
     * Default constructor
     */
    public Server(int port) {
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
        Server s = new Server(port);
        try {
            s.listening();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Starts listening socket and add new players to Array List.
     * @throws IOException Socket connection can throw this
     */
    private void listening() throws IOException {
        while (isrunning) {
            if(players.size() < PLAYER_LIMIT && !gamelobby.getstate()) {
                players.add(new Player(listener.accept(),NoPlayers));
                players.get(players.size() - 1).start();
                players.get(players.size() - 1).send("Welcome on game Server");
                //auto joining to gameloobby
                gamelobby.addplayer(players.get(players.size() - 1));
                NoPlayers++;
            } else  {
                Player p = new Player(listener.accept(),99);
                p.send("Too many players, Please come later!");
                p.s.close();
            }

        }
    }
    public static void exit(Player player){
        if(players.contains(player)) {
            players.remove(player);
        }
    }

    public void shoutdown(){
        isrunning = false;
        try {
            listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
