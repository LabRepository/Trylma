package Trylma.server;

/**
 * Player interface
 * @author Jakub Czyszczonik
 */
public interface AbstractPlayer {

    void send(String msg);
    void move(String received);
}
