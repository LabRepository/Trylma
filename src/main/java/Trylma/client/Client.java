package Trylma.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    final private int port = 12345;
    String serverAddress = "localhost"; //TODO make serveradress by get func or other.
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.run();
    }

    private void run() throws Exception {

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());

        // Process all messages from server, according to the protocol.
        while (true) {
            out.writeChars("it Works!");
            System.out.println((String) in.readObject());
        }

    }

}
