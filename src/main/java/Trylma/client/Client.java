package Trylma.client;

import Trylma.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
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
    java.awt.Color color;
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
     */
    private void inputhandler(){
        if(msg != null){
            if(msg.startsWith("START")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                gamesize = Integer.parseInt(st.nextToken());
                if(gamesize != null) {
                    startgame();
                }
            } else if(msg.startsWith("Wrng")){
                st = new StringTokenizer(msg,";");
                st.nextToken();
                info = st.nextToken();
                if(info != null){
                    serverinfo.setText(info);
                }
            } else if(msg.startsWith("MOVE")){
                    move(msg);
            } else if(msg.startsWith("RESTART")){
                    restart();
            } else if(msg.startsWith("TURN")){
                    turn(msg);
            } else if(msg.startsWith("WIN")){
                    win(msg);
            } else if(msg.startsWith("COLOR")){
                colormsg(msg);
            }

        }
    }

    /**
     * Function starts game in client
     */
    private void startgame(){
        size.setText("Game size: " + gamesize.toString());
        board = new BoardGUI(gamesize,1);
        frame.add(board);
        mouselistener();
        start.setVisible(false);
        addbot.setVisible(false);
        removebot.setVisible(false);
    }
    /**
     * Function move pawn in client
     */
    private void move(String msg){
        StringTokenizer st = new StringTokenizer(msg,";");
        st.nextToken();
        int startX = Integer.parseInt(st.nextToken());
        int startY = Integer.parseInt(st.nextToken());
        int goalX = Integer.parseInt(st.nextToken());
        int goalY = Integer.parseInt(st.nextToken());
        board.move(startX,startY,goalX,goalY);
        board.repaint();
    }
    /**
     * Function restarts game in client
     */
    private void restart(){
        frame.remove(board);
        start.setVisible(true);
        addbot.setVisible(true);
        removebot.setVisible(true);
        serverinfo.setText("RESTART!");
        size.setText("Size  ");
    }

    /**
     * Function set game turn
     */
    private void turn(String msg){
        st = new StringTokenizer(msg,";");
        st.nextToken();
        turn = st.nextToken();
        turn = turn + " turn  ";
        serverinfo.setText(turn);
    }
    /**
     * Function call after win
     */
    private void win(String msg){
        st = new StringTokenizer(msg,";");
        st.nextToken();
        info = st.nextToken() + " Wins!";
        serverinfo.setText(info);
        frame.remove(board);
        restart();
        JOptionPane.showMessageDialog(null, "Game ended!" + info, "WIN!", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Function give client specific color
     */
    private void colormsg(String msg){
        st = new StringTokenizer(msg,";");
        st.nextToken();
        String color =  st.nextToken();
        castcolor(color);
        yourcolor.setText("Your Color: "+color.toString()+"  ");
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
     * Function send add bot request to server;
     */
    public void sendaddbot(){
        send("BOTA");
    }

    /**
     * Function send remove bot request to server;
     */
    public void sendremovebot(){
        send("BOTR");
    }

    /**
     * This Function casts String to Trylma.Color
     * @param c String to cast
     * @see Trylma.Color
     */
    public void castcolor(String c){
        switch(c){
            case "BLACKPAWN":
                color = java.awt.Color.BLACK;
                break;
            case "WHITEPAWN":
                color = color = java.awt.Color.WHITE;;
                break;
            case "REDPAWN":
                color = color = java.awt.Color.RED;;
                break;
            case "BLUEPAWN":
                color = color = java.awt.Color.BLUE;;
                break;
            case "GREENPAWN":
                color = color = java.awt.Color.GREEN;
                break;
            case "YELLOWPAWN":
                color = color = java.awt.Color.YELLOW;;
                break;
            default:
                System.out.print("Wrong Color!");
                break;
        }
    }

    /**
     * Mouse Listener init
     */
    private void mouselistener(){
        board.addMouseListener(new MouseAdapter() {
            int clickCounter = 0;
            int startX, startY, goalX, goalY;
            boolean missClick = true;

            @Override
            public void mousePressed(MouseEvent e) {
                missClick = true;
                clickCounter++;
                int mX, mY;

                if (clickCounter == 1) {
                    mX = e.getX();
                    mY = e.getY();

                    for (int y = 0; y < board.board[0].length; y++) {
                        for (int x = 0; x < board.board.length; x++) {
                            java.awt.Color tmpcolor = board.board[x][y];
                            if (tmpcolor.getRGB() == color.getRGB()) {
                                if ((Math.hypot(
                                        (int) (10 + board.width / 2 + x * board.width / 1.73
                                                - mX),
                                        10 + board.width / 2 + y * board.width
                                                - mY) <= board.width / 2)) {
                                    board.activeX = x;
                                    board.activeY = y;
                                    startX = x;
                                    startY = y;
                                    board.repaint();
                                    missClick = false;
                                }
                            }
                        }
                    }

                    if (missClick) {
                        board.activeX = -1;
                        board.activeY = -1;
                        clickCounter--;
                        board.repaint();
                    }

                } else if (clickCounter == 2) {
                    mX = e.getX();
                    mY = e.getY();

                    for (int y = 0; y < board.board[0].length; y++) {
                        for (int x = 0; x < board.board.length; x++) {
                            java.awt.Color color = board.board[x][y];
                            if (color.getRGB() == java.awt.Color.GRAY.getRGB()) {
                                if ((Math.hypot(
                                        (int) (10 + board.width / 2 + x * board.width / 1.73
                                                - mX),
                                        10 + board.width / 2 + y * board.width
                                                - mY) <= board.width / 2)) {
                                    goalX = x;
                                    goalY = y;
                                    missClick = false;
                                }
                            }
                        }
                    }

                    board.activeX = -1;
                    board.activeY = -1;
                    clickCounter = 0;

                    if (!missClick) {
                        sendmove(startX,startY,goalX,goalY);

                    }
                    board.repaint();
                }
            }
        });
    }


    public static void main(String args[]) throws IOException {
        Client c = new Client();
    }

}
