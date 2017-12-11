package Trylma;

import Trylma.player.Player;

import java.util.ArrayList;

public class Game {
    //TODO Implement construct, add fields nad methods, add legal move conditions.
    final private int gameid;
    private Board board;
    private ArrayList<Player> players;

    public Game(int gameid) {
        this.gameid = gameid;
        players = new ArrayList<Player>();
    }

    public synchronized boolean legalmove() {
        return false;  //TODO
    }

    public void addplayer(Player player) {
        players.add(player);
    }

    public int getGameid() {
        return gameid;
    }

}
