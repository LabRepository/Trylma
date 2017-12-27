package Trylma.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

// TODO ADD handling reset connection

public class Client {
    static DataInputStream dis;
    static DataOutputStream dos;
    final static int ServerPort = 12345;
    Scanner scn = new Scanner(System.in);
    InetAddress ip;
    Socket s;
    Thread sendMessage;
    Thread readMessage;

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

        // readMessage thread
        readMessage = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {
                        try {
                            s.close();
                            System.exit(1);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        readMessage.start();
    }

    /**
     * Function send "JOIN" (to game lobby) request to server
     *
     * @param lobbyid Lobby ID / Here we use only one lobby
     */
    public void joinlobby(int lobbyid) {
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
     */
    public void quitlobby() {
        try {
            dos.writeUTF("QUIT");
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function send "MOVE;xs;ys;xe;ye" request to server for move validation
     * x and y represent mathematical vector [x,y]
     *
     * @param xs x start point
     * @param ys y start point
     * @param xe x end point
     * @param ye y end point
     *
     */
    public void move(int xs, int ys, int xe, int ye) {
            int x = xe - xs;
            int y = ys - ye;
        if ((x == 1 && y == 1) || (x == 1 && y == -1) || (x == -1 && y == 1) ||
                (x == -1 && y == -1) || (x == 2 && y == 0) || (x == -2 && y == 0)) {
            try {
                dos.writeUTF("MOVE;" + xs + ";" + ys + ";" + xe + ";" + ye);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("InvalidMove");
            }
        } else throw new RuntimeException("InvalidMove");
    }

    /**
     * Function send "Start" (from game lobby) request to server
     *
     * @throws IOException
     */
    public void start(){

    }
    public static void main(String args[]) throws IOException {
        Client c = new Client();
    }

}
