package Trylma.player;

import Trylma.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Basic player implementation
 *
 * @author Jakub Czyszczonik
 */
public class Player extends Thread implements AbstractPlayer {
    private int id;
    private Color color;
    private Socket socket;
    private ObjectInputStream input = null;
    private ObjectOutputStream output = null;

    public Player(int id, Socket socket) {
        this.socket = socket;
        this.id = id;
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeChars("Player " + id);
        } catch (IOException e) {
            System.out.println("Player died: " + e);
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
