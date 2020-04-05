package pokemon.logic.characters;

import java.awt.image.BufferedImage;
import pokemon.logic.Attack;
import pokemon.logic.exceptions.AttackException;
import pokemon.logic.exceptions.PokemonException;
import pokemon.utils.Resources;

/**
 * The class <code>PokemonGenerator</code> generates <code>Pokemon</code>s
 * 
 * @author Marco Russodivito
 */
public class PokemonGenerator {
    //___PLAYER___
    //inizial player values
    private static final int INITIAL_LEVEL = 5;
    private static final int INITIAL_X = 21;
    private static final int INITIAL_Y = 10;
    

    //___RANDOM ENEMIES___
    /**
     * CARMINE is fire random <code>Enemy</code>.
     */
    public static final String CARMINE_NAME = "CARMINE";
    private static final BufferedImage CARMINE_BATTLE_IMAGE = Resources.getImage("/resources/Enemy/CARMINE.png");
    private static final String[][] CARMINE_ATTACKS_NAMES = {
        {"Curse", "Imprecazione"},
        {"Fortnite's match", "Partita a Fortnite"},
        {"Will to Live", "Voglia di vivere"}
    };
    private static final String[][] CARMINE_ATTACKS_EFFECTS = {
        {"curses everybody and everything.", "impreca tutto e tutti."},
        {"'s computer is burning!", "ha il computer in fiamme!"},
        {"is looking for a way to die.", "cerca un modo per morire."},
    };
    private static final int[] CARMINE_ATTACKS_POWER = { 50, 20, 30  };
    private static final int[] CARMINE_ATTACKS_TARGET = { Attack.TARGET_OTHER_POKEMON, Attack.TARGET_SELF, Attack.TARGET_SELF };
    private static final int[] CARMINE_ATTACKS_HP_PERCENTAGE_RECOVED = { 0, 0, 0 };
    
    /**
     * GIOVANNI is water random <code>Enemy</code>.
     */
    public static final String GIOVANNI_NAME = "GIOVANNI";
    private static final BufferedImage GIOVANNI_BATTLE_IMAGE = Resources.getImage("/resources/Enemy/GIOVANNI.png");
    private static final String[][] GIOVANNI_ATTACKS_NAMES = {
        {"Scrounging Coffee", "Caffe a Scrocco"},
        {"Sed Joke", "Battuta Triste"},
        {"Beer", "Birra"}
    };
    private static final String[][] GIOVANNI_ATTACKS_EFFECTS = {
        {"is scrounging coffe at the coffee machines.", "sta scroccando caffe alle macchinette."},
        {"says <I asked my French friend if he likes video games and she said Wii> but no one laughs", "dice <Mai fidarsi del fieno. Sono tutte balle> ma nessuno ride."},
        {"drinks beer and says <FORST in unity!>", "beve birra e dice <L'unione fa la FORST!>"},
    };
    private static final int[] GIOVANNI_ATTACKS_POWER = { 0, 30, 0  };
    private static final int[] GIOVANNI_ATTACKS_TARGET = { Attack.TARGET_OTHER_POKEMON, Attack.TARGET_OTHER_POKEMON, Attack.TARGET_OTHER_POKEMON };
    private static final int[] GIOVANNI_ATTACKS_HP_PERCENTAGE_RECOVED = { 25, 0, 25 };
    
    /**
     * PEPPE is grass random <code>Enemy</code>.
     */
    public static final String PEPPE_NAME = "PEPPE";
    private static final BufferedImage PEPPE_BATTLE_IMAGE = Resources.getImage("/resources/Enemy/PEPPE.png");
    private static final String[][] PEPPE_ATTACKS_NAMES = {
        {"Happy Birthday Peppe!", "Auguri Peppe!"},
        {"Offers Peppe!", "Offre Peppe!"},
        {"Who is Peppe?", "Chi è Peppe?"}
    };
    private static final String[][] PEPPE_ATTACKS_EFFECTS = {
        {"is celebreted by everyone.", "è festeggiato da tutti."},
        {"did't show up at coffee machines.", "non si è presentato alla macchinetta."},
        {"doesn't answer.", "non risponde."}
    };
    private static final int[] PEPPE_ATTACKS_POWER = { 0, 0, 0  };
    private static final int[] PEPPE_ATTACKS_TARGET = { Attack.TARGET_OTHER_POKEMON, Attack.TARGET_OTHER_POKEMON, Attack.TARGET_OTHER_POKEMON };
    private static final int[] PEPPE_ATTACKS_HP_PERCENTAGE_RECOVED = { 50, 0, 0 };
    
