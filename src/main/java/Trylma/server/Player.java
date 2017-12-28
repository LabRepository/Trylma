package Trylma.server;

import Trylma.*;
import Trylma.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Basic player implementation
 *
 * @author Jakub Czyszczonik
 */


public class Player extends Thread implements AbstractPlayer {
    private int id;
    private Color color;
    private DataInputStream is;
    private DataOutputStream os;
    Socket s;
    boolean isloggedin;


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
                } else if (received.startsWith("MOVE")) {
                        move(received);
                } else if (received.startsWith("QUIT")) {
                    //TODO implement this when server can handle more than 1 game
                } else if (received.startsWith("START")) {
                    Server.gamelobby.rungame();
                } else if (received.startsWith("BOT")) {
                    Server.gamelobby.addbot();
                }

            } catch (IOException e) {
                try {
                    s.close();
                    System.out.println(id + " Player died!");
                    isloggedin = false;
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
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    public void send(String msg){
        try{
            os.writeUTF(msg);
            os.flush();
        } catch (IOException e){
            Server.gamelobby.exit(this);
        }

    }

    public void move(String received){
        //TODO add checking game queue
        StringTokenizer st = new StringTokenizer(received,";");
        st.nextToken();
        int startX = Integer.parseInt(st.nextToken());
        int startY = Integer.parseInt(st.nextToken());
        int goalX = Integer.parseInt(st.nextToken());
        int goalY = Integer.parseInt(st.nextToken());
        try {
            Server.gamelobby.game.moving(startX, startY, goalX, goalY);
            Server.gamelobby.respond(received);
        } catch (RuntimeException e){
            send("WMOVE");
        }
    }

}
