package Trylma;

public class Game extends AbstractGame{
     Game(int players, int sets){
         super(players, sets);
     }
     boolean inFinishArea(int sx, int sy, int x, int y) {
        switch (board.board[sx][sy].getState()) {
            case "BLACKPAWN":
                return (y > 12);
            case "REDPAWN":
                return ((x < 5 && 8 < y && y < 13) || (x < 7 && 10 < y && y < 13));
            case "BLUEPAWN":
                return ((x < 5 && 3 < y && y < 8) || (x < 7 && 3 < y && y < 6));
            case "WHITEPAWN":
                return (y < 4);
            case "GREENPAWN":
                return ((x > 19 && 3 < y && y < 8) || (x > 17 && 3 < y && y < 6));
            case "YELLOWPAWN":
                return ((x > 19 && 8 < y && y < 13) || (x > 17 && 10 < y && y < 13));
        }
        return false;
    }
}