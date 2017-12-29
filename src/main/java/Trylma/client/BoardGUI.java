package Trylma.client;


import java.awt.*;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

/**
 * This Class is Graphical representation of Game Board
 * @author Jakub Czyszczonik
 * @see javax.swing.JPanel
 */
public class BoardGUI extends JPanel {
    int height = 17;
    int width = 25;
    //Length 4 - 10 pawns, length 3 - 6 pawns, length 2 - 3 pawns
    private int lengthOfPawnFields = 4;
    int activeX = -1;
    int activeY = -1;

    public Color[][] board = new Color[width][height];

    BoardGUI(int NoPlayers, int sets) {
        setupBoard();
        setupPlayers(NoPlayers, sets);
        this.setBackground(Color.lightGray);
    }

    /**
     * Initialize fields as "BLOCKED", then call methods to appropriately open playable fields.
     */
    private void setupBoard() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = Color.lightGray;
            }
        }
        constructTriangle(0, 13, 12, Color.GRAY, 't');
        constructTriangle(16, 13, 12, Color.GRAY, 'r');
    }

    /**
     * Method constructing triangle on the map.
     *
     * @param top    Sets the row where triangle's top is.
     * @param length Sets height of the triangle.
     * @param mid    Sets the middle column of the triangle.
     * @param c     Sets the COLOR.
     * @param mode   Sets whether triangle should look like /\ or \/.
     */
    private void constructTriangle(int top, int length, int mid, Color c, char mode) {
        switch (mode) {
            case 't':
                for (int row = 0; row < length; row++) {
                    constructLine(mid, row + top, row, c);
                }
                break;

            case 'r':
                for (int row = length - 1; row >= 0; row--) {
                    constructLine(mid, top - row, row, c);
                }
                break;

            default:
                throw new RuntimeException("modeForTriangleBuildingNotSupported");
        }
    }

    /**
     * Method constructing one row of a triangle.
     * Recursive because of repeating shapes of triangle every other line.
     *
     * @param mid   Setting mid column of a triangle.
     * @param row   Setting currently working row on.
     * @param i     Current 'offset' from the middle row, repeats offsets from 2 lines before (because of repeating shapes).
     * @param c     Field Color
     */
    private void constructLine(int mid, int row, int i, Color c) {
        try {
            board[mid + i][row] = c;
            board[mid - i][row] = c;
        } catch (RuntimeException r) {
            r.getMessage();
        }
        if (i - 2 >= 0) {
            constructLine(mid, row, i - 2, c);
        }
    }

    /**
     * Based on number of players and sets they want to play with, initialises appropriate amount of pawn colors.
     * Throws exceptions if incorrect data provided.
     *
     * @param NoPlayers Number of players.
     * @param sets      Number of sets.
     */
    private void setupPlayers(int NoPlayers, int sets) {
        switch (NoPlayers) {
            case 2:
                if (sets == 1) {
                    initiateTwoColors();
                } else if (sets == 2) {
                    initiateFourColors();
                } else if (sets == 3) {
                    initiateSixColors();
                } else {
                    throw new RuntimeException("numberOfSetsUnavailableForTWOPlayers");
                }
                break;
            case 3:
                if (sets == 1) {
                    initiateThreeColors();
                } else if (sets == 3) {
                    initiateSixColors();
                } else {
                    throw new RuntimeException("numberOfSetsUnavailableForTHREEPlayers");
                }
                break;
            case 4:
                if (sets == 1) {
                    initiateFourColors();
                } else {
                    throw new RuntimeException("numberOfSetsUnavailableForFOURPlayers");
                }
                break;
            case 6:
                if (sets == 1) {
                    initiateSixColors();
                } else {
                    throw new RuntimeException("numberOfSetsUnavailableForSIXPlayers");
                }
                break;
            default:
                throw new RuntimeException("numberOfPlayersIncorrect");
        }
    }


    private void initiateTwoColors() {
        constructTriangle(0, lengthOfPawnFields, 12, Color.BLACK, 't');
        constructTriangle(16, lengthOfPawnFields, 12, Color.WHITE, 'r');
    }

    private void initiateThreeColors() {
        constructTriangle(7, lengthOfPawnFields, 3, Color.YELLOW, 'r');
        constructTriangle(7, lengthOfPawnFields, 21, Color.RED, 'r');
        constructTriangle(16, lengthOfPawnFields, 12, Color.WHITE, 'r');
    }

    private void initiateFourColors() {
        constructTriangle(7, lengthOfPawnFields, 3, Color.YELLOW, 'r');
        constructTriangle(7, lengthOfPawnFields, 21, Color.RED, 'r');
        constructTriangle(9, lengthOfPawnFields, 3, Color.GREEN, 't');
        constructTriangle(9, lengthOfPawnFields, 21, Color.BLUE, 't');

    }

    private void initiateSixColors() {
        initiateFourColors();
        initiateTwoColors();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    void doDrawing(Graphics g){
        width = (int) Math.min((getWidth() - 20) / (13 + 12 * 0.156), (getHeight() - 20) / 17);
        Graphics2D g2d = (Graphics2D) g;

        for (int y = 0; y < board[0].length; y++) {
            for (int x = 0; x < board.length; x++) {
                Color color = board[x][y];
                if (color.getRGB() != Color.LIGHT_GRAY.getRGB()) {
                    g2d.setPaint(color);
                    g2d.fillOval((int) (10 + x * width / 1.73), 10 + y * width, width, width);
                }
            }
        }

        if ((activeX != (-1)) && (activeY != (-1))) {
            Stroke oldStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(2));
                if (board[activeX][activeY].getRGB() == Color.BLACK.getRGB())
                g2d.setPaint(Color.YELLOW);
            else
                g2d.setPaint(Color.BLACK);
            g2d.draw(new Ellipse2D.Double((10 + activeX * width / 1.73), 10 + activeY * width, width, width));
            g2d.setStroke(oldStroke);
        }
    }

    public void move(int startX, int startY, int goalX, int golaY){
        board[goalX][golaY] = board [startX][startY];
        board[startX][startY] = Color.GRAY;
    }

}
