package Trylma.client;

import Trylma.Color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

//TODO Write doc and tests
public class Client {
    static DataInputStream dis;
    static DataOutputStream dos;
    final static int ServerPort = 12345;
    Scanner scn = new Scanner(System.in);
    InetAddress ip;
    Socket s;
    Thread sendMessage;
    Thread readMessage;
    String turn = "";
    String info = "";
    String msg;
    StringTokenizer st;
    Integer gamesize;
    Color color;


    Client() {
        try {
            ip = InetAddress.getByName("localhost");
            s = new Socket(ip, ServerPort);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        //TODO delete this when gui would be implemented
        sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String msg = scn.nextLine();
                    try {

                        dos.writeUTF(msg);
                    } catch (IOException e) {
                        try {
                            s.close();
                            System.out.print("Connection Lost!");
                            System.exit(1);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        sendMessage.start();

        //TODO implement input handling to be continued
        // readMessage thread
        readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        msg = dis.readUTF();
                    } catch (IOException e) {
                        try {
                            s.close();
                            System.exit(1);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    inputhandler();
                }
            }
        });
        readMessage.start();
    }

    /**
     * Function for handling inupts
     */

    private void inputhandler(){
        if(msg != null){
            if(msg.startsWith("START")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                gamesize = Integer.parseInt(st.nextToken());
                //TODO implement game start on client side
            } else if(msg.startsWith("Wrng")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                info = st.nextToken();
            } else if(msg.startsWith("MOVE")){
                StringTokenizer st = new StringTokenizer(msg,";");
                st.nextToken();
                int startX = Integer.parseInt(st.nextToken());
                int startY = Integer.parseInt(st.nextToken());
                int goalX = Integer.parseInt(st.nextToken());
                int goalY = Integer.parseInt(st.nextToken());
                //TODO implement move on client side
            } else if(msg.startsWith("RESTART")){
                //TODO implement game restart
            } else if(msg.startsWith("TURN")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                turn = st.nextToken();
                turn = turn + " turn";
            } else if(msg.startsWith("WIN")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                info = st.nextToken() + " Wins!";
                //TODO ENDGAME IMPLEMENT (back to start game...  etc)
            } else if(msg.startsWith("COLOR")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                String color =  st.nextToken();
                castcolor(color); //TODO implement this function (below)
            }

        }
    }
    /**
     * Function send "JOIN" (to game lobby) request to server
     * Use this when server can handle more than 1 game
     * @param lobbyid Lobby ID / Here we use only one lobby
     * @see Trylma.server.Server
     */
    public void sendjoinlobby(int lobbyid) {
        try {
            if (lobbyid != 0) {
                dos.writeUTF("JOIN " + lobbyid);
                dos.flush();
            } else {
                dos.writeUTF("JOIN");
                dos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function send "QUIT" (from game lobby) request to server
     * Use this when server can handle more than 1 game
     * @see Trylma.server.Server
     */
    public void sendquitlobby() {
        send("QUIT");
    }

    /**
     * Function send "MOVE;xs;ys;xe;ye" request to server for move validation
     * x and y represent mathematical vector [x,y]
     *
     * @param xs x start point
     * @param ys y start point
     * @param xe x goal point
     * @param ye y goal point
     *
     */
    public void sendmove(int xs, int ys, int xe, int ye) {
            int x = xe - xs;
            int y = ys - ye;
        if ((x == 1 && y == 1) || (x == 1 && y == -1) || (x == -1 && y == 1) ||
                (x == -1 && y == -1) || (x == 2 && y == 0) || (x == -2 && y == 0)) {
            send("MOVE;" + xs + ";" + ys + ";" + xe + ";" + ye);
        }
    }

    /**
     * Function send "START" (game) request to server
     */
    public void sendstart(){
        send("START");
    }

    /**
     * Function send "DONE" move to server
     */
    public void senddone(){
        send("DONE");
    }

    /**
     * Function to sending messages to server
     * @param msg String message
     */
    public void send(String msg){
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function call to add bot to game;
     */
    public void sendaddbot(){
        //TODO implement
    }

    public void castcolor(String c){
        //TODO implement casting to Color
    }
    public static void main(String args[]) throws IOException {
        Client c = new Client();
    }

}
