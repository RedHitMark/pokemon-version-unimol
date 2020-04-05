package pokemon.logic.characters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import pokemon.logic.Attack;
import pokemon.logic.exceptions.AttackException;
import pokemon.logic.exceptions.PokemonException;
import pokemon.utils.Resources;

/**
 * The class <code>Player</code> manages the <code>Pokemon Player</code> of the game.
 * This class provides methods to turn, move and teach move to <code>Player</code>.
 * 
 * @author Marco Russodivito
 */
public class Player extends Pokemon {
    
    /**
     * Move or turn the <code>Player</code> <code>UP</code>.
     */
    public static final int UP = 0;
    /**
     * Move or turn the <code>Player</code> <code>DOWN</code>.
     */
    public static final int DOWN = 1;
    /**
     * Move or turn the <code>Player</code> <code>RIGHT</code>.
     */
    public static final int RIGHT = 2;
    /**
     * Move or turn the <code>Player</code> <code>LEFT</code>.
     */
    public static final int LEFT = 3;
    
    
    //images
    private static final BufferedImage[] FRONT_IMAGES = {Resources.getImage("/resources/Player/Fire/front.png"), Resources.getImage("/resources/Player/Water/front.png"), Resources.getImage("/resources/Player/Grass/front.png")};
    private static final BufferedImage[] REAR_IMAGES = {Resources.getImage("/resources/Player/Fire/rear.png"), Resources.getImage("/resources/Player/Water/rear.png"), Resources.getImage("/resources/Player/Grass/rear.png")};
    private static final BufferedImage[][] MAP_IMAGES = { 
        {Resources.getImage("/resources/Player/Fire/map - UP_0.png"), Resources.getImage("/resources/Player/Fire/map - DOWN_0.png"), Resources.getImage("/resources/Player/Fire/map - RIGHT_0.png"), Resources.getImage("/resources/Player/Fire/map - LEFT_0.png")},
        {Resources.getImage("/resources/Player/Water/map - UP_0.png"), Resources.getImage("/resources/Player/Water/map - DOWN_0.png"), Resources.getImage("/resources/Player/Water/map - RIGHT_0.png"), Resources.getImage("/resources/Player/Water/map - LEFT_0.png")},
        {Resources.getImage("/resources/Player/Grass/map - UP_0.png"), Resources.getImage("/resources/Player/Grass/map - DOWN_0.png"), Resources.getImage("/resources/Player/Grass/map - RIGHT_0.png"), Resources.getImage("/resources/Player/Grass/map - LEFT_0.png")}
    };
    private static final BufferedImage[][] MAP_MOVING_IMAGES = { 
        {Resources.getImage("/resources/Player/Fire/map - UP_1.png"), Resources.getImage("/resources/Player/Fire/map - DOWN_1.png"), Resources.getImage("/resources/Player/Fire/map - RIGHT_1.png"), Resources.getImage("/resources/Player/Fire/map - LEFT_1.png")},
        {Resources.getImage("/resources/Player/Water/map - UP_1.png"), Resources.getImage("/resources/Player/Water/map - DOWN_1.png"), Resources.getImage("/resources/Player/Water/map - RIGHT_1.png"), Resources.getImage("/resources/Player/Water/map - LEFT_1.png")},
        {Resources.getImage("/resources/Player/Grass/map - UP_1.png"), Resources.getImage("/resources/Player/Grass/map - DOWN_1.png"), Resources.getImage("/resources/Player/Grass/map - RIGHT_1.png"), Resources.getImage("/resources/Player/Grass/map - LEFT_1.png")}
    };
    

    /**
     * Throwing pen is a weak <code>Attack</code> that leech life from enemy. It is the base attack.
     */
    public static final String[] THROWING_PEN = {"Throwing Pen", "Lancia Pennarello"};
    private static final String[] THROWING_PEN_EFFECTS = {"throws the pen to the enemy and leech life from him", "Lancia il pennarello al nemico e gli ruba vita"};
    private static final int THROWING_PEN_POWER = 30;
    private static final int THROWING_PEN_TARGET = Attack.TARGET_OTHER_POKEMON;
    private static final int THROWING_PEN_HP_PERCENTAGE_RECOVED = 10;
            
