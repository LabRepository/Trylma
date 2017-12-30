package Trylma;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Game {
    /*private*/ Board board;
    private int numberOfPawns = 10;
    private int[] win = new int[]{0, 0, 0, 0, 0, 0};
    private String[] winColour = new String[]{"BLACK", "RED", "BLUE", "WHITE", "GREEN", "YELLOW"};
    private ArrayList<int[]> checkedJumps = new ArrayList<>();

    Game(int players, int sets) {
        board = new Board(players, sets);
    }

    public void moveBot(String color) {
        int[] result = bot(color);
        moving(result[0], result[1], result[2], result[3]);
    }

    public void moving(int startX, int startY, int goalX, int goalY) {
        if (!board.board[startX][startY].getState().equals("EMPTY") && !board.board[startX][startY].getState().equals("BLOCKED")) {
            if (legalMove(startX, startY, goalX, goalY)) {
                board.board[goalX][goalY].setState(board.board[startX][startY].getState());
                board.board[startX][startY].setState("EMPTY");
                if (!board.board[goalX][goalY].getAtFinish()) {
                    setWin(goalX, goalY);
                }
            } else {
                throw new RuntimeException("Move not legal!");
            }
        } else {
            throw new RuntimeException("Trying to move a field which is not a pawn!");
        }
        checkedJumps = new ArrayList<>();
    }

    public String checkEnd() {
        for (int i = 0; i < 6; i++) {
            if (win[i] == numberOfPawns) {
                win[i] = -1;
                return winColour[i];
            }
        }
        return "NONE";
    }

    private void setWin(int x, int y) {
        switch (board.board[x][y].getState()) {
            case "BLACKPAWN":
                if (blackWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[0] += 1;
                }
                break;
            case "REDPAWN":
                if (redWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[1] += 1;
                }
            case "BLUEPAWN":
                if (blueWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[2] += 1;
                }
            case "WHITEPAWN":
                if (whiteWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[3] += 1;
                }
            case "GREENPAWN":
                if (greenWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[4] += 1;
                }
            case "YELLOWPAWN":
                if (yellowWinArea(x, y)) {
                    board.board[x][y].setAtFinish(true);
                    win[5] += 1;
                }
        }
    }

    private boolean isMoveLegal = false;
    boolean legalMove(int startX, int startY, int goalX, int goalY) {
        isMoveLegal = false;
        if (board.board[goalX][goalY].getState().equals("EMPTY")) {
            if (abs(goalX - startX) > 2 || abs(goalY - startY) > 1) {
                checkJump(startX, startY, goalX, goalY);
            } else  {
                isMoveLegal = true;
            }
        }
        return isMoveLegal;
    }

    private void checkJump(int startX, int startY, int goalX, int goalY) {
        if (!board.board[startX + 1][startY + 1].getState().equals("EMPTY") && !board.board[startX + 1][startY + 1].getState().equals("BLOCKED")) {
            if (startX + 2 == goalX && startY + 2 == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX + 2][startY + 2].getState().equals("EMPTY")) {
                if (!arrayInList(new int[]{startX, startY, startX + 2, startY + 2}, checkedJumps)){
                    checkedJumps.add(new int[]{startX, startY, startX + 2, startY + 2});
                    checkJump(startX + 2, startY + 2, goalX, goalY);
                }
            }
        }
        if (!board.board[startX + 1][startY - 1].getState().equals("EMPTY") && !board.board[startX + 1][startY - 1].getState().equals("BLOCKED")) {
            if (startX + 2 == goalX && startY - 2 == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX + 2][startY - 2].getState().equals("EMPTY")) {
                if (!arrayInList(new int[]{startX, startY, startX + 2, startY - 2}, checkedJumps)) {
                    checkedJumps.add(new int[]{startX, startY, startX + 2, startY - 2});
                    checkJump(startX + 2, startY - 2, goalX, goalY);
                }
            }
        }
        if (!board.board[startX + 2][startY].getState().equals("EMPTY") && !board.board[startX + 2][startY].getState().equals("BLOCKED")) {
            if (startX + 4 == goalX && startY == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX + 4][startY].getState().equals("EMPTY")) {
                if (!arrayInList(new int[]{startX, startY, startX + 4, startY}, checkedJumps)) {
                    checkedJumps.add(new int[]{startX, startY, startX + 4, startY});
                    checkJump(startX + 4, startY, goalX, goalY);
                }
            }
        }
        if (!board.board[startX - 2][startY].getState().equals("EMPTY") && !board.board[startX - 2][startY].getState().equals("BLOCKED")) {
            if (startX - 4 == goalX && startY == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX - 4][startY].getState().equals("EMPTY")) {
                if (!arrayInList(new int[]{startX, startY, startX - 4, startY}, checkedJumps)) {
                    checkedJumps.add(new int[]{startX, startY, startX - 4, startY});
                    checkJump(startX - 4, startY, goalX, goalY);
                }
            }
        }
        if (!board.board[startX - 1][startY + 1].getState().equals("EMPTY") && !board.board[startX - 1][startY + 1].getState().equals("BLOCKED")) {
            if (startX - 2 == goalX && startY + 2 == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX - 2][startY + 2].getState().equals("EMPTY")) {
                if (!arrayInList(new int[]{startX, startY, startX - 2, startY + 2}, checkedJumps)) {
                    checkedJumps.add(new int[]{startX, startY, startX - 2, startY + 2});
                    checkJump(startX - 2, startY + 2, goalX, goalY);
                }
            }
        }
        if (!board.board[startX - 1][startY - 1].getState().equals("EMPTY") && !board.board[startX - 1][startY - 1].getState().equals("BLOCKED")) {
            if (startX - 2 == goalX && startY - 2 == goalY) {
                isMoveLegal = true;
                return;
            } else if (board.board[startX - 2][startY - 2].getState().equals("EMPTY")) {
                if (!arrayInList(new int[]{startX, startY, startX - 2, startY - 2}, checkedJumps)) {
                    checkedJumps.add(new int[]{startX, startY, startX - 2, startY - 2});
                    checkJump(startX - 2, startY - 2, goalX, goalY);
                }
            }
        }
    }

    private boolean blueWinArea(int x, int y) {
        return ((x < 5 && 3 < y && y < 8) || (x < 7 && 3 < y && y < 6));
    }

    private boolean whiteWinArea(int x, int y) {
        return (y < 4);
    }

    private boolean greenWinArea(int x, int y) {
        return ((x > 19 && 3 < y && y < 8) || (x > 17 && 3 < y && y < 6));
    }

    private boolean yellowWinArea(int x, int y) {
        return ((x > 19 && 8 < y && y < 13) || (x > 17 && 10 < y && y < 13));
    }

    private boolean blackWinArea(int x, int y) {
        return (y > 12);
    }

    private boolean redWinArea(int x, int y) {
        return ((x < 5 && 8 < y && y < 13) || (x < 7 && 10 < y && y < 13));
    }

    private ArrayList<Tuple> possibleSingleJumps = new ArrayList<>();
    private ArrayList<Tuple> possibleLongJumps = new ArrayList<>();
    private ArrayList<IntTuple> tempJumpList = new ArrayList<>();
    int index = 0;

    private int[] bot(String pawnColor) {
        ArrayList<Tuple> movesInCorrectDirection = new ArrayList<>();
        possibleSingleJumps = new ArrayList<>();
        possibleLongJumps = new ArrayList<>();
        int[] resultVector = new int[]{0, 0, 0, 0};
        double bestDistance = 1000;
        IntTuple[] finishFields = new IntTuple[]{new IntTuple(12, 16), new IntTuple(0, 12), new IntTuple(0, 4),
                new IntTuple(12, 0), new IntTuple(24, 4), new IntTuple(24, 12)};
        String[] pawnColorIndex = new String[]{"BLACKPAWN", "REDPAWN", "BLUEPAWN", "WHITEPAWN", "GREENPAWN", "YELLOWPAWN"};

        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                index = 0;
                if (board.board[i][j].getState().equals(pawnColor)) {
                    possibleSingleJumps.add(0, new Tuple(new IntTuple(i, j), new ArrayList<IntTuple>()));
                    discoverSingleRoutes((IntTuple) possibleSingleJumps.get(0).x, 0);
                    possibleLongJumps.add(0, new Tuple(new IntTuple(i, j), new ArrayList<IntTuple>()));
                    discoverJumpRoutes(new IntTuple(i,j));
                    for (int k = 0; k < index; k++) {
                        discoverJumpRoutes(tempJumpList.get(k));
                    }
                    possibleLongJumps.get(0).y = tempJumpList;
                    tempJumpList = new ArrayList<>();
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            if (pawnColorIndex[i].equals(pawnColor)) {
                index = i;
                break;
            }
        }

        for (Tuple a: possibleLongJumps){
            IntTuple start = (IntTuple) a.x;
            double startDistance = sqrt(pow(finishFields[index].x - start.x, 2) + pow(finishFields[index].y - start.y, 2));
            for (IntTuple b: (ArrayList<IntTuple>)a.y) {
                double finishingDistance = sqrt(pow(finishFields[index].x - b.x, 2) + pow(finishFields[index].y - b.y, 2));
                if (finishingDistance<startDistance){
                    movesInCorrectDirection.add(new Tuple(start, b));
                }
            }
        }
        for (Tuple a: possibleSingleJumps){
            IntTuple start = (IntTuple) a.x;
            double startDistance = sqrt(pow(finishFields[index].x - start.x, 2) + pow(finishFields[index].y - start.y, 2));
            for (IntTuple b: (ArrayList<IntTuple>)a.y) {
                double finishingDistance = sqrt(pow(finishFields[index].x - b.x, 2) + pow(finishFields[index].y - b.y, 2));
                if (finishingDistance<startDistance){
                    movesInCorrectDirection.add(new Tuple(start, b));
                }
            }
        }
        Random rand = new Random();
        index = rand.nextInt(movesInCorrectDirection.size());
        IntTuple start = (IntTuple) movesInCorrectDirection.get(index).x;
        IntTuple finish = (IntTuple) movesInCorrectDirection.get(index).y;
        resultVector = new int[]{start.x, start.y, finish.x, finish.y};

        /*for (Tuple a : possibleLongJumps) {
            for (IntTuple b : (ArrayList<IntTuple>) a.y) {
                IntTuple start = (IntTuple) a.x;
                double temp = sqrt(pow(finishFields[index].x - b.x, 2)  + pow(finishFields[index].y - b.y,2));
                if (temp < bestDistance) {
                    bestDistance = temp;
                    resultVector = new int[]{start.x, start.y, b.x, b.y};
                }
            }
        }

        for (Tuple a : possibleSingleJumps) {
            for (IntTuple b : (ArrayList<IntTuple>) a.y) {
                IntTuple start = (IntTuple) a.x;
                double temp = sqrt(pow(finishFields[index].x - b.x, 2) + pow(finishFields[index].y - b.y, 2));
                /*System.out.print("..." + start.x + "->"+ b.x + "..." + start.y + "->" + b.y + "...\n"
                + temp + "<?" + bestDistance + "\n");
                if (temp < bestDistance) {
                    bestDistance = temp;
                    resultVector = new int[]{start.x, start.y, b.x, b.y};
                }
            }
        }*/




        return resultVector;
    }

    private void discoverJumpRoutes(IntTuple start) {
        if (start.x + 2 < board.width && start.y + 2 < board.height) {
            if (!board.board[start.x + 1][start.y + 1].getState().equals("EMPTY") &&
                    !board.board[start.x + 1][start.y + 1].getState().equals("BLOCKED") &&
                    board.board[start.x + 2][start.y + 2].getState().equals("EMPTY") &&
                    !tupleInList(new IntTuple(start.x + 2, start.y + 2), tempJumpList)) {
                tempJumpList.add(new IntTuple(start.x + 2, start.y + 2));
                index++;
            }
        }
        if (start.x + 4 < board.width) {
            if (!board.board[start.x + 2][start.y].getState().equals("EMPTY") &&
                    !board.board[start.x + 2][start.y].getState().equals("BLOCKED") &&
                    board.board[start.x + 4][start.y].getState().equals("EMPTY") &&
                    !tupleInList(new IntTuple(start.x + 4, start.y), tempJumpList)) {
                tempJumpList.add(new IntTuple(start.x + 4, start.y));
                index++;
            }
        }
        if (start.x + 2 < board.width && start.y - 2 >=0) {
            if (!board.board[start.x + 1][start.y - 1].getState().equals("EMPTY") &&
                    !board.board[start.x + 1][start.y - 1].getState().equals("BLOCKED") &&
                    board.board[start.x + 2][start.y - 2].getState().equals("EMPTY") &&
                    !tupleInList(new IntTuple(start.x + 2, start.y - 2), tempJumpList)) {
                tempJumpList.add(new IntTuple(start.x + 2, start.y - 2));
                index++;
            }
        }
        if (start.x - 2 >= 0 && start.y - 2 >= 0) {
            if (!board.board[start.x - 1][start.y - 1].getState().equals("EMPTY") &&
                    !board.board[start.x - 1][start.y - 1].getState().equals("BLOCKED") &&
                    board.board[start.x - 2][start.y - 2].getState().equals("EMPTY") &&
                    !tupleInList(new IntTuple(start.x - 2, start.y - 2), tempJumpList)) {
                tempJumpList.add(new IntTuple(start.x - 2, start.y - 2));
                index++;
            }
        }
        if (start.x -4 >= 0) {
            if (!board.board[start.x - 2][start.y].getState().equals("EMPTY") &&
                    !board.board[start.x - 2][start.y].getState().equals("BLOCKED") &&
                    board.board[start.x - 4][start.y].getState().equals("EMPTY") &&
                    !tupleInList(new IntTuple(start.x - 4, start.y), tempJumpList)) {
                tempJumpList.add(new IntTuple(start.x - 4, start.y));
                index++;
            }
        }
        if (start.x - 2 >= 0 && start.y + 2 < board.height) {
            if (!board.board[start.x - 1][start.y + 1].getState().equals("EMPTY") &&
                    !board.board[start.x - 1][start.y + 1].getState().equals("BLOCKED") &&
                    board.board[start.x - 2][start.y + 2].getState().equals("EMPTY") &&
                    !tupleInList(new IntTuple(start.x - 2, start.y + 2), tempJumpList)) {
                tempJumpList.add(new IntTuple(start.x - 2, start.y + 2));
                index++;
            }
        }
    }

    private void discoverSingleRoutes(IntTuple start, int index) {
        ArrayList<IntTuple> tempResults = new ArrayList<>();
        if (start.x + 1 < board.width && start.y + 1 < board.height) {
            if (board.board[start.x + 1][start.y + 1].getState().equals("EMPTY")) {
                tempResults.add(new IntTuple(start.x + 1, start.y + 1));
            }
        }
        if (start.x + 2 < board.width) {
            if (board.board[start.x + 2][start.y].getState().equals("EMPTY")) {
                tempResults.add(new IntTuple(start.x + 2, start.y));
            }
        }
        if (start.x + 1 < board.width && start.y - 1 >= 0) {
            if (board.board[start.x + 1][start.y - 1].getState().equals("EMPTY")) {
                tempResults.add(new IntTuple(start.x + 1, start.y - 1));
            }
        }
        if (start.x - 1 >= 0 && start.y -1 >= 0) {
            if (board.board[start.x - 1][start.y - 1].getState().equals("EMPTY")) {
                tempResults.add(new IntTuple(start.x - 1, start.y - 1));
            }
        }
        if (start.x -2 >= 0) {
            if (board.board[start.x - 2][start.y].getState().equals("EMPTY")) {
                tempResults.add(new IntTuple(start.x -2 , start.y));
            }
        }
        if (start.x - 1 >= 0 &&  start.y + 1 < board.height) {
            if (board.board[start.x - 1][start.y + 1].getState().equals("EMPTY")) {
                tempResults.add(new IntTuple(start.x - 1, start.y + 1));
            }
        }

        possibleSingleJumps.get(index).y = tempResults;
    }

    private boolean tupleInList(IntTuple a, ArrayList<IntTuple> b){
        boolean contains = false;

        for (IntTuple c: b){
            if (c.x == a.x && c.y == a.y){contains = true;}
        }

        return contains;
    }
    private boolean arrayInList(int[] a, ArrayList<int[]> b){
        boolean contains = false;

        for (int[] c: b){
            if (c[0] == a[0] && c[1] == a[1] && c[2] == a[2] && c[3] == a[3]){contains = true;}
        }

        return contains;
    }
}