package pokemon.GUI.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import pokemon.GUI.DialogBox;
import pokemon.GUI.GridMenu;
import pokemon.GUI.MainFrame;
import pokemon.GUI.PokemonBox;
import pokemon.logic.Attack;
import pokemon.logic.BattleController;
import pokemon.logic.GameController;
import pokemon.logic.characters.Enemy;
import pokemon.logic.characters.Player;
import pokemon.utils.Language;
import pokemon.utils.Resources;
import pokemon.utils.Sound;
import pokemon.utils.SoundException;

/**
 * The class<code>BattlePanel</code> is the battle of the game.
 *
 * @author Marco Russodivito
 */
public class BattlePanel extends JPanel {

    private static final Dimension BATTLE_PANEL_DIMENSION = new Dimension(1280, 720);

    private static final BufferedImage BATTLE_BACKGROUND = Resources.getImage("/resources/Panels/BattlePanel/BattlePanel - background.png");

    private static final Dimension PLAYER_DIMENSION = new Dimension(400, 400);

    private static final Point ENEMY_LOCATION = new Point(790, 0);
    private static final Dimension ENEMY_DIMENSION = new Dimension(320, 320);

    //menu costant
    private static final int FIGHT = 0;
    private static final int ESCAPE = 1;

    //dialogs
    private static final String[][] INTRO_RANDOM_DIALOG = {
        {"Wild ", " appeared!"},
        {"È apparso un ", " selvatico!"}
    };
    private static final String[][] INTRO_BOSS_DIALOG = {
        {"The ", " exam is getting start!"},
        {"L'esame di ", " sta per iniziare!"},};
    private static final String[][] BATTLE_MENU_RANDOM = {
        {"FIGHT", "RUN",},
        {"LOTTA", "FUGA",}
    };
    private static final String[][] BATTLE_MENU_BOSS = {
        {"FIGHT", "WITHDRAW"},
        {"LOTTA", "RITIRATI",}
    };
    private static final String[] PLAYER_TURN_DIALOG = {", it's your turn!", ", è il tuo turno!"};
    private static final String[] USE_DIALOG = {" uses ", " usa "};

    private static final String[] SUPER_EFFECTIVE_DIALOG = {"It's super-effective!", "È superefficace!"};
    private static final String[] NOT_VERY_EFFECTIVE_DIALOG = {"It's not very effective.", "Non è molto efficace."};

    private static final String[] FAINTED_DIALOG = {" fainted.", " è esausto."};

    private static final String[][] EXP_DIALOG = {
        {" gains ", " exp points."},
        {" ottiene ", " punti esperienza."},};

    private static final String[] RUN = {"Get away saftly", "Scampato pericolo"};
    private static final String[] NOT_RUN = {"Can't escape", "Non si scappa"};
    private static final String[] WITHDRAW = {"Come back to the next exam session", "Ritorna al prossimo appello"};

    private static final String[] PIACENTINO_WIN_DIALOG = {"Your essay was well copied, now I can rest. I'll teach you how do it.", "La tua tesina era copiata molto bene, ora posso riposare. Ti insegno come fare."};
    private static final String[] PALOMBA_WIN_DIALOG = {"Congratulation, you managed to make my fanduino works.", "Complimenti, sei riuscito a far funzionare il mio FANDUINO."};
    private static final String[] OLIVETO_WIN_DIALOG = {"Congratulation, you learned by the best developer. Let's go show your CAZZIMMA around.", "Complimenti, hai imparato migliore programmatore. Vai a mostrare la tua CAZZIMMA in giro."};

    private static final String[] PIACENTINO_LOST_DIALOG = {"You don't even know how to copy an essay from Internet!", "Non sei nemmeno capace di copiare una tesina da Internet!"};
    private static final String[] PALOMBA_LOST_DIALOG = {"Damn, why does no one understand Assembly?", "Dannazione, perchè nessuno capisce l'Assembly?"};
    private static final String[] OLIVETO_LOST_DIALOG = {"Loser! You will never beat the best developer of Unimol!", "Sei un perdente! Non potrai mai battere il miglior programmatore dell'Unimol!"};

    private static final String[] LEARN = {" learns ", " impara "};

