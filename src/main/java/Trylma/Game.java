package Trylma;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Game {
    /*private*/ Board board;
    private ArrayList<ArrayList<Tuple>> paths;


    Game(int players, int sets) {
        board = new Board(players, sets);
    }

    boolean legalmove(int startX, int startY, int goalX, int goalY) {
        if (!board.board[goalX][goalY].getState().equals("EMPTY")) {
            return false;/*
            throw new RuntimeException("MoveNotAllowed: final field not empty!");*/
        } else if (abs(goalX-startX)>1 || abs(goalY-startY)>2) {
            return checkJump(startX, startY, goalX, goalY);
        }
        return true;
    }

    private boolean checkJump(int startX, int startY, int goalX, int goalY) {
        if (!board.board[startX + 1][startY + 1].getState().equals("EMPTY") && !board.board[startX + 1][startY + 1].getState().equals("BLOCKED")) {
            if (startX + 2 == goalX && startY + 2 == goalY) {
                return true;
            } else if (board.board[startX + 2][startY + 2].getState().equals("EMPTY")) {
                checkJump(startX + 2, startY + 2, goalX, goalY);
            }
        }
        if (!board.board[startX + 1][startY - 1].getState().equals("EMPTY") && !board.board[startX + 1][startY + 1].getState().equals("BLOCKED")) {
            if (startX + 2 == goalX && startY - 2 == goalY) {
                return true;
            } else if (board.board[startX + 2][startY - 2].getState().equals("EMPTY")) {
                checkJump(startX + 2, startY - 2, goalX, goalY);
            }
        }
        if (!board.board[startX][startY + 2].getState().equals("EMPTY") && !board.board[startX + 1][startY + 1].getState().equals("BLOCKED")) {
            if (startX == goalX && startY + 4 == goalY) {
                return true;
            } else if (board.board[startX][startY + 4].getState().equals("EMPTY")) {
                checkJump(startX, startY + 4, goalX, goalY);
            }
        }
        if (!board.board[startX][startY - 2].getState().equals("EMPTY") && !board.board[startX + 1][startY + 1].getState().equals("BLOCKED")) {
            if (startX == goalX && startY - 4 == goalY) {
                return true;
            } else if (board.board[startX][startY - 4].getState().equals("EMPTY")) {
                checkJump(startX, startY - 4, goalX, goalY);
            }
        }
        if (!board.board[startX - 1][startY + 1].getState().equals("EMPTY") && !board.board[startX + 1][startY + 1].getState().equals("BLOCKED")) {
            if (startX - 2 == goalX && startY + 2 == goalY) {
                return true;
            } else if (board.board[startX - 2][startY + 2].getState().equals("EMPTY")) {
                checkJump(startX - 2, startY + 2, goalX, goalY);
            }
        }
        if (!board.board[startX - 1][startY - 1].getState().equals("EMPTY") && !board.board[startX + 1][startY + 1].getState().equals("BLOCKED")) {
            if (startX - 2 == goalX && startY - 2 == goalY) {
                return true;
            } else if (board.board[startX - 2][startY - 2].getState().equals("EMPTY")) {
                checkJump(startX - 2, startY - 2, goalX, goalY);
            }
        }
        return false;
        //throw new RuntimeException("Move not possible!");
    }
}
