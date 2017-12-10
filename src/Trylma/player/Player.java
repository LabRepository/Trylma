package Trylma.player;

import Trylma.Color;

/**
 * Basic player implementation
 *
 * @author Jakub Czyszczonik
 */
public class Player implements AbstractPlayer {
    private String name;
    private Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