    //sound
    private static final Sound BATTLE_SONG = Resources.getSound("/resources/Panels/BattlePanel/BattlePanel - background.wav");
    private static final Sound ESCAPE_SOUND = Resources.getSound("/resources/Panels/BattlePanel/BattlePanel - runaway.wav");
    private static final Sound WIN_RANDOM_SOUND = Resources.getSound("/resources/Panels/BattlePanel/BattlePanel - winRandom.wav");
    private static final Sound WIN_BOSS_SOUND = Resources.getSound("/resources/Panels/BattlePanel/BattlePanel - winBoss.wav");
    private static final Sound LOSE_SOUND = Resources.getSound("/resources/Panels/BattlePanel/BattlePanel - lose.wav");

    //Troncarelli of this battle
    private BattleController battleController;

    //pokemon boxes
    private PokemonBox playerBox;
    private boolean activePlayer;
    private PokemonBox enemyBox;
    private boolean activeEnemy;

    //dialog box
    private DialogBox dialogBox;
    private boolean activeDialogBox;

    //battle menu
    private GridMenu battleMenu;
    private boolean activeBattleMenu;

    //attacks menu
    private GridMenu attacksMenu;
    private boolean activeAttacksMenu;

    //move player to escape
    private Point playerLocation;

    //background music
    private Clip backgroundMusic;
    private Clip winRanomSound;
    private Clip winBossSound;

    //use for intro and outro dim animation
    private Thread introThread;
    private Thread outroThread;
    private float dimOffset;

    //used to syncronize threads.
    private boolean isThreadRunning;

