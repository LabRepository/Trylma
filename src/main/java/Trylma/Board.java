package Trylma;
/**
 * Class initializing and holding basically all the information about state of the board.
 *
 * @author Michał Budnik
 */
class Board {
    private int height = 17;
    private int width = 25;

    Fields board[][] = new Fields[height][width];


    Board(int NoPlayers, int sets)
    {
        setupBoard();
        setupPlayers(NoPlayers, sets);
    }

    // Old way of setting up the board, pretty messy
     /*private void setupBoard(){
        for(int i = 0; i<width; i++){
            for (int j = 0; j<height; j++){
                if(((i+j)%2==0) && ((3<j && j<13)||(8<i && i<16))  &&
                        ((i!=9 && i!=15) || (j!=1 && j!= 15)) &&
                        ((i!=10 && i!=14) ||(j!=0 && j!= 16)) &&
                        ((j!=6 && j!=10) || (i!=0 && i!=24)) &&
                        ((j!=7 && j!=9) || (i!=1 && i!= 23)) &&
                        (j!= 8 || (i!=0 && i!=2 && i!=24 && i!=22))){
                    board[i][j] = new Fields("EMPTY");
                } else {
                    board[i][j] = new Fields("BLOCKED");
                }
            }
        }
    }*/

    private void setupBoard(){
        for (int i = 0; i<height; i++){
            for (int j = 0; j<width; j++){
                board[i][j] = new Fields("BLOCKED");
            }
        }
        constructTriangle(0, 13, 12, "EMPTY");
        constructReversedTriangle(16, 13, 12, "EMPTY");
    }

    private void constructTriangle(int start, int length, int mid, String state){
        for(int row = 0; row<length; row++) {
            constructLine(mid, row+start, row, state);
        }
    }
    private void constructReversedTriangle(int end, int length, int mid, String state){
        for(int row = length-1; row>=0; row--) {
            constructLine(mid, end-row, row, state);
        }
    }
    private void constructLine(int mid, int row, int i, String state){
        board[row][mid + i].setState(state);
        board[row][mid - i].setState(state);
        if (i-2>=0){constructLine(mid, row, i-2, state);}
    }

    private void setupPlayers(int NoPlayers, int sets){
        switch(NoPlayers){
            case 2:
                if (sets==1){initiateTwoColors();}
                else if (sets==2){initiateFourColors();}
                else if (sets==3){initiateSixColors();}
                else {throw new RuntimeException("numberOfSetsUnavailableForTWOPlayers");}
                break;
            case 3:
                if (sets==1){initiateThreeColors();}
                else if (sets==3){initiateSixColors();}
                else {throw new RuntimeException("numberOfSetsUnavailableForTHREEPlayers");}
                break;
            case 4:
                if (sets==1){initiateFourColors();}
                else {throw new RuntimeException("numberOfSetsUnavailableForFOURPlayers");}
                break;
            case 6:
                if (sets==1){initiateSixColors();}
                else {throw new RuntimeException("numberOfSetsUnavailableForFIVEPlayers");}
                break;
            default:
                throw new RuntimeException("numberOfPlayersIncorrect");
        }
    }


    private void initiateTwoColors(){
        constructTriangle(0, 4, 12,"BLACKPAWN");
        constructReversedTriangle(16, 4,12, "WHITEPAWN");
    }
    private void initiateThreeColors(){
        constructReversedTriangle(7, 4,3, "YELLOWPAWN");
        constructReversedTriangle(7, 4,21, "REDPAWN");
        constructReversedTriangle(16, 4,12, "WHITEPAWN");
    }
    private void initiateFourColors(){
        constructReversedTriangle(7, 4,3, "YELLOWPAWN");
        constructReversedTriangle(7, 4,21, "REDPAWN");
        constructTriangle(9,4, 3, "GREENPAWN");
        constructTriangle(9, 4,21, "BLUEPAWN");

    }
    private void initiateSixColors(){
        initiateFourColors();
        initiateTwoColors();
    }
    //old triangle constructing method (only for pawns)
    /*
    void triangleShape(int startingRow, int middleCol, String type) {
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                board[startingRow+i][middleCol - i].setState(type);
                board[startingRow+i][middleCol].setState(type);
                board[startingRow+i][middleCol + i].setState(type);
            } else {
                board[startingRow+i][middleCol - i].setState(type);
                board[startingRow+i][middleCol - i + 2].setState(type);
                board[startingRow+i][middleCol + i].setState(type);
                board[startingRow+i][middleCol + i - 2].setState(type);
            }
        }
    }
    void reversedTriangleShape(int endingRow, int middleCol, String type){
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                board[endingRow-i][middleCol - i].setState(type);
                board[endingRow-i][middleCol].setState(type);
                board[endingRow-i][middleCol + i].setState(type);
            } else {
                board[endingRow-i][middleCol - i].setState(type);
                board[endingRow-i][middleCol - i + 2].setState(type);
                board[endingRow-i][middleCol + i].setState(type);
                board[endingRow-i][middleCol + i - 2].setState(type);
            }
        }
    }*/
}
