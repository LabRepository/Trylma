package Trylma;

import Trylma.server.Player;

import java.util.ArrayList;

public class Gamelobby {
    //TODO Implement construct, add fields nad methods, add legal move conditions. Builder pattern
    final private int gameid;
    private Board board;
    private ArrayList<Player> players;
    public int NoPlayer = 0;

    public Gamelobby(int gameid, Player first) {
        this.gameid = gameid;
        players = new ArrayList<Player>();
        NoPlayer++;
    }

    public boolean addplayer(Player player) {
        if(NoPlayer <= 6) {
            players.add(player);
            return true;
        } return false;
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
