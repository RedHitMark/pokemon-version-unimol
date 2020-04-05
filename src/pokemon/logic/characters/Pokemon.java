package pokemon.logic.characters;

import pokemon.logic.exceptions.PokemonException;
import pokemon.logic.Attack;

/**
 * The class <code>Pokemon</code> manages the characters of the game.
 * Each <code>Pokemon</code> has:
 *      Name: the name of <code>Pokemon</code>.
 *      Type: the element of <code>Pokemon</code>.
 *      Level: the progress of <code>Pokemon</code>.
 *      EXP: the experience of <code>Pokemon</code> gained winning battels.
 *      HP: life of <code>Pokemon</code>.
 *      Stats: attack, defense and speed used during battles
 *      Attacks: to hit or heal it self or other <code>Pokemon</code>s.
 * 
 * @author Marco Russodivito
 */
public abstract class Pokemon {
    /**
     * Max chars of <code>Pokemon</code>'s name.
     */
    public static final int MAX_CHARS_NAME = 11;
    /**
     * Fire type of <code>Pokemon</code>. More damage against <code>TYPE_GRASS</code> but less against <code>TYPE_WATER</code>.
     */
    public static final int TYPE_FIRE = 0;
    /**
     * Water type of <code>Pokemon</code>. More damage against <code>TYPE_FIRE</code> but less against <code>TYPE_GRASS</code>.
     */
    public static final int TYPE_WATER = 1;
    /**
     * Grass type of <code>Pokemon</code>. More damage against <code>TYPE_WATER</code> but less against <code>TYPE_FIRE</code>. 
     */
    public static final int TYPE_GRASS = 2;
    /**
     * Min level of <code>Pokemon</code>.
     */
    public static final int MIN_LEVEL = 1;
    /**
     * Max level of <code>Pokemon</code>.
     */
    public static final int MAX_LEVEL = 100;
    /**
     * <code>Pokemon</code> is dead when hp reach this value.
     */
    public static final int DEAD_HP = 0;
    /**
     * Max numbers of <code>Attack</code>s.
     */
    public static final int MAX_ATTACKS = 4;

    
    //some default values
    private static final String DEFAUL_NAME = "??????";
    private static final int INIT_LEVEL = 1;
    private static final int INIT_EXP = 0;
    
    //base stats values
    private static final int LOW_HP = 25;
    private static final int LOW_ATK = 7;
    private static final int LOW_DEF = 10;
    private static final int LOW_SPEED = 5;
    
    private static final int MEDIUM_HP = 40;
    private static final int MEDIUM_ATK = 10;
    private static final int MEDIUM_DEF = 15;
    private static final int MEDIUM_SPEED = 10;
    
    private static final int HIGH_HP = 50;
    private static final int HIGH_ATK = 15;
    private static final int HIGH_DEF = 20;
    private static final int HIGH_SPEED = 15;
    
    /**
     * Name of <code>Pokemon</code> - PRE-CONDITION: up to <code>MAX_CHARS_NAME</code> chars.
     */
    protected String name;
    /**
     * Type of <code>Pokemon</code> - PRE-CONDITION: must be <code>TYPE_FIRE</code>, <code>TYPE_WATER</code> or <code>TYPE_GRASS</code>.
     */
    protected int type;
    /**
     * Level of <code>Pokemon</code> - PRE-CONDITION: must be between <code>MIN_LEVEL</code> and <code>MAX_LEVEL</code>.
     */
    protected int level;
    /**
     * Exp of <code>Pokemon</code> - PRE-CONDITION: must be between <code>INIT_EXP</code> and<code>totalExpToLevelUp</code>.
     */
    protected int exp;
    /**
     * Hp of <code>Pokemon</code> - PRE-CONDITION: must be beetween <code>DEAD_HP</code> and <code>maxHP</code>.
     */
    protected int hp;
    
    /**
     * Number of exp points to level up.
     */
    protected int totalExpToLevelUp;
    /**
     * Total hp points.
     */
    protected int maxHP;
    /**
     * Attack of <code>Pokemon</code>.
     */
    protected int attack;
    /**
     * Defense of <code>Pokemon</code>.
     */
    protected int defense;
    /**
     * Speed of <code>Pokemon</code>.
     */
    protected int speed;
    
