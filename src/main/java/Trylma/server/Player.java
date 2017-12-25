package Trylma.server;

import Trylma.Color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Basic player implementation
 *
 * @author Jakub Czyszczonik
 */

// TODO ADD HANDLING MSG TO GAME and HANDLING MSG to Server

public class Player implements AbstractPlayer, Runnable {
    private int id;
    private Color color;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    private String name;


    public Player(Socket s, String name,
                  DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin = true;
        try {
            dos.writeUTF("Welcome in our Trylma Server " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        String received;
        while (isloggedin) {
            try {
                // receive the string
                received = dis.readUTF();
                System.out.println(name + ": " + received);
                this.parse(received);



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


    private void parse(String received) throws IOException {

        if (received.equals("logout")) {
            System.out.println(s + " disconnected (" + name + ")");
            this.isloggedin = false;
            this.s.close();
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

}
