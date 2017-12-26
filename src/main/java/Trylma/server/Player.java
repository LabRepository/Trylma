package Trylma.server;

import Trylma.Color;

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
    private DataInputStream dis;
    private DataOutputStream dos;
    Socket s;
    boolean isloggedin;


    public Player(Socket s) {

        this.s = s;
        this.isloggedin = true;
        try{
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        String received;
        while (isloggedin) {
            try {
                received = dis.readUTF();
                System.out.println(id + ": " + received);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            this.dis.close();
            this.dos.close();

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
            dos.writeUTF(msg);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
