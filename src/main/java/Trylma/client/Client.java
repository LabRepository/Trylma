package Trylma.client;

import Trylma.Color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

//TODO Write tests and Develop

/**
 * Basic client version (can handle inputs and send requests to server)
 */
public class Client {
    /**
     * Data input stream
     * @see DataInputStream
     */
    private static DataInputStream dis;
    /**
     * Data input stream
     * @see DataOutputStream
     */
    private static DataOutputStream dos;
    /**
     * Default Server Port
     */
    private final static int ServerPort = 12345;
    /**
     * Server Ip Adres
     * @see InetAddress
     */
    private InetAddress ip;
    /**
     * Socket
     * @see Socket
     */
    private Socket s;
    /**
     * Thread for sending requests
     * @see Thread
     */
    private Thread sendMessage;
    /**
     * Thread for reading responds from server
     * @see Thread
     */
    private Thread readMessage;
    /**
     * Contains Turn Color
     */
    private String turn = "";
    /**
     * Contains Info from server
     */
    private String info = "";
    /**
     * Contains message from server
     */
    private String msg;
    /**
     * String Tokenizer for parsing messages from server
     * @see StringTokenizer
     */
    private StringTokenizer st;
    /**
     * Contains number of players in current Game
     */
    private Integer gamesize;
    /**
     * Contains client current Color.
     */
    Color color;
    private Scanner scn = new Scanner(System.in);

    /**
     * Constructor makes new Data Input and Output Streams and Starts readMessage Thread for
     * reading messages from server
     */
    Client() {
        try {
            ip = InetAddress.getByName("localhost");
            s = new Socket(ip, ServerPort);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

        } catch (IOException e) {
            System.out.print("Server is Offline!");
            System.exit(1);
        }
        //TODO DELETE this when gui would be implemented
        createwritethread();
        sendMessage.start();
        //TODO END DELETE


        //TODO implement input handling to be continued
        createreadthread();
        readMessage.start();
    }


    /**
     * Creates readMessage Thread with inputhandler inside;
     */
    private void createreadthread(){
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
    }

    /**
     * TODO DELETE THIS When GUI would be implemented
     */
    private void createwritethread(){
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
    }

    /**
     * Function parse inputs from server
     * //TODO DEVELOP this function
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
                castcolor(color);
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

    /**
     * This Function casts String to Trylma.Color
     * @param c String to cast
     * @see Trylma.Color
     */
    public void castcolor(String c){
        switch(c){
            case "BLACKPAWN":
                color = Color.BLACKPAWN;
                break;
            case "WHITEPAWN":
                color = Color.WHITEPAWN;
                break;
            case "REDPAWN":
                color = Color.REDPAWN;
                break;
            case "BLUEPAWN":
                color = Color.BLUEPAWN;
                break;
            case "GREENPAWN":
                color = Color.GREENPAWN;
                break;
            case "YELLOWPAWN":
                color = Color.YELLOWPAWN;
                break;
            default:
                System.out.print("Wrong Color!");
                break;
        }
    }

    //TODO delete this when GUI would be implemented
    public static void main(String args[]) throws IOException {
        Client c = new Client();
    }

}
