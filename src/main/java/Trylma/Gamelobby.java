package Trylma;

import Trylma.server.Player;

import java.util.ArrayList;

public class Gamelobby {
    //TODO Implement construct, add fields nad methods, add legal move conditions. Builder pattern
    final private int gameid;
    private Board board;
    private ArrayList<Player> players;

    public Gamelobby(int gameid, Player first) {
        this.gameid = gameid;
        players = new ArrayList<Player>();
    }

    public void addplayer(Player player) {
        players.add(player);
    }

    public void exit(Player player) {
        players.remove(player);
    }

    public void rungame() {
        //TODO
    }

    public int getGameid() {
        return gameid;
    }

}
