package Trylma;

import static java.lang.Thread.sleep;

public class temporaryDisplayClass {
    public static void main(String[] args) {
        try {
            Game game = new Game(6, 1);
            Board board = game.board;
            int indx = 0;
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

                String a = game.checkEnd();
                switch(a){
                    case "NONE":
                        break;
                    default:
                        System.out.print(a + " WON!");
                        return;
                }
                switch (indx){
                    case 0:
                        System.out.print("B");
                        game.moveBot("BLACKPAWN");
                        indx++;
                        break;
                    case 1:
                        System.out.print("R");
                        game.moveBot("REDPAWN");
                        indx++;
                        break;
                    case 2:
                        System.out.print("b");
                        game.moveBot("BLUEPAWN");
                        indx++;
                        break;
                    case 3:
                        System.out.print("W");
                        game.moveBot("WHITEPAWN");
                        indx++;
                        break;
                    case 4:
                        System.out.print("G");
                        game.moveBot("GREENPAWN");
                        indx++;
                        break;
                    case 5:
                        System.out.print("Y");
                        game.moveBot("YELLOWPAWN");
                        indx=0;
                        break;
                }
                sleep(1);
            }
        } catch (InterruptedException c){}
    }
}
