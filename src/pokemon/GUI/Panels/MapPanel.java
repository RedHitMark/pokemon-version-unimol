package pokemon.GUI.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import pokemon.GUI.DialogBox;
import pokemon.GUI.MainFrame;
import pokemon.GUI.VerticalMenu;
import pokemon.logic.GameController;
import pokemon.logic.characters.Player;
import pokemon.utils.SaveException;
import pokemon.utils.Language;
import pokemon.utils.Resources;
import pokemon.utils.Save;
import pokemon.utils.Sound;

/**
 * The class <code>MapPanel</code> is the map of the game.
 * 
 * @author Marco Russodivito
 */
public class MapPanel extends JPanel {
    
    private static final Dimension MAP_PANEL_DIMENSION = new Dimension(1280,720);
    
    private static final String[][] IN_GAME_MENU_OPTIONS = {
        {GameController.getPlayer().getName(), "OPTION", "SAVE", "BACK", "EXIT"},
        {GameController.getPlayer().getName(), "OPZIONI", "SALVA","INDIETRO", "ESCI"}
    };
    
    private static final String[][] SAVE_DIALOG = {
        {GameController.getPlayer().getName() + " saved the game", "An error occured saving the game."},
        {GameController.getPlayer().getName() + " ha salvato il gioco", "Si è verificato un errore durante il salvataggio del gioco."}
    };
    private static final int SAVE_COMPLETED = 0;
    private static final int SAVE_ERROR = 1;
    
    private static final String[] PIACENTINO_DIALOG_BEFORE_EXAM = { GameController.getPlayer().getName() + ", let's discuss about the essay you copied from Internet!", GameController.getPlayer().getName() +", discutiamo la tesina che hai copiato da Internet!"};
    private static final String[] PIACENTINO_DIALOG_AFTER_EXAM = {"ZzZ...    ZzZ...    ZzZ...", "ZzZ...    ZzZ...    ZzZ..."};
    
    private static final String[] PALOMBA_DIALOG_BEFORE_EXAM1 = {"I have't corrected your test yet... Came back later for oral examination", "Non ho ancora correggere il tuo compito... Ripassa più tardi se vuoi fare l’orale."};
    private static final String[] PALOMBA_DIALOG_BEFORE_EXAM2 = {GameController.getPlayer().getName() + ", your test was well copied. Now let's speak about bugged Assembly", GameController.getPlayer().getName() + ", il tuo compito era copiato molto bene. Adesso parlami del Assemlby buggato"};
    private static final String[] PALOMBA_DIALOG_AFTER_EXAM = {"I'm  trying to see how does a semaphore works", "Sto cercando di capire come funziona un semaforo"};
    
    private static final String[] OLIVETO_DIALOG_BEFORE_EXAM1 = {"\"Programmazione e Laboratorio\" lesson is suspended.", "La lezione di \"Programmazione e Laboratorio' è stata sospesa\"."};
    private static final String[] OLIVETO_DIALOG_BEFORE_EXAM2 = {"Show me your cazzimma!", "Mostrami la tua cazzimma!"};
    private static final String[] OLIVETO_DIALOG_AFTER_EXAM = {"Congratulation, you reused well my code.", "Complimenti, hai riusato bene il mio codice."};
    
    //menu
    private static final int BADGE_MENU = 0;
    private static final int OPTION_MENU = 1;
    private static final int SAVE_MENU = 2;
    private static final int BACK_MENU = 3;
    private static final int EXIT_MENU = 4;
    
    //map image
    private static final BufferedImage MAP = Resources.getImage("/resources/Panels/MapPanel/MapPanel - unimol.png");
    private static final Dimension MAP_DIMENSION = new Dimension(2960, 1200);
    
    //player 
    private static final Dimension PLAYER_DIMENSION = new Dimension(80, 80);
    
    //sound
    private static final Sound CLICK_SOUND = Resources.getSound("/resources/Click.wav");
    private static final Sound MAP_SONG = Resources.getSound("/resources/Panels/MapPanel/MapPanel - background.wav");
    private static final Sound BREAK_SONG = Resources.getSound("/resources/Panels/MapPanel/MapPanel - break.wav");
    
    
    //map value
    private float mapDimOffset;
    private int mapOffsetX;
    private int mapOffsetY;
    private boolean mapMoving;
    
