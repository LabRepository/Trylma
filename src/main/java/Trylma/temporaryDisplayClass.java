package Trylma;

import static java.lang.Thread.sleep;

public class temporaryDisplayClass {
    public static void main(String[] args) {
        try {
            Game game = new Game(2, 1);
            Board board = game.board;
            while (true){
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 25; j++) {
                    char sign;
                    switch (board.board[j][i].getState()) {
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
                //game.moveBot("BLACKPAWN");
                game.moveBot("WHITEPAWN");

                sleep(1000);
            }
        } catch (RuntimeException r) {
            System.out.print(r.getLocalizedMessage());
        } catch (InterruptedException c){}
    }
}