    //___BOSS___
    /**
     * PIACENTINO is water <code>Enemy</code> boss.
     */
    public static final int PIACENTINO = 0;
    private static final String PIACENTINO_NAME = "PIACENTINO";
    public static final int PIACENTINO_LEVEL = 7;
    private static final BufferedImage PIACENTINO_BATTLE_IMAGE = Resources.getImage("/resources/Enemy/PIACENTINO.png");
    private static final String[][] PIACENTINO_ATTACKS_NAMES = {
        {"Rest", "Risposo"},
        {"Whistle", "Fischio"},
        {"Late", "Ritardo"}
    };
    private static final String[][] PIACENTINO_ATTACKS_EFFECTS = {
        {"sleeps and recoves life.", "dorme e recupera vita."},
        {"whistles during the lesson.", "fischia durante la lezione."},
        {"starts lesson 30 minutes later because he was sleeping.", "inizia la lezione con 30 minuti di ritardo perchè stava dormendo."},
    };
    private static final int[] PIACENTINO_ATTACKS_POWER = { 0, 30, 0 };
    private static final int[] PIACENTINO_ATTACKS_TARGET = { Attack.TARGET_SELF, Attack.TARGET_OTHER_POKEMON,  Attack.TARGET_SELF };
    private static final int[] PIACENTINO_ATTACKS_HP_PERCENTAGE_RECOVED = { 40, 0, 10 };
    
    /**
     * PALOMBA is grass <code>Enemy</code> boss.
     */
    public static final int PALOMBA = 1;
    private static final String PALOMBA_NAME = "PALOMBA";
    public static final int PALOMBA_LEVEL = 10;
    private static final BufferedImage PALOMBA_BATTLE_IMAGE = Resources.getImage("/resources/Enemy/PALOMBA.png");
    private static final String[][] PALOMBA_ATTACKS_NAMES = {
        {"Fanduino", "Fanduino"},
        {"Semaphore", "Semaforo"},
        {"Assemlby", "Assembly"}
    };
    private static final String[][] PALOMBA_ATTACKS_EFFECTS = {
        {"uses a Fanduino that doesn't work.", "usa un Fanduino che non funziona."},
        {"is so confused to hit himseft.", "è cosi confuso da colpirsi da solo."},
        {"explains Assembly but no one understand.", "speiga Assembly ma nessuno capisce."},
    };
    private static final int[] PALOMBA_ATTACKS_POWER = { 0, 20, 50 };
    private static final int[] PALOMBA_ATTACKS_TARGET = { Attack.TARGET_OTHER_POKEMON,  Attack.TARGET_SELF,  Attack.TARGET_OTHER_POKEMON };
    private static final int[] PALOMBA_ATTACKS_HP_PERCENTAGE_RECOVED = { 0, 0, 0 };
    /**
     * OLIVETO is fire <code>Enemy</code> boss.
     */
    public static final int OLIVETO = 2;
    private static final String OLIVETO_NAME = "OLIVETO";
    public static final int OLIVETO_LEVEL = 15;
    private static final BufferedImage OLIVETO_BATTLE_IMAGE = Resources.getImage("/resources/Enemy/OLIVETO.png");
    private static final String[][] OLIVETO_ATTACKS_NAMES = {
        {"Cazzimma", "Cazzimma"},
        {"Naivee method", "Metodo naivee"},
        {"Code appezzotation", "Codice appezzottato"},
    };
    private static final String[][] OLIVETO_ATTACKS_EFFECTS = {
        {"shows whole his cazzima to the studens.", "Mostra tutta la sua cazzimma allo studente."},
        {"recove life coding frijen' manjann.", "recupera vita programmando frijen' manjann."},
        {"is the best developer of Unimol."}, {" è il miglior programmatore dell'Unimol."}
    };
    private static final int[] OLIVETO_ATTACKS_POWER = { 60, 0, 40 };
    private static final int[] OLIVETO_ATTACKS_TARGET = { Attack.TARGET_OTHER_POKEMON,  Attack.TARGET_SELF,  Attack.TARGET_OTHER_POKEMON };
    private static final int[] OLIVETO_ATTACKS_HP_PERCENTAGE_RECOVED = { 0, 40, 0 };
    
