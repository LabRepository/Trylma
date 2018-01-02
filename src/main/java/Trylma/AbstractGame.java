package Trylma;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public abstract class AbstractGame {
    public Board board;
    private int numberOfPawns = 10;
    private int[] win = new int[]{0, 0, 0, 0, 0, 0};
    private static String[] winColour = new String[]{"BLACKPAWN", "REDPAWN", "BLUEPAWN", "WHITEPAWN", "GREENPAWN", "YELLOWPAWN"};
    private ArrayList<int[]> checkedJumps = new ArrayList<>();

    AbstractGame(int players, int sets){
        board = new Board(players, sets);
    }

    public int[] moveBot(String color) {
        int[] result = bot(color);
        moving(result[0], result[1], result[2], result[3]);
        return result;
    }

    public void moving(int startX, int startY, int goalX, int goalY){
        if (!board.board[startX][startY].getState().equals("EMPTY") && !board.board[startX][startY].getState().equals("BLOCKED")) {
            if (legalMove(startX, startY, goalX, goalY)) {
                board.board[goalX][goalY] = board.board[startX][startY];
                board.board[startX][startY] = new Fields("EMPTY");
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


    String checkEnd() {
        for (int i = 0; i < 6; i++) {
            if (win[i] == numberOfPawns) {
                win[i] = -1;
                return winColour[i];
            }
        }
        return "NONE";
    }

    int colorIndex(int x, int y){
        for (int i = 0; i < 6; i++) {
            if (board.board[x][y].getState().equals(winColour[i])) {
                return i;
            }
        }
        return -1;
    }

    private void setWin(int x, int y) {
        if (inFinishArea(x, y, x, y)){
            board.board[x][y].setAtFinish(true);
            win[colorIndex(x, y)] += 1;
        }
    }

    private boolean isMoveLegal = false;

    boolean legalMove(int startX, int startY, int goalX, int goalY) {
        isMoveLegal = false;
        if (board.board[goalX][goalY].getState().equals("EMPTY")) {
            if (abs(goalX - startX) > 2 || abs(goalY - startY) > 1) {
                checkJump(startX, startY, goalX, goalY);
            } else {
                isMoveLegal = true;
            }
            if (board.board[startX][startY].getAtFinish()) {
                isMoveLegal = inFinishArea(startX, startY, goalX, goalY);
            }
        }
        return isMoveLegal;
    }

    private void checkJump(int startX, int startY, int goalX, int goalY) {
        if (startX + 2 < board.width && startY + 2 < board.height) {
            if (!board.board[startX + 1][startY + 1].getState().equals("EMPTY") && !board.board[startX + 1][startY + 1].getState().equals("BLOCKED")) {
                if (startX + 2 == goalX && startY + 2 == goalY) {
                    isMoveLegal = true;
                    return;
                } else if (board.board[startX + 2][startY + 2].getState().equals("EMPTY")) {
                    if (!arrayInList(new int[]{startX, startY, startX + 2, startY + 2}, checkedJumps)) {
                        checkedJumps.add(new int[]{startX, startY, startX + 2, startY + 2});
                        checkJump(startX + 2, startY + 2, goalX, goalY);
                    }
                }
            }
        }
        if (startX + 2 < board.width && startY - 2 >= 0) {
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
        }
        if (startX + 4 < board.width) {
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
        }
        if (startX - 4 >= 0) {
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
        }
        if (startX - 2 >= 0 && startY + 2 < board.height) {
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
        }
        if (startX - 2 >= 0 && startY - 2 >= 0) {
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
    }

    abstract boolean inFinishArea(int sx, int sy, int x, int y);

    private ArrayList<Tuple> possibleSingleJumps = new ArrayList<>();
    private ArrayList<Tuple> possibleLongJumps = new ArrayList<>();
    private ArrayList<IntTuple> tempJumpList = new ArrayList<>();
    IntTuple[] finishFields;
    int index = 0;

    void setFinishFields(){
        switch (board.width){
            case 25:
                if (numberOfPawns == 10){
                    finishFields = new IntTuple[]{new IntTuple(12, 16), new IntTuple(0, 12), new IntTuple(0, 4),
                            new IntTuple(12, 0), new IntTuple(24, 4), new IntTuple(24, 12)};
                }
        }
    }

    private int[] bot(String pawnColor) {
        ArrayList<Tuple> movesInCorrectDirection = new ArrayList<>();
        possibleSingleJumps = new ArrayList<>();
        possibleLongJumps = new ArrayList<>();
        int[] resultVector = new int[]{0, 0, 0, 0};

        setFinishFields();

        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                index = 0;
                if (board.board[i][j].getState().equals(pawnColor)) {
                    possibleSingleJumps.add(0, new Tuple(new IntTuple(i, j), new ArrayList<IntTuple>()));
                    discoverSingleRoutes((IntTuple) possibleSingleJumps.get(0).x, 0);
                    possibleLongJumps.add(0, new Tuple(new IntTuple(i, j), new ArrayList<IntTuple>()));
                    discoverJumpRoutes(new IntTuple(i, j));
                    for (int k = 0; k < index; k++) {
                        discoverJumpRoutes(tempJumpList.get(k));
                    }
                    possibleLongJumps.get(0).y = tempJumpList;
                    tempJumpList = new ArrayList<>();
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            if (AbstractGame.winColour[i].equals(pawnColor)) {
                index = i;
                break;
            }
        }

        for (Tuple a : possibleLongJumps) {
            IntTuple start = (IntTuple) a.x;
            double startDistance = sqrt(pow(finishFields[index].x - start.x, 2) + pow(finishFields[index].y - start.y, 2));
            for (IntTuple b : (ArrayList<IntTuple>) a.y) {
                double finishingDistance = sqrt(pow(finishFields[index].x - b.x, 2) + pow(finishFields[index].y - b.y, 2));
                if (finishingDistance <= startDistance) {
                    movesInCorrectDirection.add(new Tuple(start, b));
                }
            }
        }
        for (Tuple a : possibleSingleJumps) {
            IntTuple start = (IntTuple) a.x;
            double startDistance = sqrt(pow(finishFields[index].x - start.x, 2) + pow(finishFields[index].y - start.y, 2));
            for (IntTuple b : (ArrayList<IntTuple>) a.y) {
                double finishingDistance = sqrt(pow(finishFields[index].x - b.x, 2) + pow(finishFields[index].y - b.y, 2));
                if (finishingDistance <= startDistance) {
                    movesInCorrectDirection.add(new Tuple(start, b));
                }
            }
        }

        Random rand = new Random();
        do {
            if (movesInCorrectDirection.size() != 0) {
                index = rand.nextInt(movesInCorrectDirection.size());
                IntTuple start = (IntTuple) movesInCorrectDirection.get(index).x;
                IntTuple finish = (IntTuple) movesInCorrectDirection.get(index).y;
                resultVector = new int[]{start.x, start.y, finish.x, finish.y};
            } else {
                ArrayList<IntTuple> finishArray = new ArrayList<>();
                while (finishArray.size() == 0) {
                    index = rand.nextInt(possibleSingleJumps.size());
                    IntTuple start = (IntTuple) possibleSingleJumps.get(index).x;
                    finishArray = (ArrayList<IntTuple>) possibleSingleJumps.get(index).y;
                    if (finishArray.size() == 0){
                        index = rand.nextInt(possibleLongJumps.size());
                        start = (IntTuple) possibleLongJumps.get(index).x;
                        finishArray = (ArrayList<IntTuple>) possibleLongJumps.get(index).y;
                    }
                    if (finishArray.size() > 0) {
                        index = rand.nextInt(finishArray.size());
                        IntTuple finish = finishArray.get(index);
                        resultVector = new int[]{start.x, start.y, finish.x, finish.y};
                    }
                }
            }
        } while (board.board[resultVector[0]][resultVector[1]].getAtFinish() &&
                !inFinishArea(resultVector[0], resultVector[1], resultVector[2], resultVector[3]));

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
        if (start.x + 2 < board.width && start.y - 2 >= 0) {
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
        if (start.x - 4 >= 0) {
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
        if (start.x - 1 >= 0 && start.y - 1 >= 0) {
            if (board.board[start.x - 1][start.y - 1].getState().equals("EMPTY")) {
                tempResults.add(new IntTuple(start.x - 1, start.y - 1));
            }
        }
        if (start.x - 2 >= 0) {
            if (board.board[start.x - 2][start.y].getState().equals("EMPTY")) {
                tempResults.add(new IntTuple(start.x - 2, start.y));
            }
        }
        if (start.x - 1 >= 0 && start.y + 1 < board.height) {
            if (board.board[start.x - 1][start.y + 1].getState().equals("EMPTY")) {
                tempResults.add(new IntTuple(start.x - 1, start.y + 1));
            }
        }

        possibleSingleJumps.get(index).y = tempResults;
    }

    private boolean tupleInList(IntTuple a, ArrayList<IntTuple> b) {
        boolean contains = false;

        for (IntTuple c : b) {
            if (c.x == a.x && c.y == a.y) {
                contains = true;
            }
        }

        return contains;
    }

    private boolean arrayInList(int[] a, ArrayList<int[]> b) {
        boolean contains = false;

        for (int[] c : b) {
            if (c[0] == a[0] && c[1] == a[1] && c[2] == a[2] && c[3] == a[3]) {
                contains = true;
            }
        }

        return contains;
    }
}
