package Trylma.server;

import Trylma.Color;

import java.io.*;
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
                    System.out.print(id + "QUIT");
                } else if (received.startsWith("START")) {
                    System.out.print(id + "START");
                } else if (received.startsWith("BOT")) {
                    System.out.print(id + "BOT");
                } else if (received.startsWith("TEST")){
                    try {
                        Server.gamelobby.addplayer(this);
                        System.out.println(Server.gamelobby.check());
                    } catch (RuntimeException e) {
                        System.out.println(id + " : " + e.getMessage());
                    }
                }



            } catch (IOException e) {
                try {
                    s.close();
                    System.out.println(id + " Player Connection lost!");
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
            e.printStackTrace();
        }

    }

    public void move(String received){
//        if (color == Server.gamelobby.game.getCurrentplayercolor()) { //TODO THIS MOMENT Doesn't work
//            StringTokenizer st = new StringTokenizer(received, ";");
//            st.nextToken();
//            System.out.print(st.nextToken());
//            System.out.print(st.nextToken());
//            int startX = Integer.parseInt(st.nextToken());
//            int startY = Integer.parseInt(st.nextToken());
//            int goalX = Integer.parseInt(st.nextToken());
//            int goalY = Integer.parseInt(st.nextToken());

//            if (Server.gamelobby.game.legalmove(startX,startY,goalX,goalY)) {
//  //              Server.gamelobby.game.move(startX, startY, goalX, goalY);
//                //TODO Write to all players in game (recieved) as move
//            } else {
//                try {
//                    os.writeUTF("Wrong Move");
//                    os.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

}
