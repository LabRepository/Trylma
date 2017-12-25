package Trylma.server;

import Trylma.Color;

/**
 * Interface of Player
 *
 * @author Jakub Czyszczonik
 */
public interface AbstractPlayer {

    int getID();
    Color getColor();
    void setID(int ID);
    void setColor(Color c);
}
