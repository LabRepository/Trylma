package Trylma.server;

/**
 * Player interface
 * @author Jakub Czyszczonik
 */
public interface AbstractPlayer {

    public void send(String msg);
    public void move(String received);
}