    /**
     * Generates a new <code>Player</code>.
     * 
     * @param pName the name of <code>Player</code>
     * @param pType the type of <code>Player</code>
     * @return the <code>Player</code> generated
     */
    public static Player getNewPlayer(String pName, int pType) {
        Player player = null;
        
        if(pName.length() > Pokemon.MAX_CHARS_NAME) {
            pName = pName.substring(0, Pokemon.MAX_CHARS_NAME - 1);
        }
        
        try {
            switch(pType) {
                case Player.TYPE_FIRE:
                    player = new Player(pName, Player.TYPE_FIRE, PokemonGenerator.INITIAL_LEVEL, PokemonGenerator.INITIAL_X, PokemonGenerator.INITIAL_Y);
                    break;    

                case Player.TYPE_WATER:
                    player = new Player(pName, Player.TYPE_WATER, PokemonGenerator.INITIAL_LEVEL, PokemonGenerator.INITIAL_X, PokemonGenerator.INITIAL_Y);
                    break;

                case Player.TYPE_GRASS:
                    player = new Player(pName, Player.TYPE_GRASS, PokemonGenerator.INITIAL_LEVEL, PokemonGenerator.INITIAL_X, PokemonGenerator.INITIAL_Y); 
                    break;
                    
            }
        } catch (PokemonException ex) {
            //consant value
        }
        
        return player;
    }
    
