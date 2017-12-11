package Trylma;

public class Pawn {
    // TODO implement point
    Color color;
    private int x;
    private int y;

    Pawn(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void move(int x, int y) {
        if ((x > 0)) {
            this.x++;
        } else {
            this.x--;
        }
        if ((y > 0)) {
            this.y++;
        } else {
            this.y--;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