    /**
     * Rest is a recover <code>Attack</code>. The player learns it after Piacentino battle.
     */
    public static final String[] REST = {"Rest", "Riposo"};
    private static final String[] REST_EFFECTS = {"rests and recoves hp", "risposa e recupera hp"};
    private static final int REST_POWER = 0;
    private static final int REST_TARGET = Attack.TARGET_SELF;
    private static final int REST_HP_PERCENTAGE_RECOVED = 40;
    
    /**
     * Fanduino is a good <code>Attack</code> of <code>Player</code>. The <code>Player</code> learns it after Palomba battle.
     */
    public static final String[] FANDUINO=  {"Fanduino", "Fanduino"};
    private static final String[] FANDUINO_EFFECTS = {"throws an explosive Fanduino to the enemy", "lancia un Fanduino esplosivo al nemico"};
    private static final int FANDUINO_POWER = 40;
    private static final int FANDUINO_TARGET = Attack.TARGET_OTHER_POKEMON;
    private static final int FANDUINO_HP_PERCENTAGE_RECOVED = 0;
    
    /**
     * Cazzimma is the most powerful <code>Attack</code> of <code>Player</code>. The <code>Player</code> learns it after oliveto battle.
     */
    public static final String[] CAZZIMMA = {"Cazzimma", "Cazzimma"};
    private static final String[] CAZZIMMA_EFFECTS = {"shows all his cazzimma to enemy", "mostra tutta la sua cazzima al nemico"};
    private static final int CAZZIMMA_POWER = 60;
    private static final int CAZZIMMA_TARGET = Attack.TARGET_OTHER_POKEMON;
    private static final int CAZZIMMA_HP_PERCENTAGE_RECOVED = 0;
    
    //position 
    private int x;
    private int y;
    private int lookAt;
    
    //images
    private BufferedImage frontImage;
    private BufferedImage rearImage;
    private BufferedImage mapImage;
    private BufferedImage mapMovingImage;
    
    //boss defeated
    private boolean piacentinoDefeated;
    private boolean palombaDefeated;
    private boolean olivetoDefeated;

    //id
    private int number;
    
    
    /**
     * Generates a new <code>Player</code> with name, type, level and coordinate requested.
     * 
     * @param pName the name of <code>Player</code>.
     * @param pType the type of <code>Player</code>.
     * @param pLevel the level of <code>Player</code>.
     * @param pX the X coordinate of <code>Player</code>.
     * @param pY the Y coordate of <code>Player</code>.
     * @throws PokemonException if parameters don't respect PRE-CONDITIONS.
     */
    public Player(String pName, int pType, int pLevel, int pX, int pY) throws PokemonException {
        super(pName, pType, pLevel);
        
        this.x = pX;
        this.y = pY;
        this.lookAt = Player.DOWN;
        
        this.frontImage = FRONT_IMAGES[this.type];
        this.rearImage = REAR_IMAGES[this.type];
        this.mapImage = MAP_IMAGES[this.type][this.lookAt];
        this.mapMovingImage = MAP_MOVING_IMAGES[this.type][this.lookAt];
        
        try {
            this.teachAttack(new Attack(Player.THROWING_PEN, Player.THROWING_PEN_EFFECTS, Player.THROWING_PEN_POWER, Player.THROWING_PEN_TARGET, Player.THROWING_PEN_HP_PERCENTAGE_RECOVED));
        } catch (AttackException ex) {
            //constant values
        }
        
        this.setPiacentinoDefeated(false);
        this.setPalombaDefeated(false);
        this.setOlivetoDefeated(false);
        
        this.number = (int) (Math.random()*2000)+158000; //casual number between 158000 and 160000
    }
    
