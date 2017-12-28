package Trylma.server;

import Trylma.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Player handler implementation with handling requests
 *
 * @author Jakub Czyszczonik
 */


public class Player extends Thread implements AbstractPlayer {
    /**
     * Player ID
     */
    private int id;
    private Color color;
    private DataInputStream is;
    private DataOutputStream os;
    public Socket s;
    /**
     * Player State
     */
    boolean isloggedin;


    /**
     * Constructor makes new Data Input and Output Stream, and changes player state as login
     * @param s Player socket
     * @param id Player ID
     * @see DataOutputStream
     * @see DataInputStream
     * @see Socket
     */
    public Player(Socket s, int id) {

        this.s = s;
        this.isloggedin = true;
        this.id = id;
        try{
            is = new DataInputStream(s.getInputStream());
            os = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This function runs listening thread for this player.
     */
    @Override
    public void run() {

        String received;
        while (isloggedin) {
            try {

                received = is.readUTF();
                System.out.println(id + ": " + received);
                //TODO implement methods
                if (received.startsWith("JOIN")) {
                    //TODO implement this when server can handle more than 1 game
                } else if (received.startsWith("MOVE")&& Server.gamelobby.getstate()) {
                    if(Server.gamelobby.isturn(color)) {
                        move(received);
                    }
                } else if (received.startsWith("QUIT")) {
                    //TODO implement this when server can handle more than 1 game
                } else if (received.startsWith("START")) {
                    Server.gamelobby.rungame();
                } else if (received.startsWith("BOT")) {
                    Server.gamelobby.addbot();
                } else if (received.startsWith("DONE") && Server.gamelobby.isturn(color)) {
                    Server.gamelobby.moveturn(color);
                }

            } catch (IOException e) {
                try {
                    s.close();
                    System.out.println(id + " Player died!");
                    isloggedin = false;
                    Server.gamelobby.exit(this);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }            }

        }
        try {
            this.is.close();
            this.os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void send(String msg){
        try{
            os.writeUTF(msg);
            os.flush();
        } catch (IOException e){
            Server.gamelobby.exit(this);

        }

    }

    public void move(String received){
        StringTokenizer st = new StringTokenizer(received,";");
        st.nextToken();
        int startX = Integer.parseInt(st.nextToken());
        int startY = Integer.parseInt(st.nextToken());
        int goalX = Integer.parseInt(st.nextToken());
        int goalY = Integer.parseInt(st.nextToken());
        if(Server.gamelobby.game.board.board[startX][startY].getState() == color.toString()) {
                try{
                    Server.gamelobby.game.moving(startX, startY, goalX, goalY);
                    Server.gamelobby.hasWinner();
                    Server.gamelobby.moveturn(color);
                    Server.gamelobby.respond(received);
                } catch(RuntimeException e) {
                    send("Wrng;MOVE");
                }
        } else {
            send("Wrng;MOVE");
        }
    }

    /**
     * Color getter
     * @see Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Color setter
     * @param color
     * @see Color
     */
    public void setColor(Color color) {
        this.color = color;
        send("COLOR;"+color);
    }
}
