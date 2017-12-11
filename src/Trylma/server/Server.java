package Trylma.server;

import Trylma.Game;
import Trylma.player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Server for java project
 *
 * @author Jakub Czyszczonik
 */
public class Server {
    private static boolean isrunning = false;
    private static ArrayList<Player> lobby;
    private static Player cur;
    public ServerSocket servers;
    /**
     * The port that the server listens on.
     */
    private int port = 12345;
    /**
     * Array list of current games
     */
    private ArrayList<Game> games;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    public Server(int port) throws IOException {
        this.port = port;
        servers = new ServerSocket(port);
        System.out.println("Server is running on port : " + port);
        games = new ArrayList<Game>();
        lobby = new ArrayList<Player>();
        isrunning = true;

    }

    public Server() throws IOException {
        servers = new ServerSocket(port);
        System.out.println("Server is running on port : " + port);
        games = new ArrayList<Game>();
        lobby = new ArrayList<Player>();
        isrunning = true;
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        try {
            while (isrunning) {
                new Player(1, server.servers.accept()).start(); //TODO Players List
            }
        } finally {
            server.servers.close();
        }
    }

    public boolean getstate() {
        return isrunning;
    }

    public void shoutdown() {
        isrunning = false;
    }

}
