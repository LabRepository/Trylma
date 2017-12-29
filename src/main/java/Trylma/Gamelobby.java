package Trylma;

import Trylma.server.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Gamelobby {
    //TODO repair moving 9;3;13;3
    final private int gameid = 0;
    private Board board;
    public ArrayList<Player> players = new ArrayList<>();
    private LinkedList<Color> turn = new LinkedList<>();
    private int NoPlayers = 0;
    private int NoBots = 0;
    public Game game;
    private boolean isrunning = false;

    /**
     * Constructor
     */
    public Gamelobby() {}

    /**
     * Function to adding player to GameLobby
     * @param player player to add
     */
    public void addplayer(Player player) {
        if(!isrunning) {
            if (NoPlayers < 6) {
                if (!players.contains(player)) {
                    players.add(player);
                    NoPlayers++;
                    if (NoBots + NoPlayers > 6) {
                        removebot();
                    }
                } else {
                    throw new RuntimeException("Youareinlobby");
                }
            } else throw new RuntimeException("Toomanyplayers");
        } else {
            player.send("Game is Running!");
            try {
                player.s.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Function add bot to game
     */
    public void addbot(){
        if(NoPlayers <= 6) {
            //TODO Implement this method later
        } else throw new RuntimeException("Youcantaddbot");
    }

    /**
     * Function remove bot from game
     */
    public void removebot(){
        //TODO Implement this method later
    }

    public void exit(Player player) {
        if(isrunning) {
            players.remove(player);
            NoPlayers--;
            restart();
        } else {
            players.remove(player);
            NoPlayers--;
        }
    }

    /**
     * Function runs game, checks conditions and give players color
     */
    public void rungame() {
        if(isrunning){
            return;
        }
        int gamers = NoBots+NoPlayers;
        switch (gamers) {
            case 2:
                game = new Game(gamers,1);
                isrunning = true;
                respond("START;"+gamers);
                addcolors(2);
                respond("TURN;"+turn.getFirst().toString());
                break;
            case 3:
                game = new Game(gamers,1);
                isrunning = true;
                respond("START;"+gamers);
                addcolors(3);
                respond("TURN;"+turn.getFirst().toString());
                break;
            case 4:
                game = new Game(gamers,1);
                isrunning = true;
                respond("START;"+gamers);
                addcolors(4);
                respond("TURN;"+turn.getFirst().toString());
                break;
            case 6:
                game = new Game(gamers,1);
                isrunning = true;
                respond("START;"+gamers);
                addcolors(6);
                respond("TURN;"+turn.getFirst().toString());
                break;
            default:
                respond("Wrng;NoPlayers");
        }

    }

    public void addcolors(int gamers){
        if(gamers == 2) {
            players.get(0).setColor(Color.BLACKPAWN);
            players.get(1).setColor(Color.WHITEPAWN);
            turn.addLast(Color.BLACKPAWN);
            turn.addLast(Color.WHITEPAWN);
        } else if (gamers == 3) {
            players.get(0).setColor(Color.YELLOWPAWN);
            players.get(1).setColor(Color.WHITEPAWN);
            players.get(2).setColor(Color.REDPAWN);
            turn.addLast(Color.YELLOWPAWN);
            turn.addLast(Color.WHITEPAWN);
            turn.addLast(Color.REDPAWN);
        } else if(gamers == 4){
            players.get(0).setColor(Color.YELLOWPAWN);
            players.get(1).setColor(Color.GREENPAWN);
            players.get(2).setColor(Color.REDPAWN);
            players.get(3).setColor(Color.BLUEPAWN);
            turn.addLast(Color.YELLOWPAWN);
            turn.addLast(Color.GREENPAWN);
            turn.addLast(Color.REDPAWN);
            turn.addLast(Color.BLUEPAWN);
        } else if(gamers == 6){
            players.get(0).setColor(Color.YELLOWPAWN);
            players.get(1).setColor(Color.GREENPAWN);
            players.get(2).setColor(Color.REDPAWN);
            players.get(3).setColor(Color.BLUEPAWN);
            players.get(4).setColor(Color.BLACKPAWN);
            players.get(5).setColor(Color.WHITEPAWN);
            turn.addLast(Color.YELLOWPAWN);
            turn.addLast(Color.GREENPAWN);
            turn.addLast(Color.REDPAWN);
            turn.addLast(Color.BLUEPAWN);
            turn.addLast(Color.BLACKPAWN);
            turn.addLast(Color.WHITEPAWN);
        }

    }


    public void respond(String respond){
        for (Player p: players) {
            p.send(respond);
        }
    }

    public void hasWinner(){
        if(!game.checkEnd().equals("NONE")){
            respond("WIN;"+game.checkEnd());
            game = null;
            isrunning = false;
        }
    }

    public boolean isturn(Color c){
        if(turn.getFirst() == c){
            return true;
        } return false;
    }

    public void moveturn(Color c){
        turn.remove(c);
        turn.addLast(c);
        c = turn.getFirst();
        respond("TURN;"+c.toString());
    }

    public void restart(){
        game = null;
        isrunning = false;
        rungame();
        respond("RESTART");
    }

    public boolean getstate(){
        return isrunning;
    }
}
