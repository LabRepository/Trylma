package Trylma;

import Trylma.server.Player;

import java.io.IOException;
import java.util.ArrayList;

public class Gamelobby {
    //TODO QUEUE implementation and respond.
    final private int gameid = 0;
    private Board board;
    private ArrayList<Player> players = new ArrayList<>();
    private int NoPlayers = 0;
    private int NoBots = 0;
    public Game game;

    //TODO ADD game start and write tests
    public Gamelobby() {
    }

    public void addplayer(Player player) {
        if(NoPlayers < 6) {
            if(!players.contains(player)) {
                players.add(player);
                NoPlayers++;
                if(NoBots+NoPlayers > 6){
                    removebot();
                }
            } else {
                throw new RuntimeException("Youareinlobby");
            }
        } else throw new RuntimeException("Toomanyplayers");

    }

    //TODO Implement this method
    public void addbot(){
        if(NoPlayers <= 6) {
            //    TODO     players.add(new Bot);
        } else throw new RuntimeException("Youcantaddbot");
    }

    //TODO Implement this method
    public void removebot(){

            if (NoPlayers <= 6) {
                //    TODO     players.add(new Bot);
            } else throw new RuntimeException("Youcantaddbot");

    }


//    public void exit(Player player) {
//        players.remove(player);
//        NoPlayers--;
//    }

    //this is super basic version and i don't extend very much
    public void rungame() {
        switch (NoPlayers+NoBots) {
            case 2:
                game = new Game(NoPlayers,1);
                break;
            case 3:
                game = new Game(NoPlayers,1);
                break;
            case 4:
                game = new Game(NoPlayers,1);
                break;
            case 6:
                game = new Game(NoPlayers,1);
                break;
            default:
                throw new RuntimeException("numberOfPlayersIncorrect");
        }
        for (Player p: players) {
                p.setGame(game);
        }
        respond("START");
    }


//    public int getGameid() {
//        return gameid;
//    }

    private void respond(String respond){
        for (Player p: players) {
            p.send(respond);
        }
    }
}
