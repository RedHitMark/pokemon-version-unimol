package pokemon.logic;

import pokemon.logic.characters.Player;
import pokemon.logic.characters.Pokemon;
import pokemon.logic.characters.PokemonGenerator;
import pokemon.utils.SaveException;
import pokemon.utils.Save;


/**
 * The class <code>GameController</code> is contorller of game. 
 * This class provides methods to:
 *      Load and save game
 *      Keep player progress
 *      Keep map information
 *      Start battles
 * 
 * @author Marco Russodivito
 */
public class GameController {
    /**
     * It's a walkable cell. <code>Player</code> can walk but can't talk with this cell.
     */
    public static final int MAP_WALKABLE = 0;
    /**
     * It's a wall cell. <code>Player</code> can't walk and can't talk whit this cell.
     */
    public static final int MAP_WALL = 1;
    /**
     * It's Piacentino cell. <code>Player</code> can't walk but can talk with this cell.
     */
    public static final int MAP_PIACENTINO = 2;
    /**
     * It's Palomba cell. <code>Player</code> can't walk but can talk with this cell.
     */
    public static final int MAP_PALOMBA = 3;
    /**
     * It's Oliveto cell. <code>Player</code> can't walk but can talk with this cell.
     */
    public static final int MAP_OLIVETO = 4;
    /**
     * It's coffee machine cell. <code>Player</code> can't walk but can talk with this cell.
     */
    public static final int MAP_BREAK = 5;
    /**
     * <code>Player</code> can't talk with this cell.
     */
    public static final int NOT_TALK = 6;
    
    //the map is a grid
    private static final int[][] MAP_LEGEND = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,5,5,5,1,1,1,1,1,1,1,1,1,1,5,5,5,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,0,0,0,0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,4,1,1,0,0,0,1,0,0,0,0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    };
    
    //the player of the game
    private static Player player;
    
    /**
     * Start a new Game
     * 
     * @param pName the name of new <code>Player</code>.
     * @param pType the type of new <code>Player</code>.
     */
    public static void newGame(String pName, int pType) {
        player = PokemonGenerator.getNewPlayer(pName, pType);
    }
    
    /**
     * Load game from .sav file.
     * 
     * @throws SaveException if save is corrupted.
     */
    public static void loadGame() throws SaveException {
        player = Save.load();
    }
    
    /**
     * Returns the <code>Player</code>.
     * 
     * @return the <code>Player</code>.
     */
    public static Player getPlayer() {
        return player;
    }
    
    /**
     * Checks if <code>Player</code> can walk in given direction.
     * 
     * @param pDirection the direction to check.
     * @return <code>true</code> if <code>Player</code> can walk in given direction, <code>false</code> otherwise.
     */
    public static boolean canWalk(int pDirection) {
        switch(pDirection) {
            case Player.UP:
                return (GameController.MAP_LEGEND[player.getY() - 1][player.getX()] == 0);
                
            case Player.DOWN:
                return (GameController.MAP_LEGEND[player.getY() + 1][player.getX()] == 0);
                
            case Player.LEFT:
                return (GameController.MAP_LEGEND[player.getY()][player.getX() - 1] == 0);
                
            case Player.RIGHT:
                return (GameController.MAP_LEGEND[player.getY()][player.getX() + 1] == 0);
        }
        return false;
    }
    
    /**
     * Talks with the cell in the direction in which <code>Player</code> is looking.
     * 
     * @return the talking result.
     */
    public static int talkWith() {
        switch(player.getLookAt()) {
            case Player.UP:
                if( !(GameController.MAP_LEGEND[player.getY() - 1][player.getX()] == GameController.MAP_WALKABLE && GameController.MAP_LEGEND[player.getY() - 1][player.getX()] == GameController.MAP_WALL) ) {
                    return (GameController.MAP_LEGEND[player.getY() - 1][player.getX()]);
                }
                
            case Player.DOWN:
                if( !(GameController.MAP_LEGEND[player.getY() + 1][player.getX()] == GameController.MAP_WALKABLE && GameController.MAP_LEGEND[player.getY() + 1][player.getX()] == GameController.MAP_WALL) ) {
                    return (GameController.MAP_LEGEND[player.getY() + 1][player.getX()]);
                }
                
            case Player.LEFT:
                if( !(GameController.MAP_LEGEND[player.getY()][player.getX() - 1] == GameController.MAP_WALKABLE && GameController.MAP_LEGEND[player.getY()][player.getX() - 1] == GameController.MAP_WALL) ) {
                    return (GameController.MAP_LEGEND[player.getY()][player.getX() - 1]);
                }
                
            case Player.RIGHT:
                if( !(GameController.MAP_LEGEND[player.getY()][player.getX() + 1] == GameController.MAP_WALKABLE && GameController.MAP_LEGEND[player.getY()][player.getX() + 1] == GameController.MAP_WALL) ) {
                    return (GameController.MAP_LEGEND[player.getY()][player.getX() + 1]);
                }
        }
        return GameController.NOT_TALK;
    }
    
    /**
     * Starts a random battle against a random student.
     * 
     * @return the <code>BattleController</code> of random student battle.
     */
    public static BattleController randomBattle() {
        //random level and type according to player's level.
        int randomLevel = (int) Math.round(Math.random()*(player.getLevel() - 1) + 1);
        int randomType = (int) Math.round(Math.random()*2);
        
        Pokemon enemy = PokemonGenerator.getRandomEnemy(randomType, randomLevel);
        return new BattleController(player, enemy, false);
    }
    
    /**
     * Starts the battle against Piacenetino.
     * 
     * @return the <code>BattleController</code> of Piacentino battle.
     */
    public static BattleController startPiacentinoBattle() {
        Pokemon enemy = PokemonGenerator.getBossEnemy(PokemonGenerator.PIACENTINO);
        
        return new BattleController(player, enemy, true);
    }
    
    /**
     * Starts the battle against Palomba.
     * 
     * @return the <code>BattleController</code> of Palomba battle.
     */
    public static BattleController startPalombaBattle() {
        Pokemon enemy = PokemonGenerator.getBossEnemy(PokemonGenerator.PALOMBA);
        
        return new BattleController(player, enemy, true);
    }
    
    /**
     * Starts the battle against Oliveto.
     * 
     * @return the <code>BattleController</code> of Oliveto battle.
     */
    public static BattleController startsOlivetoBattle() {
        Pokemon enemy = PokemonGenerator.getBossEnemy(PokemonGenerator.OLIVETO);
        
        return new BattleController(player, enemy, true);
    }

    /**
     * Teleports <code>Player</code> at coffee machines and heal all his hp.
     */
    public static void teleportPlayerAtBreakPoint() {
        player.heal();
        player.moveAt(8, 2);
    }
}
