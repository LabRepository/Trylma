package Trylma;

import Trylma.server.Player;

import java.util.ArrayList;

public class Gamelobby {
    //TODO QUEUE implementation and respond.
    final private int gameid = 0;
    private Board board;
    private ArrayList<Player> players = new ArrayList<>();
    private int NoPlayer = 0;
    public Game game;

    //TODO ADD game start and write tests
    public Gamelobby() {
    }

    public void addplayer(Player player) {
        if(NoPlayer < 6) {
            if(!players.contains(player)) {
                players.add(player);
                NoPlayer++;
            } else {
                throw new RuntimeException("Youareinlobby");
            }
        } else throw new RuntimeException("Toomanyplayers"); //TODO this doesn't work
    }

    public void addbot(){
        if(NoPlayer <= 6) {
   //    TODO     players.add(new Bot);
        } else throw new RuntimeException("Youcantaddbot");
    }

    public void exit(Player player) {
        players.remove(player);
        NoPlayer--;
    }

    public void rungame() {
        // TODO Rungame
    }

    public int check(){
        return players.size();
    }

    public int getGameid() {
        return gameid;
    }

}
