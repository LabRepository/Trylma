package Trylma;

public class temporaryDisplayClass {
    public static void main(String[] args) {
        try {
            Board board = new Board(2, 1);
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 25; j++) {
                    char sign;
                    switch (board.board[i][j].getState()) {
                        case ("BLOCKED"):
                            sign = ' ';
                            break;
                        case ("BLACKPAWN"):
                            sign = 'B';
                            break;
                        case ("WHITEPAWN"):
                            sign = 'W';
                            break;
                        case ("YELLOWPAWN"):
                            sign = 'Y';
                            break;
                        case ("REDPAWN"):
                            sign = 'R';
                            break;
                        case ("GREENPAWN"):
                            sign = 'G';
                            break;
                        case ("BLUEPAWN"):
                            sign = 'b';
                            break;
                        default:
                            sign = '_';
                            break;
                    }
                    System.out.print(sign);
                }
                System.out.print("\n");
            }
        } catch (RuntimeException r) {
            System.out.print(r.getMessage());
        }
    }
}