    //player value
    private boolean activePlayer;
    private boolean playerMoving;
    
    //dialog
    private DialogBox dialogBox;
    private boolean activeDialogBox; 
    private boolean currentDialogTerminated;
    
    //menu
    private VerticalMenu inGameMenu;
    private boolean inGameMenuOpened;
    
    private Clip backgroundMusic;
    
    /**
     * Creates the <code>MapPanel</code>.
     * Initialize all <code>MapPanel</code>'s components.
     * 
     * @param pMenuOpened <code>true</code> if menu should be opened, <code>false</code> otherwise
     */
    public MapPanel(boolean pMenuOpened) {
        this.initMapPanel();
        
        //init map values
        this.mapDimOffset = 1f;
        this.mapOffsetX = 0;
        this.mapOffsetY = 0;
        this.mapMoving = false;
        
        //init player values
        this.playerMoving = false;
        this.activePlayer = true;
        
        //init dialog box
        this.dialogBox = new DialogBox(5, 535, 1270, 180);
        this.activeDialogBox = false;
        this.currentDialogTerminated = false;
        
        //init menu
        this.inGameMenu = new VerticalMenu(920, 10, 350, 415, MapPanel.IN_GAME_MENU_OPTIONS[Language.getLanguage()]);
        this.inGameMenuOpened = pMenuOpened;
        if(this.inGameMenuOpened) {
            this.addKeyListener(new MenuListener());
        } else {
            this.addKeyListener(new PlayerListener());
        }
        
        //start background music
        this.backgroundMusic = MapPanel.MAP_SONG.loop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        //black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, MapPanel.MAP_PANEL_DIMENSION.width, MapPanel.MAP_PANEL_DIMENSION.height);
        
        //map
        int x = 40 + 600 - 40 + GameController.getPlayer().getX()*(-80) + this.mapOffsetX;
        int y = 360 - 40  + GameController.getPlayer().getY()*(-80) + this.mapOffsetY;
        BufferedImage dimmableMapImage = new RescaleOp(this.mapDimOffset , 0, null).filter(MapPanel.MAP, null);
        g.drawImage(dimmableMapImage, x, y, MapPanel.MAP_DIMENSION.width, MapPanel.MAP_DIMENSION.height, this);
        
        //player
        if(this.activePlayer) {
            GameController.getPlayer().drawOnMap( (MapPanel.MAP_PANEL_DIMENSION.width - MapPanel.PLAYER_DIMENSION.width)/2, (MapPanel.MAP_PANEL_DIMENSION.height - MapPanel.PLAYER_DIMENSION.height)/2, MapPanel.PLAYER_DIMENSION.width, MapPanel.PLAYER_DIMENSION.height, this.playerMoving, g, this);
        }
        
        //dialogbox
        if(this.activeDialogBox) {
            this.dialogBox.draw(g, this);
        }
        
        //menu
        if(this.inGameMenuOpened) {
            this.inGameMenu.draw(g, this);
        }
        
        //border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, MapPanel.MAP_PANEL_DIMENSION.width - 1, MapPanel.MAP_PANEL_DIMENSION.height - 1); 
    }
    
    private void moveMapAndPlayer(int pDirection) {
        if(!this.playerMoving && !this.mapMoving) {
            
            if(GameController.getPlayer().getLookAt() == pDirection) {
                //walk
                if(GameController.canWalk(pDirection)) {
                    Thread playerMotionThread = new Thread(new PlayerMotion());
                    playerMotionThread.start();
                    
                    Thread mapMotionThread = new Thread(new MapMotion());
                    mapMotionThread.start();
                }    
            } else {
                //turn at that direction
                GameController.getPlayer().turnAt(pDirection);
                repaint();
            }
            //debug
            System.out.println(GameController.getPlayer());
        }    
    }
    
