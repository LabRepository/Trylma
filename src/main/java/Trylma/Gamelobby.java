package Trylma;

import Trylma.server.Player;

import java.util.ArrayList;

public class Gamelobby {
    //TODO Implement construct, add fields nad methods, add legal move conditions. Builder pattern
    final private int gameid;
    private Board board;
    private ArrayList<Player> players;
    private int NoPlayer = 0;
    private Game game;

    //TODO ADD game start and write tests
    public Gamelobby(int gameid, Player first) {
        this.gameid = gameid;
        players = new ArrayList<Player>();
        NoPlayer++;
    }

    public void addplayer(Player player) {
        if(NoPlayer < 6) {
            players.add(player);
            NoPlayer++;
        } else throw new RuntimeException("Toomanyplayers"); //TODO this doesn't work
    }

    public void addbot(){
        if(NoPlayer <= 6) {
   //    TODO     players.add(new Bot);
        } else throw new RuntimeException("Youcantaddbot");
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
