package Trylma;

public class temporaryDisplayClass {
    public static void main(String[] args) {
        //try {
            Board board = new Board(2, 3);
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 25; j++) {
                    char sign;
                    if (board.board[i][j].getState().equals("BLOCKED")) {
                        sign = ' ';
                    } else if (board.board[i][j].getState().equals("BLACKPAWN")) {
                        sign = 'B';
                    } else if (board.board[i][j].getState().equals("WHITEPAWN")) {
                        sign = 'W';
                    } else if (board.board[i][j].getState().equals("YELLOWPAWN")) {
                        sign = 'Y';
                    } else if (board.board[i][j].getState().equals("REDPAWN")) {
                        sign = 'R';
                    } else if (board.board[i][j].getState().equals("GREENPAWN")) {
                        sign = 'G';
                    } else if (board.board[i][j].getState().equals("BLUEPAWN")) {
                        sign = 'b';
                    } else {
                        sign = '_';
                    }
                    System.out.print(sign);
                }
                System.out.print("\n");
            }
        /*} catch (RuntimeException r) {
            System.out.print(r.getMessage());
        }*/
    }
}
