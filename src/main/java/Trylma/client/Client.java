package Trylma.client;

import Trylma.Color;
import com.sun.java.accessibility.util.GUIInitializedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

//TODO Write tests and add mouse handler

/**
 * Basic client version (can handle inputs and send requests to server)
 * @author Jakub Czyszczonik
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
    Integer gamesize;
    /**
     * Contains client current Color.
     */
    Color color;
    /**
     * Contains hello message
     */
    JLabel hello = new JLabel("Welcome in our Trylma Client. Enjoy!");

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

        createreadthread();
        readMessage.start();

        GUImaker();

    }
//////////////////////////////////////////
//    GUI
//////////////////////////////////////////
    private JFrame frame = new JFrame("Chinese Checkers");
    private JPanel panel = new JPanel(new GridLayout(1,2));
    private JPanel functions = new JPanel(new FlowLayout());
    private JLabel yourcolor = new JLabel("Color");
    private JLabel serverinfo = new JLabel("Info");
    private JLabel size = new JLabel("Size");
    private JButton start = new JButton("Start");
    private JButton done = new JButton("DONE");
    private JButton addbot = new JButton("Add Bot");
    private JButton removebot = new JButton("Remove Bot");
    private BoardGUI board;








    private void GUImaker(){
        //Frame Section
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1,2));
        frame.add(panel);
        frame.setBackground(java.awt.Color.lightGray);
        panel.add(functions);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //Action Handlers
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendstart();
            }
        } );

        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                senddone();
            }
        } );

        addbot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendaddbot();
            }
        } );
        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendremovebot();
            }
        } );




        // JPanel (function) section
        functions.setLayout(new BoxLayout(functions, BoxLayout.PAGE_AXIS));
        functions.setBackground(java.awt.Color.lightGray);
        functions.add(hello);
        functions.add(yourcolor);
        functions.add(serverinfo);
        functions.add(size);
        functions.add(start);
        functions.add(done);
        functions.add(addbot);
        functions.add(removebot);

        // Labels font size
        yourcolor.setFont(new Font("Serif", Font.PLAIN, 20));
        serverinfo.setFont(new Font("Serif", Font.PLAIN, 20));
        size.setFont(new Font("Serif", Font.PLAIN, 20));
        hello.setFont(new Font("Serif", Font.PLAIN, 20));

        //Frame Section
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
//////////////////////////////////////////
//    GUI
//////////////////////////////////////////


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
     * Function parse inputs from server
     *
     */
    private void inputhandler(){
        if(msg != null){
            if(msg.startsWith("START")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                gamesize = Integer.parseInt(st.nextToken());
                if(gamesize != null) {
                    size.setText("Game size: " + gamesize.toString());
                    board = new BoardGUI(gamesize,1);
                    frame.add(board);
                    start.setVisible(false);
                    addbot.setVisible(false);
                    removebot.setVisible(false);
                }
                //TODO implement game start on client side
            } else if(msg.startsWith("Wrng")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                info = st.nextToken();
                if(info != null){
                    serverinfo.setText(info);
                }
            } else if(msg.startsWith("MOVE")){
                StringTokenizer st = new StringTokenizer(msg,";");
                st.nextToken();
                int startX = Integer.parseInt(st.nextToken());
                int startY = Integer.parseInt(st.nextToken());
                int goalX = Integer.parseInt(st.nextToken());
                int goalY = Integer.parseInt(st.nextToken());
                board.move(startX,startY,goalX,goalY);
                board.repaint();
            } else if(msg.startsWith("RESTART")){
                frame.remove(board);
                start.setVisible(true);
                addbot.setVisible(true);
                removebot.setVisible(true);
                serverinfo.setText("RESTART!");
                size.setText("Size  ");
            } else if(msg.startsWith("TURN")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                turn = st.nextToken();
                turn = turn + " turn  ";
                serverinfo.setText(turn);
            } else if(msg.startsWith("WIN")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                info = st.nextToken() + " Wins!";
                serverinfo.setText(info);
                frame.remove(board);
            } else if(msg.startsWith("COLOR")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                String color =  st.nextToken();
                castcolor(color);
                yourcolor.setText("Your Color: "+color.toString()+"  ");
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
     * Function send request to add bot in server;
     */
    public void sendaddbot(){
        send("BOT#ADD");
    }
    public void sendremovebot(){
        send("BOT#REMOVE");
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

    public static void main(String args[]) throws IOException {
        Client c = new Client();
    }

}