    private void initMapPanel() {
        this.setSize(MapPanel.MAP_PANEL_DIMENSION);
        this.setFocusable(true);
        this.setLayout(null);
    }
    
    private class MenuListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                inGameMenu.resetEntrySelection();
                inGameMenuOpened = false;
                removeKeyListener(this);
                
                addKeyListener(new PlayerListener());
                repaint();
            }
                
            if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                inGameMenu.previousEntry();
                repaint();   
            }

            if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                inGameMenu.nextEntry();
                repaint();   
            }


            if(e.getKeyCode() == KeyEvent.VK_SPACE) {

                switch(inGameMenu.getEntrySelected()) {
                    
                    case MapPanel.BADGE_MENU:
                        MapPanel.CLICK_SOUND.play();
                        backgroundMusic.stop();
                        
                        MainFrame.switchPanel(MainFrame.FROM_MAP_TO_BADGE);
                        break;

                    case MapPanel.OPTION_MENU: //open option
                        MapPanel.CLICK_SOUND.play();
                        backgroundMusic.stop();
                        
                        MainFrame.switchPanel(MainFrame.FROM_MAP_TO_OPTION);
                        break;

                    case MapPanel.SAVE_MENU: //save game
                        MapPanel.CLICK_SOUND.play();
                        
                        try {
                            Save.save(GameController.getPlayer());
                            Thread drawDialogThread = new Thread(new DrawDialog(SAVE_DIALOG[Language.getLanguage()][MapPanel.SAVE_COMPLETED]));
                            drawDialogThread.start();
                                    
                        } catch (SaveException ex) {
                            Thread drawDialogThread = new Thread(new DrawDialog(SAVE_DIALOG[Language.getLanguage()][MapPanel.SAVE_ERROR]));
                            drawDialogThread.start();
                        } finally {
                            inGameMenuOpened = false;
                            removeKeyListener(this);
                            
                            activeDialogBox = true;
                            addKeyListener(new SaveDialogBoxListener());
                            repaint();
                        }    
                        break;

                    case MapPanel.BACK_MENU: //close menu
                        MapPanel.CLICK_SOUND.play();
                        
                        inGameMenu.resetEntrySelection();
                        inGameMenuOpened = false;
                        removeKeyListener(this);
                        
                        addKeyListener(new PlayerListener());
                        repaint();
                        break;

                    case MapPanel.EXIT_MENU: //exit game
                        MapPanel.CLICK_SOUND.play();
                        
                        System.exit(0);
                        break;
                }   
            }
        }
    }
    
    private class PlayerListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { //open menu
                if(!playerMoving && !mapMoving){
                    inGameMenuOpened = true;
                    removeKeyListener(this);
                    addKeyListener(new MenuListener());
                    repaint();
                }    
            }

            if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) { 
                if(!playerMoving && !mapMoving){ //move up
                    moveMapAndPlayer(Player.UP);
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                if(!playerMoving && !mapMoving){ // moveMapAndPlayer down
                    moveMapAndPlayer(Player.DOWN);
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(!playerMoving && !mapMoving) { // moveMapAndPlayer right
                    moveMapAndPlayer(Player.RIGHT);
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                if(!playerMoving && !mapMoving) { //move left
                    moveMapAndPlayer(Player.LEFT);
                }    
            }
            
            
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                
                switch (GameController.talkWith()) {
                    
                    case GameController.MAP_PIACENTINO:
                        if( GameController.getPlayer().isPiacentinoDefeated() ) {
                            Thread drawPiacentinoDialogThread = new Thread(new DrawDialog(MapPanel.PIACENTINO_DIALOG_AFTER_EXAM[Language.getLanguage()]));
                            drawPiacentinoDialogThread.start();
                        } else {
                            Thread drawPiacentinoDialogThread = new Thread(new DrawDialog(MapPanel.PIACENTINO_DIALOG_BEFORE_EXAM[Language.getLanguage()]));
                            drawPiacentinoDialogThread.start();
                        }
                        
                        removeKeyListener(this);
                            
                        activeDialogBox = true;
                        addKeyListener(new PiacentinoDialogBoxListener());
                        repaint();
                        break;
                        
                    case GameController.MAP_PALOMBA:
                        if(GameController.getPlayer().isPalombaDefeated()) {
                            Thread drawPalombaDialogThread = new Thread(new DrawDialog(MapPanel.PALOMBA_DIALOG_AFTER_EXAM[Language.getLanguage()]));
                            drawPalombaDialogThread.start();
                        } else if ( GameController.getPlayer().isPiacentinoDefeated() ) {
                            Thread drawPalombaDialogThread = new Thread(new DrawDialog(MapPanel.PALOMBA_DIALOG_BEFORE_EXAM2[Language.getLanguage()]));
                            drawPalombaDialogThread.start();
                        } else {
                            Thread drawPalombaDialogThread = new Thread(new DrawDialog(MapPanel.PALOMBA_DIALOG_BEFORE_EXAM1[Language.getLanguage()]));
                            drawPalombaDialogThread.start();
                        }
                        
                        removeKeyListener(this);
                            
                        activeDialogBox = true;
                        addKeyListener(new PalombaDialogBoxListener());
                        repaint();
                        break;
                        
                    case GameController.MAP_OLIVETO:
                        if(GameController.getPlayer().isOlivetoDefeated()) {
                            Thread drawOlivetoDialogThread = new Thread(new DrawDialog(MapPanel.OLIVETO_DIALOG_AFTER_EXAM[Language.getLanguage()]));
                            drawOlivetoDialogThread.start();
                        } else if ( !(GameController.getPlayer().isPiacentinoDefeated() && GameController.getPlayer().isPalombaDefeated()) ) {
                            Thread drawOlivetoDialogThread = new Thread(new DrawDialog(MapPanel.OLIVETO_DIALOG_BEFORE_EXAM1[Language.getLanguage()]));
                            drawOlivetoDialogThread.start();
                        } else {
                            Thread drawOlivetoDialogThread = new Thread(new DrawDialog(MapPanel.OLIVETO_DIALOG_BEFORE_EXAM2[Language.getLanguage()]));
                            drawOlivetoDialogThread.start();
                        }
                        
                        removeKeyListener(this);
                            
                        activeDialogBox = true;
                        addKeyListener(new OlivetoDialogBoxListener());
                        repaint();
                        
                        break;
                        
                    case GameController.MAP_BREAK:
                        removeKeyListener(this);
                        
                        Thread breakThread = new Thread(new BreakAnimation());
                        breakThread.start();
                        GameController.getPlayer().heal();
                        break;
                        
                }
            }
        }
    }
    
    private class SaveDialogBoxListener extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                
                if(!currentDialogTerminated) { //terminate the dialog
                    currentDialogTerminated = true;
                    repaint();
                } else {
                    currentDialogTerminated = false;
                    activeDialogBox = false;
                    removeKeyListener(this);
                    
                    addKeyListener(new PlayerListener());
                    repaint();
                }    
            }
        }
    }
    
    private class PiacentinoDialogBoxListener extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                if(!currentDialogTerminated) { //terminate the dialog
                    currentDialogTerminated = true;
                    repaint();
                } else {
                    
                    if( GameController.getPlayer().isPiacentinoDefeated()) {
                        currentDialogTerminated = false;
                        activeDialogBox = false;
                        removeKeyListener(this);

                        addKeyListener(new PlayerListener());
                        repaint();
                    } else {
                        backgroundMusic.stop();
                        
                        MainFrame.switchPanel(MainFrame.FROM_MAP_TO_PIACENTINO_BATTLE);
                    } 
                }
            }
        }
    }
    
    private class PalombaDialogBoxListener extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                if(!currentDialogTerminated) { //terminate the dialog
                    currentDialogTerminated = true;
                    repaint();
                } else {
                    
                    if( GameController.getPlayer().isPalombaDefeated() || (!(GameController.getPlayer().isPiacentinoDefeated()))) {
                        currentDialogTerminated = false;
                        activeDialogBox = false;
                        removeKeyListener(this);

                        addKeyListener(new PlayerListener());
                        repaint();
                    } else {
                        backgroundMusic.stop();
                        
                        MainFrame.switchPanel(MainFrame.FROM_MAP_TO_PALOMBA_BATTLE);
                    }
                    
                }
            }
        }
    }
    
    private class OlivetoDialogBoxListener extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                if(!currentDialogTerminated) { //terminate the dialog
                    currentDialogTerminated = true;
                    repaint();
                } else {
                    
                    if( GameController.getPlayer().isOlivetoDefeated() || !(GameController.getPlayer().isPiacentinoDefeated() && GameController.getPlayer().isPalombaDefeated())  ) {
                        currentDialogTerminated = false;
                        activeDialogBox = false;
                        removeKeyListener(this);

                        addKeyListener(new PlayerListener());
                        repaint();
                    } else {
                        backgroundMusic.stop();
                        
                        MainFrame.switchPanel(MainFrame.FROM_MAP_TO_OLIVETO_BATTLE);
                    }    
                }
            }
        }
    }
    
    private class PlayerMotion implements Runnable {
        
        @Override
        public void run() {
            try {
                //change
                playerMoving = true;
                repaint();
                Thread.sleep(250);
                
                //normal
                playerMoving = false;
                repaint();
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                
            }
        }
    }
    
    private class MapMotion implements Runnable {

        @Override
        public void run() {
            float timer = 0;
            
            mapMoving = true;
            while((timer += 6.25) < 500) {
                try {
                    switch(GameController.getPlayer().getLookAt()) {
                        case Player.UP:
                            mapOffsetY++;
                            break;
                            
                        case Player.DOWN:
                            mapOffsetY--;
                            break;
                            
                        case Player.RIGHT:
                            mapOffsetX--;
                            break;
                            
                        case Player.LEFT:
                            mapOffsetX++;
                            break;  
                    }
                    repaint();
                    Thread.sleep(6, 25);
                } catch (InterruptedException ex) {

                }
            }
            mapOffsetX = 0;
            mapOffsetY = 0;
            mapMoving = false;
            
            //update player position
            GameController.getPlayer().move();
            repaint();
            
            if( Math.round(Math.random()*100) < 10) { //10% chanche to start a new battle for each step
                backgroundMusic.stop();
                MainFrame.switchPanel(MainFrame.FROM_MAP_TO_RANDOM_BATTLE);
            }
        }
    
    }
    
    private class BreakAnimation implements Runnable {

        @Override
        public void run() {
            
            try {
                repaint();        
                int counter = 1000;
                //dim the screen
                while (mapDimOffset >= 0) {
                    mapDimOffset = (float) counter/1000;
                    counter--;

                    Thread.sleep(1);
                    repaint();
                }
                
                //stop background music and play break time
                backgroundMusic.stop();
                MapPanel.BREAK_SONG.play();
                
                //disable palyer for the brack sound time
                activePlayer = false;
                repaint();
                Thread.sleep(1800);
                activePlayer = true;
                repaint();
                
                //dim the screen again
                while (mapDimOffset <= 1) {
                    mapDimOffset = (float) counter/1000;
                    counter++;

                    Thread.sleep(1);
                    repaint();
                }
                
                //restart music
                backgroundMusic.start();
                addKeyListener(new PlayerListener());
            } catch (InterruptedException ex) {
                Logger.getLogger(MapPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    private class DrawDialog implements Runnable {
        private String textToDraw;
        private int charCounter;
        
        public DrawDialog(String pText) {
            this.textToDraw = pText;
            this.charCounter = 0;
        }
        
        @Override
        public void run() {
            try {
                
                while( (this.charCounter < this.textToDraw.length()) && (!currentDialogTerminated) ) {
                    dialogBox.setDialog(this.textToDraw.substring(0, this.charCounter), currentDialogTerminated);
                    this.charCounter++;
                    repaint();
                    Thread.sleep(100);
                }
                
                currentDialogTerminated = true;
                dialogBox.setDialog(this.textToDraw, currentDialogTerminated);
                repaint();
            
            } catch (InterruptedException ex) {
                Logger.getLogger(TutorialPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