    /**
     * Generates a <code>Player</code> from existent values.
     * 
     * @param pName the name of <code>Player</code>
     * @param pType the type of <code>Player</code>
     * @param pLevel the level of <code>Player</code>
     * @param pX the X coordinate of <code>Player</code>
     * @param pY the Y coordinate of <code>Player</code>
     * @param pExp the exp of <code>Player</code>
     * @param pHP the hp of <code>Player</code>
     * @param pPiacentinoDefeated <code>true</code> if <code>Player</code> defeated Piacentino, <code>false</code> otherwise.
     * @param pPalombaDefeated <code>true</code> if <code>Player</code> defeated Palomba, <code>false</code> otherwise.
     * @param pOlivetoDefeated <code>true</code> if <code>Player</code> defeated Oliveto, <code>false</code> otherwise.
     * @param pNumber the number of the <code>Player</code>'s Bedge.
     * @throws PokemonException if parameters don't respect PRE-CONDITIONS.
     */
    public Player(String pName, int pType, int pLevel, int pX, int pY, int pExp, int pHP, boolean pPiacentinoDefeated, boolean pPalombaDefeated, boolean pOlivetoDefeated, int pNumber) throws PokemonException {
        this(pName, pType, pLevel, pX, pY);
        
        if( pExp < 0 || pExp >= this.totalExpToLevelUp) 
            throw new PokemonException();
        
        if(pHP<= 0 || pHP>this.maxHP)
            throw new PokemonException();
            
            
        this.exp = pExp;
        this.hp = pHP;
        
        this.setPiacentinoDefeated(pPiacentinoDefeated);
        this.setPalombaDefeated(pPalombaDefeated);
        this.setOlivetoDefeated(pOlivetoDefeated);
        
        this.number = pNumber;
    }

    /**
     * Sets Piacentino defeated status.
     * 
     * @param pPiacentinoDefeated <code>true</code> if <code>Player</code> defeated Piacentino, <code>false</code> otherwise.
     */
    public void setPiacentinoDefeated(boolean pPiacentinoDefeated)  {
        try {
            
            if(pPiacentinoDefeated)
                teachAttack(new Attack(Player.REST, Player.REST_EFFECTS, Player.REST_POWER, Player.REST_TARGET, Player.REST_HP_PERCENTAGE_RECOVED));
            
            this.piacentinoDefeated = pPiacentinoDefeated;
            
        } catch (PokemonException | AttackException ex) {
            //constant values
        }
    }

    /**
     * Sets Palomba defeated status.
     * 
     * @param pPalombaDefeated <code>true</code> if <code>Player</code> defeated Palomba, <code>false</code> otherwise.
     */
    public void setPalombaDefeated(boolean pPalombaDefeated ){
        try {
            
            if(pPalombaDefeated)
                teachAttack(new Attack(Player.FANDUINO, Player.FANDUINO_EFFECTS, Player.FANDUINO_POWER, Player.FANDUINO_TARGET, Player.FANDUINO_HP_PERCENTAGE_RECOVED));
            
            this.palombaDefeated = pPalombaDefeated;
            
        } catch (PokemonException | AttackException ex) {
            //constant values
        }
    }

    /**
     * Sets Oliveto defeated status.
     * 
     * @param pOlivetoDefeated <code>true</code> if <code>Player</code> defeated Olieveto, <code>false</code> otherwise.
     */
    public void setOlivetoDefeated(boolean pOlivetoDefeated) {
        try {
            
            if(pOlivetoDefeated)
                teachAttack(new Attack(Player.CAZZIMMA, Player.CAZZIMMA_EFFECTS, Player.CAZZIMMA_POWER, Player.CAZZIMMA_TARGET, Player.CAZZIMMA_HP_PERCENTAGE_RECOVED));
            
            this.olivetoDefeated = pOlivetoDefeated;
            
        } catch (PokemonException | AttackException ex) {
            //constant values
        }
    }
    
    
    
    /**
     * Returns the X coordinate of <code>Player</code>.
     * 
     * @return the X coordinate of <code>Player</code>.
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Returns the Y coordinate of <code>Player</code>.
     * 
     * @return the Y coordinate of <code>Player</code>.
     */
    public int getY() {
        return this.y;
    }
    /**
     * Returns the direction in which <code>Player</code> is looking.
     * 
     * @return the direction in which <code>Player</code> is looking.
     */
    public int getLookAt() {
        return this.lookAt;
    }

    /**
     * Returns the status of Piacentino defeated.
     * 
     * @return <code>true</code> if <code>Player</code> defeated Piacentino, <code>false</code> otherwise.
     */
    public boolean isPiacentinoDefeated() {
        return this.piacentinoDefeated;
    }
    
    /**
     * Returns the status of Palomba defeated.
     * 
     * @return <code>true</code> if <code>Player</code> defeated Palomba, <code>false</code> otherwise.
     */
    public boolean isPalombaDefeated() {
        return this.palombaDefeated;
    }
    
