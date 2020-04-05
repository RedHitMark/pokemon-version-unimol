package pokemon.GUI.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import pokemon.GUI.MainFrame;
import pokemon.GUI.PokeButton;
import pokemon.logic.GameController;
import pokemon.utils.Language;
import pokemon.utils.Resources;
import pokemon.utils.Save;
import pokemon.utils.SaveException;
import pokemon.utils.Sound;

/**
 * The <code>MainMenuPanel</code> class is main manu of the game.
 * You can chose to continue an existent save, to start a new game, to to in option menu or exit.
 * 
 * @author Marco Russodivito
 */
public class MainMenuPanel extends JPanel {
    private static final Dimension MAIN_MENU_PANEL_DIMENSION = new Dimension(1280, 720);
    
    private static final String[][] MAIN_MENU_BUTTONS_TEXT = {
        {"CONTINUE", "NEW GAME", "OPTION", "EXIT"},
        {"CONTINUA", "NUOVO GIOCO", "OPZIONI", "USCITA"},
    };
    private static final String[] SAVE_EXCEPTION_ERROR = {"A malitous user currupted the save! Start a new game to override the corrupted save.", "Un utente malizioso ha corroto il salvataggio! Inizia una nuova partita per sovrascrivere il salvataggio corrotto."}; 
    
    private static final int LOAD_GAME = 0;
    private static final int NEW_GAME = 1;
    private static final int OPTION = 2;
    private static final int EXIT = 3;
    
    private static final Color READY_BACKGROUND_COLOR = new Color(136, 144, 248);
    private static final Color FLASH_BACKGROUND_COLOR = Color.WHITE;
    
    private static final Sound CLICK_SOUND = Resources.getSound("/resources/Click.wav");
    
    private static final Dimension BUTTONS_DIMENSION = new Dimension(1180, 135);
    
    private int savePresentOffset;
    
    private PokeButton[] buttons;
    private int buttonSelected;
    
    
    private boolean ready;
    private Color backgroundColor;
    
    private Thread flashScreenThread;
    
    /**
     * Creates the <code>MainMenuPanel</code> and flash screen.
     * Initialize all <code>MainMenuPanel</code>'s components.
     */
    public MainMenuPanel() {
        this.initMainMenuPanel();
        
        this.savePresentOffset = (Save.isSaveLoadable())? 0 : 1;
        
        //init buttons
        this.initButtons();
        
        this.ready = false;
        
        this.backgroundColor = FLASH_BACKGROUND_COLOR;
        
        this.flashScreenThread = new Thread( new FlashScreen());
        this.flashScreenThread.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        //Background
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, MainMenuPanel.MAIN_MENU_PANEL_DIMENSION.width, MainMenuPanel.MAIN_MENU_PANEL_DIMENSION.height);
        
        //buttons
        if(this.ready) {
            for (PokeButton button : buttons) {
                button.draw(g, this);
            }
        }
        
        //Border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, MainMenuPanel.MAIN_MENU_PANEL_DIMENSION.width - 1, MainMenuPanel.MAIN_MENU_PANEL_DIMENSION.height - 1); 
    }
    
    private void initMainMenuPanel() {
        this.setSize(MainMenuPanel.MAIN_MENU_PANEL_DIMENSION);
        this.setLayout(null);
        this.setFocusable(true);
    }

    private void initButtons() {
        //decide how may button to init
        int temp = (this.savePresentOffset == 0)? 1 : 0;
        this.buttons = new PokeButton[3 + temp];
        
        //init buttons
        for (int i = 0; i < buttons.length; i++) {
            this.buttons[i] = new PokeButton(50, 5 + i*145, MainMenuPanel.BUTTONS_DIMENSION.width, MainMenuPanel.BUTTONS_DIMENSION.height, MainMenuPanel.MAIN_MENU_BUTTONS_TEXT[Language.getLanguage()][i + this.savePresentOffset]);
        }
        
        //default selection
        this.buttonSelected = 0;
        this.buttons[buttonSelected].select();
    }
    
    
    
    private class ButtonsListner extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            if( (buttonSelected > 0) && (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)) {
                buttons[buttonSelected].deselect();
                buttonSelected--;
                buttons[buttonSelected].select();
                repaint();
            }
            
            if( (buttonSelected < buttons.length - 1) && (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)) {
                buttons[buttonSelected].deselect();
                buttonSelected++;
                buttons[buttonSelected].select();
                repaint();
            }
            
            
            if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                removeKeyListener(this);
                switch (buttonSelected + savePresentOffset) {
                    
                    case MainMenuPanel.LOAD_GAME:
                        try {
                            CLICK_SOUND.play();
                            
                            GameController.loadGame();
                            
                            MainFrame.switchPanel(MainFrame.FROM_MAIN_MENU_TO_MAP);
                        } catch(SaveException ex) {
                            //this exception occurs if and olny if a maltios user modify the save when the game is running in this panel.
                            JOptionPane.showMessageDialog(null, SAVE_EXCEPTION_ERROR[Language.getLanguage()]);
                        }
                        break;
                        
                    case MainMenuPanel.NEW_GAME:
                        CLICK_SOUND.play();
                        
                        MainFrame.switchPanel(MainFrame.FROM_MAIN_MENU_TO_TUTORIAL_SCREEN);
                        break;
                        
                    case MainMenuPanel.OPTION:
                        CLICK_SOUND.play();
                        
                        MainFrame.switchPanel(MainFrame.FROM_MAIN_MENU_TO_OPTION_MENU);
                        break;
                        
                    case MainMenuPanel.EXIT:
                        CLICK_SOUND.play();
                        
                        System.exit(0);
                        break;
                }
            }
        }
    }
    
    
    
    private class FlashScreen implements Runnable {

        @Override
        public void run() {
            try {
                //wait some millisecond with white screen then show componets the panel
                backgroundColor = FLASH_BACKGROUND_COLOR;
                repaint();
                
                Thread.sleep(50);
                
                ready = true;
                backgroundColor = READY_BACKGROUND_COLOR;
                addKeyListener(new ButtonsListner());
                repaint();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
}