    /**
     * Generates a new random <code>Enemy</code>.
     * 
     * @param pType the type of random <code>Enemy</code>
     * @param pLevel the level of random <code>Enemy</code>.
     * @return the random <code>Enemy</code> generated
     */
    public static Pokemon getRandomEnemy(int pType, int pLevel)  {
        Pokemon enemy = null; 
        
        try {
            switch(pType) {
                case Pokemon.TYPE_FIRE:
                    enemy = new Enemy(PokemonGenerator.CARMINE_NAME, pType, pLevel, PokemonGenerator.CARMINE_BATTLE_IMAGE);
                    
                    enemy.teachAttack(new Attack(PokemonGenerator.CARMINE_ATTACKS_NAMES[0], PokemonGenerator.CARMINE_ATTACKS_EFFECTS[0], PokemonGenerator.CARMINE_ATTACKS_POWER[0], PokemonGenerator.CARMINE_ATTACKS_TARGET[0], PokemonGenerator.CARMINE_ATTACKS_HP_PERCENTAGE_RECOVED[0])); //big damage
                    enemy.teachAttack(new Attack(PokemonGenerator.CARMINE_ATTACKS_NAMES[1], PokemonGenerator.CARMINE_ATTACKS_EFFECTS[1], PokemonGenerator.CARMINE_ATTACKS_POWER[1], PokemonGenerator.CARMINE_ATTACKS_TARGET[1], PokemonGenerator.CARMINE_ATTACKS_HP_PERCENTAGE_RECOVED[1])); //big self-damage
                    enemy.teachAttack(new Attack(PokemonGenerator.CARMINE_ATTACKS_NAMES[2], PokemonGenerator.CARMINE_ATTACKS_EFFECTS[2], PokemonGenerator.CARMINE_ATTACKS_POWER[2], PokemonGenerator.CARMINE_ATTACKS_TARGET[2], PokemonGenerator.CARMINE_ATTACKS_HP_PERCENTAGE_RECOVED[2])); //big self-damage
                    break;

                case Pokemon.TYPE_WATER:
                    enemy = new Enemy(PokemonGenerator.GIOVANNI_NAME, pType, pLevel, PokemonGenerator.GIOVANNI_BATTLE_IMAGE);
                    
                    enemy.teachAttack(new Attack(PokemonGenerator.GIOVANNI_ATTACKS_NAMES[0], PokemonGenerator.GIOVANNI_ATTACKS_EFFECTS[0], PokemonGenerator.GIOVANNI_ATTACKS_POWER[0], PokemonGenerator.GIOVANNI_ATTACKS_TARGET[0], PokemonGenerator.GIOVANNI_ATTACKS_HP_PERCENTAGE_RECOVED[0])); //no damage - big heal);
                    enemy.teachAttack(new Attack(PokemonGenerator.GIOVANNI_ATTACKS_NAMES[1], PokemonGenerator.GIOVANNI_ATTACKS_EFFECTS[1], PokemonGenerator.GIOVANNI_ATTACKS_POWER[1], PokemonGenerator.GIOVANNI_ATTACKS_TARGET[1], PokemonGenerator.GIOVANNI_ATTACKS_HP_PERCENTAGE_RECOVED[1])); //small damage
                    enemy.teachAttack(new Attack(PokemonGenerator.GIOVANNI_ATTACKS_NAMES[2], PokemonGenerator.GIOVANNI_ATTACKS_EFFECTS[2], PokemonGenerator.GIOVANNI_ATTACKS_POWER[2], PokemonGenerator.GIOVANNI_ATTACKS_TARGET[2], PokemonGenerator.GIOVANNI_ATTACKS_HP_PERCENTAGE_RECOVED[2])); //damage - big heal
                    break;

                case Pokemon.TYPE_GRASS:
                    enemy = new Enemy(PokemonGenerator.PEPPE_NAME, pType, pLevel, PokemonGenerator.PEPPE_BATTLE_IMAGE);
                    
                    enemy.teachAttack(new Attack(PokemonGenerator.PEPPE_ATTACKS_NAMES[0], PokemonGenerator.PEPPE_ATTACKS_EFFECTS[0], PokemonGenerator.PEPPE_ATTACKS_POWER[0], PokemonGenerator.PEPPE_ATTACKS_TARGET[0], PokemonGenerator.PEPPE_ATTACKS_HP_PERCENTAGE_RECOVED[0])); //recover half hp
                    enemy.teachAttack(new Attack(PokemonGenerator.PEPPE_ATTACKS_NAMES[1], PokemonGenerator.PEPPE_ATTACKS_EFFECTS[1], PokemonGenerator.PEPPE_ATTACKS_POWER[1], PokemonGenerator.PEPPE_ATTACKS_TARGET[1], PokemonGenerator.PEPPE_ATTACKS_HP_PERCENTAGE_RECOVED[1])); //nothing
                    enemy.teachAttack(new Attack(PokemonGenerator.PEPPE_ATTACKS_NAMES[2], PokemonGenerator.PEPPE_ATTACKS_EFFECTS[2], PokemonGenerator.PEPPE_ATTACKS_POWER[2], PokemonGenerator.PEPPE_ATTACKS_TARGET[2], PokemonGenerator.PEPPE_ATTACKS_HP_PERCENTAGE_RECOVED[2])); //nothing
                    break;
            }
        } catch (PokemonException | AttackException ex) {
            //constant value
        }
        
        return enemy;
    }
    