    /**
     * Returns the status of Oliveto defeated.
     * 
     * @return <code>true</code> if <code>Player</code> defeated Oliveto, <code>false</code> otherwise.
     */
    public boolean isOlivetoDefeated() {
        return this.olivetoDefeated;
    }
    
    /**
     * Returns the number of <code>Player</code>'s Badge.
     * 
     * @return the number of <code>Player</code>'s Badge.
     */
    public int getNumber() {
        return this.number;
    }
    
    /**
     * Moves <code>Player</code> in direction in which he is watching.
     */
    public void move() {
        switch(this.lookAt) {
            case Player.UP:
                this.y--;
                break;

            case Player.DOWN:
                this.y++;
                break;

            case Player.RIGHT:
                this.x++;
                break;

            case Player.LEFT:
                this.x--;
                break;
        }   
    }
    
    /**
     * Turns the <code>Player</code> in the given direction.
     * 
     * @param pDirection the direction in which turn the <code>Player</code>. Should be <code>UP</code>, <code>DOWN</code>, <code>LEFT</code> or <code>RIGHT</code>
     */
    public void turnAt(int pDirection) {
        if( pDirection == UP || pDirection == DOWN || pDirection == LEFT || pDirection == RIGHT) {
            this.lookAt = pDirection;
            this.mapImage = Player.MAP_IMAGES[this.type][this.lookAt];
            this.mapMovingImage = Player.MAP_MOVING_IMAGES[this.type][this.lookAt];
        }
    }
    
    /**
     * Moves the <code>Player</code> at given X and Y coordinate.
     * 
     * @param pX the X coordinate in which move the <code>Player</code>
     * @param pY the Y coordinate in which move the <code>Player</code>
     */
    public void moveAt(int pX, int pY) {
        this.x = pX;
        this.y = pY;       
    }
    
    /**
     * Draws front image of <code>Player</code>.
     * 
     * @param pX the X coordinate of the upper-left corner of <code>Player</code>'s front image.
     * @param pY the Y coordinate of the upper-left corner of <code>Player</code>'s front image.
     * @param pWidth the width of <code>Player</code>'s front image.
     * @param pHeight the height of <code>Player</code>'s front image.
     * @param g the Graphics object.
     * @param obs the ImageObserver object. 
     */
    public void drawFront(int pX, int pY, int pWidth, int pHeight, Graphics g, ImageObserver obs) {
        g.drawImage(this.frontImage, pX, pY, pWidth, pHeight, obs);
    }
    /**
     * Draws rear image of <code>Player</code>.
     * 
     * @param pX the X coordinate of the upper-left corner of <code>Player</code>'s rear image.
     * @param pY the Y coordinate of the upper-left corner of <code>Player</code>'s rear image.
     * @param pWidth the width of <code>Player</code>'s rear image.
     * @param pHeight the height of <code>Player</code>'s rear image.
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void drawRear(int pX, int pY, int pWidth, int pHeight, Graphics g, ImageObserver obs) {
        g.drawImage(this.rearImage, pX, pY, pWidth, pHeight, obs);
    }
    /**
     * Draws map image of <code>Player</code>.
     * 
     * @param pX the X coordinate of the upper-left corner of <code>Player</code>'s map image.
     * @param pY the Y coordinate of the upper-left corner of <code>Player</code>'s map image.
     * @param pWidth the width of <code>Player</code>'s map image.
     * @param pHeight the height of <code>Player</code>'s map image.
     * @param pOnMoving <code>true</code> if player is moving, <code>false</code> otehrwise.
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void drawOnMap(int pX, int pY, int pWidth, int pHeight ,boolean pOnMoving, Graphics g, ImageObserver obs) {
        if(pOnMoving) {
            g.drawImage(this.mapMovingImage, pX, pY, pWidth, pHeight, obs);
        } else {
            g.drawImage(this.mapImage, pX, pY, pWidth, pHeight, obs);
        }    
    }
    
    
    @Override
    public String toString() {
        //used for save the player status
        return (this.name + "\r\n" + this.type + "\r\n" + this.level + "\r\n" + this.x + "\r\n" + this.y + "\r\n" + this.exp + "\r\n" + this.hp + "\r\n" + this.piacentinoDefeated + "\r\n" + this.palombaDefeated + "\r\n" + this.olivetoDefeated + "\r\n" + this.number);
    }
}
