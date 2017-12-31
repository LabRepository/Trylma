package Trylma;

import Trylma.server.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Gamelobby {
    /**
     * Contain game id
     * variable for future develop
     */
    final private int gameid = 0;
    /**
     * Array list players in lobby.
     */
    public ArrayList<Player> players = new ArrayList<>();
    /**
     * Linked list contains game (turn) queue
     */
    private LinkedList<Color> turn = new LinkedList<>();
    /**
     * Contains color for bot.
     */
    private ArrayList<Color> bot = new ArrayList<>();
    /**
     * Players number
     */
    private int NoPlayers = 0;
    /**
     * Bot number
     */
    private int NoBots = 0;
    /**
     * Current game
     */
    public volatile Game game;
    /**
     * Game state
     */
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
                    sendBotNo();
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
            sendBotNo();
        }
    }

    /**
     * Function remove bot from game
     */
    public void removebot(){
        if(NoBots>0){
            NoBots--;
            sendBotNo();
        }
    }

    /**
     * For exit player from lobby
     * @param player
     */
    public void exit(Player player) {
        if(isrunning) {
            players.remove(player);
            NoPlayers--;
            NoBots = 0;
            bot.clear();
            restart();
        } else {
            players.remove(player);
            NoPlayers--;
        }
    }

    /**
     * Function runs game, checks conditions and give players color
     * Game Run uses Singleton Pattern
     */
    public void rungame() {
        if(game == null && isrunning){
            synchronized (game) {
                if(game == null) {
                    int gamers = NoBots + NoPlayers;
                    switch (gamers) {
                        case 2:
                            game = new Game(gamers, 1);
                            isrunning = true;
                            respond("START;" + gamers);
                            addcolors(gamers);
                            respond("TURN;" + turn.getFirst().toString());
                            break;
                        case 3:
                            game = new Game(gamers, 1);
                            isrunning = true;
                            respond("START;" + gamers);
                            addcolors(gamers);
                            respond("TURN;" + turn.getFirst().toString());
                            break;
                        case 4:
                            game = new Game(gamers, 1);
                            isrunning = true;
                            respond("START;" + gamers);
                            addcolors(gamers);
                            respond("TURN;" + turn.getFirst().toString());
                            break;
                        case 6:
                            game = new Game(gamers, 1);
                            isrunning = true;
                            respond("START;" + gamers);
                            addcolors(gamers);
                            respond("TURN;" + turn.getFirst().toString());
                            break;
                        default:
                            respond("Wrng;NoPlayers");
                    }
                }
            }
        }

    }

    /**
     * Function give color and add to queue
     * @param gamers number of players
     */
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

    /**
     * Function help in giving color
     * @param tmp player/bot number
     * @param c color
     */
    private void colorhelper(int tmp, Color c){
        if(tmp<players.size()) {
            players.get(tmp).setColor(c);
        } else {
            bot.add(c);
        }
    }

    /**
     * Function respond message to whole players in lobby
     * @param respond message
     */
    public void respond(String respond){
        for (Player p: players) {
            p.send(respond);
        }
    }

    /**
     * Function checking game winner
     */
    public void hasWinner(){
        String winner = game.checkEnd();
        if(!winner.equals("NONE")){
            respond("WIN;"+winner);
            game = null;
            isrunning = false;
            turn.clear();
            restart();
        }
    }

    /**
     * Checking turn for color
     * @param c color
     * @return boolean
     */
    public boolean isturn(Color c){

        if(turn.getFirst() == c) return true;
        return false;
    }

    /**
     * Handles moving queue
     * @param c Color
     */
    public void moveturn(Color c){
        if(!isrunning)
        {
            return;
        }
        turn.remove(c);
        turn.addLast(c);
        c = turn.getFirst();
        if(bot.contains(c)){
            botmove(c);
            if(turn.size() > 1) {
                c = turn.getFirst();
            }

        }
        respond("TURN;"+c.toString());
    }

    /**
     * End game and send restart
     */
    public void restart(){
        game = null;
        isrunning = false;
        turn.clear();
        bot.clear();
        NoBots = 0;
        sendBotNo();
        respond("RESTART");
    }

    /**
     * State of game
     * @return boolean
     */
    public boolean getstate(){
        return isrunning;
    }

    /**
     * Function handles bot moving
     * @param c Pawn color
     */
    private void botmove(Color c){
        int[] result = game.moveBot(c.toString());
        respond("MOVE;"+result[0]+";"+result[1]+";"+result[2]+";"+result[3]);
        hasWinner();
        moveturn(c);
    }

    /**
     * NoBots getter
     * @return int
     */
    public int getNoBots(){
        return NoBots;
    }

    public void sendBotNo(){
        respond("BOT;"+getNoBots());
    }
}