    /**
     * Generates a new boss <code>Enemy</code>.
     * 
     * @param pBoss the boss to generate.
     * @return the boss <code>Enemy</code> generated
     */
    public static Pokemon getBossEnemy(int pBoss) {
        Pokemon enemy = null;
        
        try {
            switch (pBoss) {
                case PokemonGenerator.PIACENTINO:
                    enemy = new Enemy(PokemonGenerator.PIACENTINO_NAME, Player.TYPE_WATER, PokemonGenerator.PIACENTINO_LEVEL, PokemonGenerator.PIACENTINO_BATTLE_IMAGE);
                    
                    enemy.teachAttack(new Attack(PokemonGenerator.PIACENTINO_ATTACKS_NAMES[0], PokemonGenerator.PIACENTINO_ATTACKS_EFFECTS[0], PokemonGenerator.PIACENTINO_ATTACKS_POWER[0], PokemonGenerator.PIACENTINO_ATTACKS_TARGET[0], PokemonGenerator.PIACENTINO_ATTACKS_HP_PERCENTAGE_RECOVED[0])); //big heal
                    enemy.teachAttack(new Attack(PokemonGenerator.PIACENTINO_ATTACKS_NAMES[1], PokemonGenerator.PIACENTINO_ATTACKS_EFFECTS[1], PokemonGenerator.PIACENTINO_ATTACKS_POWER[1], PokemonGenerator.PIACENTINO_ATTACKS_TARGET[1], PokemonGenerator.PIACENTINO_ATTACKS_HP_PERCENTAGE_RECOVED[1])); //big damage
                    enemy.teachAttack(new Attack(PokemonGenerator.PIACENTINO_ATTACKS_NAMES[2], PokemonGenerator.PIACENTINO_ATTACKS_EFFECTS[2], PokemonGenerator.PIACENTINO_ATTACKS_POWER[2], PokemonGenerator.PIACENTINO_ATTACKS_TARGET[2], PokemonGenerator.PIACENTINO_ATTACKS_HP_PERCENTAGE_RECOVED[2])); //small heal
                    break;
                case PokemonGenerator.PALOMBA:
                    enemy = new Enemy(PokemonGenerator.PALOMBA_NAME, Player.TYPE_GRASS, PokemonGenerator.PALOMBA_LEVEL, PokemonGenerator.PALOMBA_BATTLE_IMAGE);
                    
                    enemy.teachAttack(new Attack(PokemonGenerator.PALOMBA_ATTACKS_NAMES[0], PokemonGenerator.PALOMBA_ATTACKS_EFFECTS[0], PokemonGenerator.PALOMBA_ATTACKS_POWER[0], PokemonGenerator.PALOMBA_ATTACKS_TARGET[0], PokemonGenerator.PALOMBA_ATTACKS_HP_PERCENTAGE_RECOVED[0])); //nothing
                    enemy.teachAttack(new Attack(PokemonGenerator.PALOMBA_ATTACKS_NAMES[1], PokemonGenerator.PALOMBA_ATTACKS_EFFECTS[1], PokemonGenerator.PALOMBA_ATTACKS_POWER[1], PokemonGenerator.PALOMBA_ATTACKS_TARGET[1], PokemonGenerator.PALOMBA_ATTACKS_HP_PERCENTAGE_RECOVED[1])); //small self damage
                    enemy.teachAttack(new Attack(PokemonGenerator.PALOMBA_ATTACKS_NAMES[2], PokemonGenerator.PALOMBA_ATTACKS_EFFECTS[2], PokemonGenerator.PALOMBA_ATTACKS_POWER[2], PokemonGenerator.PALOMBA_ATTACKS_TARGET[2], PokemonGenerator.PALOMBA_ATTACKS_HP_PERCENTAGE_RECOVED[2])); //big damage
                    break;
                case PokemonGenerator.OLIVETO:
                    enemy = new Enemy(PokemonGenerator.OLIVETO_NAME, Player.TYPE_FIRE, PokemonGenerator.OLIVETO_LEVEL, PokemonGenerator.OLIVETO_BATTLE_IMAGE);
                    
                    enemy.teachAttack(new Attack(PokemonGenerator.OLIVETO_ATTACKS_NAMES[0], PokemonGenerator.OLIVETO_ATTACKS_EFFECTS[0], PokemonGenerator.OLIVETO_ATTACKS_POWER[0], PokemonGenerator.OLIVETO_ATTACKS_TARGET[0], PokemonGenerator.OLIVETO_ATTACKS_HP_PERCENTAGE_RECOVED[0])); // very big damage
                    enemy.teachAttack(new Attack(PokemonGenerator.OLIVETO_ATTACKS_NAMES[1], PokemonGenerator.OLIVETO_ATTACKS_EFFECTS[1], PokemonGenerator.OLIVETO_ATTACKS_POWER[1], PokemonGenerator.OLIVETO_ATTACKS_TARGET[1], PokemonGenerator.OLIVETO_ATTACKS_HP_PERCENTAGE_RECOVED[1])); //big heal
                    enemy.teachAttack(new Attack(PokemonGenerator.OLIVETO_ATTACKS_NAMES[2], PokemonGenerator.OLIVETO_ATTACKS_EFFECTS[2], PokemonGenerator.OLIVETO_ATTACKS_POWER[2], PokemonGenerator.OLIVETO_ATTACKS_TARGET[2], PokemonGenerator.OLIVETO_ATTACKS_HP_PERCENTAGE_RECOVED[2])); //big damage
                    break;
            }
        } catch (PokemonException | AttackException ex) {
            //constant value
        }
        
        return enemy;
    }
}
