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
    private ArrayList<Color> bot = new ArrayList<>();
    private int NoPlayers = 0;
    private int NoBots = 0;
    public Game game;
    private volatile boolean isrunning = false;

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
        if(NoPlayers <= 6 && NoBots < 5 ) {
            NoBots++;
        }
    }

    /**
     * Function remove bot from game
     */
    public void removebot(){
        if(NoBots>0){
            NoBots--;
        }
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
                addcolors(gamers);
                respond("TURN;"+turn.getFirst().toString());
                break;
            case 3:
                game = new Game(gamers,1);
                isrunning = true;
                respond("START;"+gamers);
                addcolors(gamers);
                respond("TURN;"+turn.getFirst().toString());
                break;
            case 4:
                game = new Game(gamers,1);
                isrunning = true;
                respond("START;"+gamers);
                addcolors(gamers);
                respond("TURN;"+turn.getFirst().toString());
                break;
            case 6:
                game = new Game(gamers,1);
                isrunning = true;
                respond("START;"+gamers);
                addcolors(gamers);
                respond("TURN;"+turn.getFirst().toString());
                break;
            default:
                respond("Wrng;NoPlayers");
        }

    }

    public void addcolors(int gamers){
        int tmp = 0;
        if(gamers == 2) {
            colorhelper(tmp++,Color.BLACKPAWN);
            colorhelper(tmp,Color.WHITEPAWN);
            turn.addLast(Color.BLACKPAWN);
            turn.addLast(Color.WHITEPAWN);
        } else if (gamers == 3) {
            colorhelper(tmp++,Color.YELLOWPAWN);
            colorhelper(tmp++,Color.WHITEPAWN);
            colorhelper(tmp,Color.REDPAWN);
            turn.addLast(Color.YELLOWPAWN);
            turn.addLast(Color.WHITEPAWN);
            turn.addLast(Color.REDPAWN);
        } else if(gamers == 4){
            colorhelper(tmp++,Color.YELLOWPAWN);
            colorhelper(tmp++,Color.GREENPAWN);
            colorhelper(tmp++,Color.REDPAWN);
            colorhelper(tmp,Color.BLUEPAWN);
            turn.addLast(Color.YELLOWPAWN);
            turn.addLast(Color.GREENPAWN);
            turn.addLast(Color.REDPAWN);
            turn.addLast(Color.BLUEPAWN);
        } else if(gamers == 6){
            colorhelper(tmp++,Color.YELLOWPAWN);
            colorhelper(tmp++,Color.GREENPAWN);
            colorhelper(tmp++,Color.REDPAWN);
            colorhelper(tmp++,Color.BLUEPAWN);
            colorhelper(tmp++,Color.BLACKPAWN);
            colorhelper(tmp,Color.WHITEPAWN);
            turn.addLast(Color.YELLOWPAWN);
            turn.addLast(Color.GREENPAWN);
            turn.addLast(Color.REDPAWN);
            turn.addLast(Color.BLUEPAWN);
            turn.addLast(Color.BLACKPAWN);
            turn.addLast(Color.WHITEPAWN);
        }

    }

    public void colorhelper(int tmp, Color c){
        System.out.print("1: "+tmp);
        if(tmp<players.size()) {
            players.get(tmp).setColor(c);
        } else {
            System.out.print("BOTTTTTTTTTTTTTT" +c.toString());
            bot.add(c);
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
            turn.clear();
        }
    }

    public boolean isturn(Color c){

        if(turn.getFirst() == c){
            return true;
        } return false;
    }

    public void moveturn(Color c){
        if(!isrunning)
        {
            return;
        }
        turn.remove(c);
        turn.addLast(c);
        c = turn.getFirst();
        respond("TURN;"+c.toString());
        if(bot.contains(c));
        {
            botmove(c);
        }
    }

    public void restart(){
        game = null;
        isrunning = false;
        turn.clear();
        bot.clear();
        NoBots = 0;
        respond("RESTART");
    }

    public boolean getstate(){
        return isrunning;
    }

    private void botmove(Color c){
        int[] result = game.moveBot(c.toString());
        respond("MOVE;"+result[0]+";"+result[1]+";"+result[2]+";"+result[3]);
        hasWinner();
        moveturn(c);
    }
}