    /**
     * <code>Attack</code>s of <code>Pokemon</code> - PRE-CONDITION: must be up to <code>MAX_ATTACKS</code>. 
     */
    protected Attack[] attacks;
    /**
     * Number of <code>Attack</code>s learned by <code>Pokemon</code>.
     */
    protected int numAttaks;
    
    /**
     * Generates a <code>Pokemon</code> with name, type and level requested.
     * 
     * @param pName the name of <code>Pokemon</code>
     * @param pType the type of <code>Pokemon</code>
     * @param pLevel the level of <code>Pokemon</code>
     * @throws PokemonException if parameters don't respect PRE-CONDITIONS.
     */
    public Pokemon(String pName, int pType, int pLevel) throws PokemonException {
        if( pName.length() >= Pokemon.MAX_CHARS_NAME )
            throw new PokemonException("Name too long");
            
        if( !(pType == Pokemon.TYPE_FIRE || pType == Pokemon.TYPE_WATER || pType == Pokemon.TYPE_GRASS) ) 
            throw new PokemonException("Invalid type");
         
        if( pLevel > Pokemon.MAX_LEVEL || pLevel < Pokemon.MIN_LEVEL) 
            throw new PokemonException("Invalid level");
        
        if(pName.length() == 0)
            pName = Pokemon.DEFAUL_NAME;
        
        this.name = pName;  
        this.type = pType;
        this.level = pLevel;
        
        //calcualte stats using a function
        this.calculateStats();
        
        //init exp and hp 
        this.exp = Pokemon.INIT_EXP;
        this.hp = this.maxHP;
        
        this.attacks = new Attack[Pokemon.MAX_ATTACKS];
        this.numAttaks = 0;
    }
    
    
    /**
     * Returns the name of this <code>Pokemon</code>.
     * 
     * @return the name of this <code>Pokemon</code>.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the type of this <code>Pokemon</code>.
     * 
     * @return the type of this <code>Pokemon</code>.
     */
    public int getType() {
        return this.type;
    }
    
    /**
     * Returns the level of this <code>Pokemon</code>.
     * 
     * @return the level of this <code>Pokemon</code>.
     */
    public int getLevel() {
        return this.level;
    }
    
    /**
     * Returns the exp of this <code>Pokemon</code>.
     * 
     * @return the exp of this <code>Pokemon</code>.
     */
    public int getExp() {
        return this.exp;
    }
    
    /**
     * Return the HP of this <code>Pokemon</code>.
     * 
     * @return the HP of this <code>Pokemon</code>.
     */
    public int getHp() {
        return this.hp;
    }
    
    /**
     * Returns the total exp to level up of this <code>Pokemon</code>. 
     * 
     * @return the total exp to level up of this <code>Pokemon</code>.
     */
    public int getTotalExpToLevelUp() {
        return this.totalExpToLevelUp;
    }
    
    /**
     * Returns the MaxHP of this <code>Pokemon</code>.
     * 
     * @return the MaxHP of this <code>Pokemon</code>.
     */
    public int getMaxHp() {
        return this.maxHP;
    }
    
    /**
     * Returns the attack of this <code>Pokemon</code>.
     * 
     * @return the attack of this <code>Pokemon</code>.
     */
    public int getAttack() {
        return this.attack;
    }
    
    /**
     * Returns the defense of this <code>Pokemon</code>.
     * 
     * @return the defense of this <code>Pokemon</code>.
     */
    public int getDefense() {
        return this.defense;
    }
    
    /**
     * Returns the speed of this <code>Pokemon</code>.
     * 
     * @return the speed of this <code>Pokemon</code>.
     */
    public int getSpeed() {
        return this.speed;
    }
    
    /**
     * Returns an array with the names of <code>Attack</code>s learned by this <code>Pokemon</code>.
     * 
     * @return the names of <code>Attack</code>s learned by this <code>Pokemon</code>.
     */
    public String[] getAttacksName() {
        String[] attackNames = new String[this.numAttaks];
        
        for (int i = 0; i < this.numAttaks; i++) {
            attackNames[i] = attacks[i].getName();
        }
        
        return attackNames;
    }
    
