package Trylma;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Game {
    /*private*/ Board board;
    private boolean isMoveLegal = false;
    private int[] win = new int[]{0, 0, 0, 0, 0, 0};
    private String[] winColour = new String[]{"BLACK", "RED", "BLUE", "WHITE", "GREEN", "YELLOW"};


    Game(int players, int sets) {
        board = new Board(players, sets);
    }

    void moving(int startX, int startY, int goalX, int goalY){
        if (legalmove(startX, startY, goalX, goalY)){
            board.board[goalX][goalY].setState(board.board[startX][startY].getState());
            board.board[startX][startY].setState("EMPTY");
        } else {
            throw new RuntimeException("Move not legal!");
        }
    }

    void checkWin(){

    }

    boolean legalmove(int startX, int startY, int goalX, int goalY) {
        isMoveLegal = false;
        if (abs(goalX-startX)>2 || abs(goalY-startY)>1) {
            checkJump(startX, startY, goalX, goalY);
        } else if (board.board[goalX][goalY].getState().equals("EMPTY")) {
            isMoveLegal = true;
        }
        return isMoveLegal;
    }

    private void checkJump(int startX, int startY, int goalX, int goalY) {
        if (!board.board[startX + 1][startY + 1].getState().equals("EMPTY") && !board.board[startX + 1][startY + 1].getState().equals("BLOCKED")) {
            if (startX + 2 == goalX && startY + 2 == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX + 2][startY + 2].getState().equals("EMPTY")) {
                checkJump(startX + 2, startY + 2, goalX, goalY);
            }
        }
        if (!board.board[startX + 1][startY - 1].getState().equals("EMPTY") && !board.board[startX + 1][startY - 1].getState().equals("BLOCKED")) {
            if (startX + 2 == goalX && startY - 2 == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX + 2][startY - 2].getState().equals("EMPTY")) {
                checkJump(startX + 2, startY - 2, goalX, goalY);
            }
        }
        if (!board.board[startX + 2][startY].getState().equals("EMPTY") && !board.board[startX + 2][startY].getState().equals("BLOCKED")) {
            if (startX + 4 == goalX && startY == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX + 4][startY].getState().equals("EMPTY")) {
                checkJump(startX + 4, startY, goalX, goalY);
            }
        }
        if (!board.board[startX - 2][startY].getState().equals("EMPTY") && !board.board[startX - 2][startY].getState().equals("BLOCKED")) {
            if (startX - 4 == goalX && startY == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX - 4][startY].getState().equals("EMPTY")) {
                checkJump(startX - 4, startY, goalX, goalY);
            }
        }
        if (!board.board[startX - 1][startY + 1].getState().equals("EMPTY") && !board.board[startX - 1][startY + 1].getState().equals("BLOCKED")) {
            if (startX - 2 == goalX && startY + 2 == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX - 2][startY + 2].getState().equals("EMPTY")) {
                checkJump(startX - 2, startY + 2, goalX, goalY);
            }
        }
        if (!board.board[startX - 1][startY - 1].getState().equals("EMPTY") && !board.board[startX - 1][startY - 1].getState().equals("BLOCKED")) {
            if (startX - 2 == goalX && startY - 2 == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX - 2][startY - 2].getState().equals("EMPTY")) {
                checkJump(startX - 2, startY - 2, goalX, goalY);
            }
        }
        throw new RuntimeException("Move not legal!");
    }
}
