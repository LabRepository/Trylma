package Trylma.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// WHEN YOU uncomment code you will have basic chat
// TODO ADD HANDLING MSG TO GAME and HANDLING MSG to Server
class ClientHandler implements Runnable {
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    private String name;

    // constructor
    public ClientHandler(Socket s, String name,
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
        while (true) {
            try {
                // receive the string
                received = dis.readUTF();
                System.out.println(name + ": " + received);

                if (received.equals("logout")) {
                    System.out.println(s + " disconnected (" + name + ")");
                    this.isloggedin = false;
                    this.s.close();
                    break;
                }

//                // break the string into message and recipient part
//                StringTokenizer st = new StringTokenizer(received, "#");
//                String command = st.nextToken();
//                String recipient = st.nextToken();

//                // search for the recipient in the connected devices list.
//                // ar is the vector storing client of active users
//                for (ClientHandler mc : Server.ar)
//                {
//                    // if the recipient is found, write on its
//                    // output stream
//                    if (mc.name.equals(recipient) && mc.isloggedin==true)
//                    {
//                        mc.dos.writeUTF(this.name+" : "+MsgToSend);
//                        break;
//                    }
//                }
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

    public String getName() {
        return name;
    }
}