    /**
     * Creates the <code>BattlePanel</code>.
     *
     * @param pBattleController the <code>BattleController</code> that manages
     * the game.
     */
    public BattlePanel(BattleController pBattleController) {
        this.initBattlePanel();

        this.battleController = pBattleController;

        //init player and enemy boxes
        this.playerBox = new PokemonBox(810, 340, 460, 165, this.battleController.getPlayer());
        this.activePlayer = false;
        this.enemyBox = new PokemonBox(10, 10, 460, 165, this.battleController.getEnemy());
        this.activeEnemy = false;

        //init dialog box
        this.dialogBox = new DialogBox(5, 550, 1270, 165);
        this.activeDialogBox = false;

        //init battle menu
        if (battleController.isBossBattle()) {
            this.battleMenu = new GridMenu(645, 545, 635, 175, BATTLE_MENU_BOSS[Language.getLanguage()]);
        } else {
            this.battleMenu = new GridMenu(645, 545, 635, 175, BATTLE_MENU_RANDOM[Language.getLanguage()]);
        }
        this.activeBattleMenu = false;

        //init attacks name
        this.attacksMenu = new GridMenu(645, 545, 635, 175, battleController.getPlayer().getAttacksName());
        this.activeAttacksMenu = false;

        //set default player location
        this.playerLocation = new Point(0, 145);

        //get new win sound and loop background music
        try {
            this.backgroundMusic = BattlePanel.BATTLE_SONG.loop();
            this.winRanomSound = BattlePanel.WIN_RANDOM_SOUND.getNewClip();
            this.winBossSound = BattlePanel.WIN_BOSS_SOUND.getNewClip();
        } catch (SoundException ex) {
            Logger.getLogger(BattlePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //start dim animation
        this.dimOffset = 0;
        this.introThread = new Thread(new IntroDimAnimation());
        this.introThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage dimmableBackgroundImage = new RescaleOp(this.dimOffset, 0, null).filter(BattlePanel.BATTLE_BACKGROUND, null);
        g.drawImage(dimmableBackgroundImage, 0, 0, BattlePanel.BATTLE_PANEL_DIMENSION.width, BattlePanel.BATTLE_PANEL_DIMENSION.height, this);

        //draw player if is active
        if (this.activePlayer) {
            this.playerBox.draw(g, this);
            this.battleController.getPlayer().drawRear(this.playerLocation.x, this.playerLocation.y, BattlePanel.PLAYER_DIMENSION.width, BattlePanel.PLAYER_DIMENSION.height, g, this);
        }

        //draw enemy if is active
        if (this.activeEnemy) {
            this.enemyBox.draw(g, this);
            this.battleController.getEnemy().drawBattleImage(BattlePanel.ENEMY_LOCATION.x, BattlePanel.ENEMY_LOCATION.y, BattlePanel.ENEMY_DIMENSION.width, BattlePanel.ENEMY_DIMENSION.height, g, this);
        }

        //draw dialog box if is active
        if (this.activeDialogBox) {
            this.dialogBox.draw(g, this);
        }

        //draw battle menu if is active
        if (this.activeBattleMenu) {
            this.battleMenu.draw(g, this);
        }

        //draw attack menu if is active
        if (this.activeAttacksMenu) {
            this.attacksMenu.draw(g, this);
        }

        //border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, BattlePanel.BATTLE_PANEL_DIMENSION.width - 1, BattlePanel.BATTLE_PANEL_DIMENSION.height - 1);
    }

    private void startBattle() {
        this.battleController.startBattle();

        if (this.battleController.getTurn() == BattleController.PLAYER_TURN) {
            this.playerTurn();
        } else {
            this.enemyTurn();
        }
    }

    private void switchTurn() {
        //switch turn
        this.battleController.switchTurn();

        //decide who is the next player
        if (this.battleController.getTurn() == BattleController.PLAYER_TURN) {
            this.playerTurn();
        } else {
            this.enemyTurn();
        }
    }

    private void playerTurn() {
        this.activeDialogBox = true;
        this.dialogBox.setDialog(this.battleController.getPlayer().getName() + PLAYER_TURN_DIALOG[Language.getLanguage()], false);

        this.activeBattleMenu = true;
        this.addKeyListener(new BattleMenuListener());

        repaint();
    }

    private void enemyTurn() {
        int idAttack = (int) Math.round(Math.random() * (battleController.getEnemy().getNumAttacks() - 1));

        Thread enemyTurnThread = new Thread(new TurnThread(idAttack));
        enemyTurnThread.start();
    }

    private void initBattlePanel() {
        this.setSize(BattlePanel.BATTLE_PANEL_DIMENSION);
        this.setFocusable(true);
        this.setLayout(null);
    }

    private class BattleMenuListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                battleMenu.previousSelection();
                repaint();
            }

            if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                battleMenu.nextSelection();
                repaint();
            }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                switch (battleMenu.getEntrySelected()) {
                    case BattlePanel.FIGHT:
                        activeBattleMenu = false;
                        removeKeyListener(this);

                        activeAttacksMenu = true;
                        addKeyListener(new AttacksMenuListener());

                        repaint();
                        break;

                    case BattlePanel.ESCAPE:
                        activeBattleMenu = false;
                        removeKeyListener(this);

                        Thread escapeThread = new Thread(new EscapeAnimation());
                        escapeThread.start();

                        repaint();
                        break;
                }
            }
        }
    }

    private class AttacksMenuListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                activeAttacksMenu = false;
                removeKeyListener(this);

                activeBattleMenu = true;
                addKeyListener(new BattleMenuListener());

                repaint();
            }

            if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                attacksMenu.previousSelection();
                repaint();
            }

            if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                attacksMenu.nextSelection();
                repaint();
            }

            if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                //appezzotation
                attacksMenu.previousSelection();
                attacksMenu.previousSelection();
                repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                //appezzotation
                attacksMenu.nextSelection();
                attacksMenu.nextSelection();
                repaint();
            }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                int idAttack = attacksMenu.getEntrySelected();

                activeAttacksMenu = false;
                removeKeyListener(this);

                Thread playerTurn = new Thread(new TurnThread(idAttack));
                playerTurn.start();

                repaint();
            }
        }
    }

    private class IntroDimAnimation implements Runnable {

        @Override
        public void run() {
            try {

                int counter = 0;

                while (dimOffset <= 1) {
                    dimOffset = (float) counter / 1000;
                    counter++;

                    Thread.sleep(2);
                    repaint();
                }

                activePlayer = true;
                activeEnemy = true;
                activeDialogBox = true;
                repaint();

                if (battleController.isBossBattle()) {
                    if (battleController.getEnemy().getName().equals("PIACENTINO")) {
                        dialogBox.setDialog(BattlePanel.INTRO_BOSS_DIALOG[Language.getLanguage()][0] + "EVOLUZIONE" + BattlePanel.INTRO_BOSS_DIALOG[Language.getLanguage()][1], false);
                        repaint();
                    }

                    if (battleController.getEnemy().getName().equals("PALOMBA")) {
                        dialogBox.setDialog(BattlePanel.INTRO_BOSS_DIALOG[Language.getLanguage()][0] + "ARCHITETTURA" + BattlePanel.INTRO_BOSS_DIALOG[Language.getLanguage()][1], false);
                        repaint();
                    }

                    if (battleController.getEnemy().getName().equals("OLIVETO")) {
                        dialogBox.setDialog(BattlePanel.INTRO_BOSS_DIALOG[Language.getLanguage()][0] + "PROGRAMMAZIONE" + BattlePanel.INTRO_BOSS_DIALOG[Language.getLanguage()][1], false);
                        repaint();
                    }

                } else {
                    dialogBox.setDialog(BattlePanel.INTRO_RANDOM_DIALOG[Language.getLanguage()][0] + battleController.getEnemy().getName() + BattlePanel.INTRO_RANDOM_DIALOG[Language.getLanguage()][1], false);
                    repaint();

                }

                Thread.sleep(2000);

                startBattle();

            } catch (InterruptedException ex) {
                Logger.getLogger(TutorialPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class TurnThread implements Runnable {

        private int idAttack;

        public TurnThread(int pIDAttack) {
            this.idAttack = pIDAttack;
        }

        @Override
        public void run() {
            try {
                Attack attack = battleController.getActivePokemon().useAttack(this.idAttack);

                dialogBox.setDialog(battleController.getActivePokemon().getName() + USE_DIALOG[Language.getLanguage()] + attack.getName(), false);
                repaint();

                Thread.sleep(1000);

                dialogBox.setDialog(battleController.getActivePokemon().getName() + " " + attack.getEffectDescription(), false);
                repaint();

                Thread.sleep(2000);

                //damage thread
                int damage = battleController.calculateDamage(attack);
                if ((attack.getTarget() == Attack.TARGET_OTHER_POKEMON && battleController.getActivePokemon() instanceof Player) || (attack.getTarget() == Attack.TARGET_SELF && battleController.getActivePokemon() instanceof Enemy)) {
                    Thread enemyLoseHPThread = new Thread(new EnemyLoseHPAnimation(damage));
                    enemyLoseHPThread.start();
                } else if ((attack.getTarget() == Attack.TARGET_OTHER_POKEMON && battleController.getActivePokemon() instanceof Enemy) || (attack.getTarget() == Attack.TARGET_SELF && battleController.getActivePokemon() instanceof Player)) {
                    Thread playerLoseHPThread = new Thread(new PlayerLoseHPAnimation(damage));
                    playerLoseHPThread.start();
                }

                
                //busy waiting -> this thread wait the end of lose hp thread
                isThreadRunning = true;
                while (isThreadRunning) {
                    Thread.sleep(100);
                }

                //hprecoved thread
                int hp = battleController.calculateHPRecoved(attack);
                Thread recoveHPThread = new Thread(new RecoverHPAnimation(hp));
                recoveHPThread.start();

                //busy waiting -> this thread wait the end of recovehp
                isThreadRunning = true;
                while (isThreadRunning) {
                    Thread.sleep(100);
                }
                
                //super effective or not
                if ((damage > 0) && (attack.getTarget() == Attack.TARGET_OTHER_POKEMON)) {
                    if (battleController.calculateTypeEffectiveness() == BattleController.SUPER_EFFECTIVE) {
                        dialogBox.setDialog(BattlePanel.SUPER_EFFECTIVE_DIALOG[Language.getLanguage()], false);
                        repaint();
                        Thread.sleep(1500);
                    } else if ((battleController.calculateTypeEffectiveness() == BattleController.NOT_VERY_EFFECTIVE)) {
                        dialogBox.setDialog(BattlePanel.NOT_VERY_EFFECTIVE_DIALOG[Language.getLanguage()], false);
                        repaint();
                        Thread.sleep(1500);
                    }
                }

                
                //check player and enemy condition
                if (battleController.enemyIsDead()) { //player win battle
                    backgroundMusic.stop();
                    
                    if(battleController.isBossBattle()) {
                        winBossSound.start();
                    } else {
                        winRanomSound.start();
                    }
                    
                    dialogBox.setDialog(battleController.getEnemy().getName() + BattlePanel.FAINTED_DIALOG[Language.getLanguage()], false);
                    repaint();
                    Thread.sleep(1000);

                    if (battleController.getEnemy().getName().equals("PIACENTINO")) {
                        dialogBox.setDialog(BattlePanel.PIACENTINO_WIN_DIALOG[Language.getLanguage()], false);
                        repaint();
                        Thread.sleep(2500);

                        dialogBox.setDialog(battleController.getPlayer().getName() + LEARN[Language.getLanguage()] + Player.REST[Language.getLanguage()], false);
                        repaint();
                        Thread.sleep(2000);
                    }

                    if (battleController.getEnemy().getName().equals("PALOMBA")) {
                        dialogBox.setDialog(BattlePanel.PALOMBA_WIN_DIALOG[Language.getLanguage()], false);
                        repaint();
                        Thread.sleep(2500);

                        dialogBox.setDialog(battleController.getPlayer().getName() + LEARN[Language.getLanguage()] + Player.FANDUINO[Language.getLanguage()], false);
                        repaint();
                        Thread.sleep(2000);
                    }

                    if (battleController.getEnemy().getName().equals("OLIVETO")) {
                        dialogBox.setDialog(BattlePanel.OLIVETO_WIN_DIALOG[Language.getLanguage()], false);
                        repaint();
                        Thread.sleep(2500);

                        dialogBox.setDialog(battleController.getPlayer().getName() + LEARN[Language.getLanguage()] + Player.CAZZIMMA[Language.getLanguage()], false);
                        repaint();
                        Thread.sleep(2000);
                    }
                    
                    Thread playerGainExpThread = new Thread(new PlayerGainExpAnimation());
                    playerGainExpThread.start();
                    
                } else if (battleController.playerIsDead()) { //if player lose
                        
                        BattlePanel.LOSE_SOUND.play();

                        dialogBox.setDialog(battleController.getPlayer().getName() + BattlePanel.FAINTED_DIALOG[Language.getLanguage()], false);
                        repaint();
                        Thread.sleep(1000);

                        if (battleController.getEnemy().getName().equals("PIACENTINO")) {
                            dialogBox.setDialog(BattlePanel.PIACENTINO_LOST_DIALOG[Language.getLanguage()], false);
                            repaint();
                            Thread.sleep(2500);
                        }

                        if (battleController.getEnemy().getName().equals("PALOMBA")) {
                            dialogBox.setDialog(BattlePanel.PALOMBA_LOST_DIALOG[Language.getLanguage()], false);
                            repaint();
                            Thread.sleep(2500);
                        }

                        if (battleController.getEnemy().getName().equals("OLIVETO")) {
                            dialogBox.setDialog(BattlePanel.OLIVETO_LOST_DIALOG[Language.getLanguage()], false);
                            repaint();
                            Thread.sleep(2500);
                        }

                        //heal and teleport player at coffee machine
                        GameController.teleportPlayerAtBreakPoint();

                        outroThread = new Thread(new OutroDimAnimation());
                        outroThread.start();
                } else {
                    Thread.sleep(2000);
                    switchTurn();
                }
                
            } catch (InterruptedException e) {
                //
            }
        }
    }

    private class EnemyLoseHPAnimation implements Runnable {

        int damage;

        public EnemyLoseHPAnimation(int pDamage) {
            this.damage = pDamage;
        }

        @Override
        public void run() {
            isThreadRunning = true;
            try {
                while ((this.damage > 0) && !(battleController.enemyIsDead())) {

                    battleController.getEnemy().reciveDamage(1);
                    this.damage--;

                    enemyBox.updatePokemonStatus(battleController.getEnemy());
                    repaint();

                    Thread.sleep(20);

                }
            } catch (InterruptedException ex) {
                Logger.getLogger(BattlePanel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                isThreadRunning = false;
            }
        }

    }

    private class PlayerLoseHPAnimation implements Runnable {

        int damage;

        public PlayerLoseHPAnimation(int pDamage) {
            this.damage = pDamage;
        }

        @Override
        public void run() {
            isThreadRunning = true;
            try {
                while ((this.damage > 0) && !(battleController.playerIsDead())) {

                    battleController.getPlayer().reciveDamage(1);
                    this.damage--;

                    playerBox.updatePokemonStatus(battleController.getPlayer());
                    repaint();

                    Thread.sleep(20);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(BattlePanel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                isThreadRunning = false;
            }
        }

    }

    private class RecoverHPAnimation implements Runnable {

        int hp;

        private RecoverHPAnimation(int hp) {
            this.hp = hp;
        }

        @Override
        public void run() {
            isThreadRunning = true;
            try {
                while ((this.hp > 0) && !(battleController.getActivePokemon().getHp() == battleController.getActivePokemon().getMaxHp())) {

                    battleController.getActivePokemon().heal(1);
                    this.hp--;

                    enemyBox.updatePokemonStatus(battleController.getEnemy());
                    playerBox.updatePokemonStatus(battleController.getPlayer());
                    repaint();

                    Thread.sleep(30);
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(BattlePanel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                isThreadRunning = false;
            }
        }

    }

    private class PlayerGainExpAnimation implements Runnable {

        @Override
        public void run() {
            try {
                int exp = battleController.calculateExp();

                dialogBox.setDialog(battleController.getPlayer().getName() + BattlePanel.EXP_DIALOG[Language.getLanguage()][0] + exp + BattlePanel.EXP_DIALOG[Language.getLanguage()][1], false);
                repaint();
                Thread.sleep(1000);

                while (exp > 0) {
                    battleController.getPlayer().gainEXP(1);
                    exp--;

                    playerBox.updatePokemonStatus(battleController.getPlayer());
                    repaint();

                    Thread.sleep(50);
                }

                Thread.sleep(2000);

                Thread th = new Thread(new OutroDimAnimation());
                th.start();
            } catch (InterruptedException ex) {
                Logger.getLogger(BattlePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class EscapeAnimation implements Runnable {

        @Override
        public void run() {
            try {

                if (battleController.playerCanEscape()) {
                    //can escape

                    //text to draw
                    if (battleController.isBossBattle()) {
                        dialogBox.setDialog(BattlePanel.WITHDRAW[Language.getLanguage()], false);
                        repaint();
                    } else {
                        dialogBox.setDialog(BattlePanel.RUN[Language.getLanguage()], false);
                        repaint();
                    }
                    Thread.sleep(1000);

                    //stop background sound and play escape sound
                    backgroundMusic.stop();
                    BattlePanel.ESCAPE_SOUND.play();

                    //move player out of screen
                    while (playerLocation.x >= -(BattlePanel.PLAYER_DIMENSION.width)) {
                        playerLocation.translate(-1, 0);
                        repaint();
                        Thread.sleep(1);
                    }

                    outroThread = new Thread(new OutroDimAnimation());
                    outroThread.start();
                } else {
                    //escape blocked
                    dialogBox.setDialog(BattlePanel.NOT_RUN[Language.getLanguage()], false);
                    repaint();
                    Thread.sleep(1000);

                    switchTurn();
                }
            } catch (InterruptedException e) {
                //
            }
        }
    }

    private class OutroDimAnimation implements Runnable {

        @Override
        public void run() {
            //disable all components
            activeAttacksMenu = false;
            activeBattleMenu = false;
            activeDialogBox = false;
            activeEnemy = false;
            activePlayer = false;

            int counter = 1000;
            while (dimOffset >= 0) {
                dimOffset = (float) counter / 1000;
                counter--;
                try {
                    Thread.sleep(2);
                    repaint();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TutorialPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            winRanomSound.stop();
            winBossSound.stop();
            battleController.endBattle();
            MainFrame.switchPanel(MainFrame.FROM_BATTLE_TO_MAP);
        }

    }
}
