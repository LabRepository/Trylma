package Trylma.server;

import Trylma.Color;

import java.io.*;
import java.net.Socket;

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
                if(received.startsWith("JOIN")){
                    Server.gamelobby.addplayer(this);
                } else if (received.startsWith("MOVE")){

                } else if (received.startsWith("QUIT")) {

                } else if (received.startsWith("START")) {

                } else if (received.startsWith("BOT")){

                } else if (received.startsWith("START")){

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

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
            e.printStackTrace();
        }

    }

}
