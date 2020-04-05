package pokemon.logic;

import pokemon.logic.characters.Enemy;
import pokemon.logic.characters.Player;
import pokemon.logic.characters.Pokemon;

/**
 * The class <code>BattleController</code> is the contorller of every battle (<i>Troncarelli</i>).
 * 
 * @author Marco Russodivito
 */
public class BattleController {
    /**
     * <code>Player</code>'s turn. 
     */
    public static final int PLAYER_TURN = 0;
    /**
     * <code>Enemy</code>'s turn. 
     */
    public static final int ENEMY_TURN = 1;
    
    /**
     * The <code>Attack</code> is normal.
     */
    public static final float NORMAL_EFFECTIVE = 1f;
    /**
     * The <code>Attack</code> is supereffective.
     */
    public static final float SUPER_EFFECTIVE = 1.2f;
    /**
     * The <code>Attack</code> is not very effective.
     */
    public static final float NOT_VERY_EFFECTIVE = 0.8f;
       
    
    
    //player
    private Pokemon player;
    //enemy
    private Pokemon enemy;
    //flag
    private boolean bossBattle;
    
    //turn
    private int turn;
    
    
    /**
     * Creates a new <code>BattleController</code>.
     * 
     * @param pPlayer the player
     * @param pEnemy the enemy
     * @param pBoss <code>true</code> if this is a boss battle, <code>false</code> otherwise.
     */
    public BattleController(Pokemon pPlayer, Pokemon pEnemy, boolean pBoss) {
        this.player = pPlayer;
        this.enemy = pEnemy;
        
        this.bossBattle = pBoss;
    }
    
    /**
     * Returns the <code>Player</code>.
     * 
     * @return the <code>Player</code>
     */
    public Player getPlayer() {
        if( this.player instanceof Player)
            return (Player) this.player;
        
        return null;
    }
    
    /**
     * Returns the <code>Enemy</code>.
     * 
     * @return the <code>Enemy</code>
     */
    public Enemy getEnemy() {
        if( this.enemy instanceof Enemy)
            return (Enemy) this.enemy;
        
        return null;
    }
    
    /**
     * Checks if this is a boss Battle
     * 
     * @return <code>true</code> if this is a boss battle, <code>false</code> otherwise.
     */
    public boolean isBossBattle() {
        return this.bossBattle;
    }
    
    /**
     * Returns the turn.
     * 
     * @return the turn.
     */
    public int getTurn() {
        return this.turn;
    }
    
    /**
     * Returns the active <code>Pokemon</code>
     * 
     * @return the active <code>Pokemon</code>
     */
    public Pokemon getActivePokemon() {
        return (this.turn == BattleController.PLAYER_TURN)? this.player : this.enemy;
    }
    
    /**
     * Returns the passive <code>Pokemon</code>
     * 
     * @return the passive <code>Pokemon</code>
     */
    public Pokemon getPassivePokemon() {
        return (this.turn == BattleController.PLAYER_TURN)? this.enemy : this.player;
    }
    
    /**
     * Returns the dead <code>Pokemon</code>
     * 
     * @return the dead <code>Pokemon</code> or null.
     */
    public Pokemon getDeadPokemon() {
        if(this.playerIsDead())
            return this.player;
        
        if(this.enemyIsDead())
            return this.enemy;
        
        return null;
    }
    
    /**
     * Check is <code>Player</code> is dead.
     * 
     * @return <code>true</code> if <code>Player</code> is dead, <code>false</code> otherwise.
     */
    public boolean playerIsDead() {
        return this.getPlayer().getHp() == 0;
    }
    
    /**
     * Check is <code>Enemy</code> is dead.
     * 
     * @return <code>true</code> if <code>Enemy</code> is dead, <code>false</code> otherwise.
     */
    public boolean enemyIsDead() {
        return this.getEnemy().getHp() == 0;
    }
    
    /**
     * Start the battle, decide who is the first to attack.
     */
    public void startBattle() {
        this.turn = (this.player.getSpeed() > this.enemy.getSpeed())? BattleController.PLAYER_TURN : BattleController.ENEMY_TURN;
    }
    
    /**
     * Switch the turn.
     */
    public void switchTurn() {
        this.turn = (this.turn == BattleController.PLAYER_TURN) ? BattleController.ENEMY_TURN : BattleController.PLAYER_TURN;
    }
    
