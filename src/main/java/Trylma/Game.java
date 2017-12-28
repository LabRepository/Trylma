package Trylma;

import static java.lang.Math.abs;

public class Game {
    /*private*/ Board board;
    private boolean isMoveLegal = false;
    private int numberOfPawns = 10;
    private int[] win = new int[]{0, 0, 0, 0, 0, 0};
    private String[] winColour = new String[]{"BLACK", "RED", "BLUE", "WHITE", "GREEN", "YELLOW"};


    Game(int players, int sets) {
        board = new Board(players, sets);
    }

    void moving(int startX, int startY, int goalX, int goalY){
        if (legalMove(startX, startY, goalX, goalY)){
            board.board[goalX][goalY].setState(board.board[startX][startY].getState());
            board.board[startX][startY].setState("EMPTY");
            if (!board.board[goalX][goalY].getAtFinish()) {
                setWin(goalX, goalY);
            }
        } else {
            throw new RuntimeException("Move not legal!");
        }
    }

    String checkEnd(){
        for (int i = 0; i<6; i++) {
            if (win[i] == numberOfPawns) {
                win[i] = -1;
                return winColour[i];
            }
        }
            return "NONE";
    }

    private void setWin(int x, int y){
        switch (board.board[x][y].getState()) {
            case "BLACKPAWN":
                if (blackWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[0]+=1;
                }
                break;
            case "REDPAWN":
                if (redWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[1]+=1;
                }
            case "BLUEPAWN":
                if (blueWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[2]+=1;
                }
            case "WHITEPAWN":
                if (whiteWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[3]+=1;
                }
            case "GREENPAWN":
                if (greenWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[4]+=1;
                }
            case "YELLOWPAWN":
                if (yellowWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[5]+=1;
                }
        }
    }

    private boolean legalMove(int startX, int startY, int goalX, int goalY) {
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

    private boolean blueWinArea(int x, int y){
        return((x<5 && 3<y && y<8) || (x<7 && 3<y && y<6));
    }
    private boolean whiteWinArea(int x, int y){
        return(y<4);
    }
    private boolean greenWinArea(int x, int y){
        return((x>19 && 3<y && y<8) || (x>17 && 3<y && y<6));
    }
    private boolean yellowWinArea(int x, int y){
        return((x>19 && 8<y && y<13) || (x>17 && 10<y && y<13));
    }
    private boolean blackWinArea(int x, int y){
        return(y>12);
    }
    private boolean redWinArea(int x, int y){
        return((x<5 && 8<y && y<13) || (x<7 && 10<y && y<13));
    }
}