    /**
     * Returns the number of <code>Attack</code>s learned by this <code>Pokemon</code>.
     * 
     * @return the number of <code>Attack</code>s learned by this <code>Pokemon</code>.
     */
    public int getNumAttacks() {
        return this.numAttaks;
    }
    
    
    
    /**
     * Teachs pAttack to this <code>Pokemon</code>.
     * 
     * @param pAttack the attach to teach.
     * @throws PokemonException if this <code>Pokemon</code> have learned already 4 attacks.
     */
    public void teachAttack(Attack pAttack) throws PokemonException {
        if(this.numAttaks >= Pokemon.MAX_ATTACKS) 
            throw new PokemonException();
        
        this.attacks[this.numAttaks] = pAttack;
        this.numAttaks++;
    }
    
    /**
     * This <code>Pokemon</code> uses an <code>Attack</code>.
     * 
     * @param pIDAttack the id of the <code>Attack</code> to use.
     * @return the <code>Attack</code> to use
     */
    public Attack useAttack(int pIDAttack){
        return this.attacks[pIDAttack];
    }
    
    /**
     * This <code>Pokemon</code> receive a damage that decrese its life.
     * 
     * @param pDamage the damage received.
     */
    public void reciveDamage(int pDamage) {
        if ( (this.hp -= pDamage) <= 0) {
            this.hp = 0;
        }
    }
    
    
    /**
     * Restores all HP points of this <code>Pokemon</code> 
     */
    public void heal() {
        this.hp = this.maxHP;
    }
    
    /**
     * Restores pHP points this <code>Pokemon</code> 
     * 
     * @param pHP the hp points to restore.
     */
    public void heal(int pHP) {
        if( (this.hp += pHP) > this.maxHP ) {
            this.hp = this.maxHP;
        }
    }
    
    /**
     * This <code>Pokemon</code> gain pEXP points and levelUp if reach totalExpToLevelUp.
     * 
     * @param pEXP the exp points gained by this <code>Pokemon</code>.
     */
    public void gainEXP(int pEXP) {
        if( (this.exp += pEXP) >= this.totalExpToLevelUp) {
            levelUP();
        }    
    }
    
    @Override
    public String toString() {
        //used for testing
        return (this.name + "    HP:" + this.hp + "/" + this.maxHP +"      EXP:"+ + this.exp + "/" + this.totalExpToLevelUp);
    }
    
    /**
     * Calculates the stats of this <code>Pokemon</code>.
     */
    protected void calculateStats() {
        this.totalExpToLevelUp = 30 + this.level*10;
        
        switch(this.type) {
            //high attack and speed, low defense  -> sweeper
            case Pokemon.TYPE_FIRE:
                this.maxHP = Pokemon.MEDIUM_HP + this.level*7;
                this.attack = Pokemon.HIGH_ATK + this.level*3;
                this.defense = Pokemon.LOW_DEF + this.level*3;
                this.speed = Pokemon.HIGH_SPEED + this.level*5;
                break;
                
            //medium all -> freelance
            case Pokemon.TYPE_GRASS:
                this.maxHP = Pokemon.MEDIUM_HP + this.level*7;
                this.attack = Pokemon.MEDIUM_ATK + this.level*3;
                this.defense = Pokemon.MEDIUM_DEF + this.level*3;
                this.speed = Pokemon.MEDIUM_SPEED + this.level*5;
                break;
                
            //high hp, defense, medium attack, low defense and speed -> tank 
            case Pokemon.TYPE_WATER:
                this.maxHP = Pokemon.HIGH_HP + this.level*7;
                this.attack = Pokemon.MEDIUM_ATK + this.level*3;
                this.defense = Pokemon.HIGH_DEF + this.level*3;
                this.speed = Pokemon.LOW_SPEED + this.level*5;
                break;  
        }
    }
    
    private void levelUP() {
        if(this.level < 100) {
            this.level++;
            this.exp = 0;
            
            //recalcuate stats after levelup
            this.calculateStats();
            
            //recover hp after levelup
            this.heal();
        }    
    }
}