    /**
     * Checks if <code>Player</code> can escape the battle.
     *      -<code>Player</code> can always escape if this is a boss battle
     *      -if <code>Player</code> speed is greater than <code>Enemy</code> 80%
     *      -if <code>Enemy</code> speed is greater or equals than <code>Player</code> 30%
     * 
     * @return <code>true</code> if <code>Player</code> can escape, <code>false</code> otherwise.
     */
    public boolean playerCanEscape() {
        if(this.isBossBattle()) 
            return true;
        
        if( (this.player.getSpeed() > this.enemy.getSpeed()) && ((Math.round(Math.random()*100)) > 20) ) //80% to escape
            return true;
        
        
        if( (this.player.getSpeed() <= this.enemy.getSpeed()) && ((Math.round(Math.random()*100)) > 70)) //30% to escape
            return true;
        
        return false;
    }
    
    /**
     * Calculates the type effectiveness of active vs passive <code>Pokemon</code>
     * 
     * @return <code>SUPER_EFFECTIVE</code>, <code>NOT_VERY_EFFECTIVE</code> or <code>NORMAL_EFFECTIVE</code>.
     */
    public float calculateTypeEffectiveness() {
        if ( this.getActivePokemon().getType() == Pokemon.TYPE_FIRE && this.getPassivePokemon().getType() == Pokemon.TYPE_GRASS) {
            return BattleController.SUPER_EFFECTIVE;
        }
        
        if ( this.getActivePokemon().getType() == Pokemon.TYPE_FIRE && this.getPassivePokemon().getType() == Pokemon.TYPE_WATER) {
            return BattleController.NOT_VERY_EFFECTIVE;
        }
        
        if ( this.getActivePokemon().getType() == Pokemon.TYPE_WATER && this.getPassivePokemon().getType() == Pokemon.TYPE_FIRE) {
            return BattleController.SUPER_EFFECTIVE;
        }
        
        if (this.getActivePokemon().getType() == Pokemon.TYPE_WATER && this.getPassivePokemon().getType() == Pokemon.TYPE_GRASS) {
            return BattleController.NOT_VERY_EFFECTIVE;
        }
        
        if (this.getActivePokemon().getType() == Pokemon.TYPE_GRASS && this.getPassivePokemon().getType() == Pokemon.TYPE_WATER) {
            return BattleController.SUPER_EFFECTIVE;
        }
        
        if (this.getActivePokemon().getType() == Pokemon.TYPE_GRASS && this.getPassivePokemon().getType() == Pokemon.TYPE_FIRE) {
            return BattleController.NOT_VERY_EFFECTIVE;
        }
            
        return BattleController.NORMAL_EFFECTIVE;
    }
    
    /**
     * Calculate the damage of <code>Attack</code>
     * 
     * @param pAttack the <code>Attack</code>
     * @return the damage
     */
    public int calculateDamage(Attack pAttack) {
        int damage = 0;
        
        float typeEffectiveness = calculateTypeEffectiveness();
        
        if(pAttack.getTarget() == Attack.TARGET_OTHER_POKEMON) {
            damage = (int) ( typeEffectiveness * ((getActivePokemon().getAttack()*pAttack.getPower()) / (getPassivePokemon().getDefense())) );
            return damage;
        }
        
        if(pAttack.getTarget() == Attack.TARGET_SELF){
            damage = (int) ( (getActivePokemon().getAttack()*pAttack.getPower()) / getActivePokemon().getDefense() );
            return damage;
        }
        
        return 0;
    }
    
    /**
     * Calcualte the hp to recove.
     * 
     * @param pAttack the <code>Attack</code>
     * @return the hp to recove
     */
    public int calculateHPRecoved(Attack pAttack) {
        int hp;
        
        hp = (int) ( (pAttack.getHPRecoved()*getActivePokemon().getMaxHp())/ 100);
        
        return hp;
    }
    
    /**
     * Calculates the exp gained by <code>Player</code> if <code>Player</code> wins battle.
     * 
     * @return the exp gained by <code>Player</code> if <code>Player</code> wins battle.
     */
    public int calculateExp() {
        return this.enemy.getLevel() * 23;
    }
    
    /**
     * End battle and, if player win and this is a boss battle player, could learn a new <code>Attack</code>.
     */
    public void endBattle() {
        if((this.bossBattle) && enemyIsDead()) {
            if( this.enemy.getName().equals("PIACENTINO") ) {
                GameController.getPlayer().setPiacentinoDefeated(true);
                return;
            }
            
            if (this.enemy.getName().equals("PALOMBA")) {
                GameController.getPlayer().setPalombaDefeated(true);
                return;
            }
            
            if (this.enemy.getName().equals("OLIVETO")) {
                GameController.getPlayer().setOlivetoDefeated(true);
            }    
        }
    }
}
