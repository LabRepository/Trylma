package Trylma.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

// TODO ADD handling reset connection

public class TestClient {
    static DataInputStream dis;
    static DataOutputStream dos;
    final static int ServerPort = 12345;
    Scanner scn = new Scanner(System.in);
    InetAddress ip;
    Socket s;
    Thread sendMessage;
    Thread readMessage;

    TestClient(){
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
                    if(msg == "jo"){
                        msg ="lobby#join";
                    }
                    try {
                        // write on the output stream
                        dos.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
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

                        e.printStackTrace();
                    }
                }
            }
        });
        readMessage.start();


        // obtaining input and out streams


        // sendMessage thread
    }

    public static void main(String args[]) throws IOException {
        TestClient c = new TestClient();
    }

}
