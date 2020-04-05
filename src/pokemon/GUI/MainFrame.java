package pokemon.GUI;

import pokemon.GUI.Panels.BadgePanel;
import pokemon.GUI.Panels.MainMenuPanel;
import pokemon.GUI.Panels.StartScreenPanel;
import pokemon.GUI.Panels.TutorialPanel;
import pokemon.GUI.Panels.OptionMenuPanel;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pokemon.GUI.Panels.BattlePanel;
import pokemon.GUI.Panels.MapPanel;
import pokemon.logic.GameController;
import pokemon.utils.Resources;

/**
 * The <code>MainFrame</code> class is the game form.
 * This class provide methods to init and switch between <code>JPanel</code>s
 * 
 * @author Marco Russodivito
 */
public class MainFrame extends JFrame {
    /**
     * <code>MainFrame</code> Dimension. Best fit modern screens. Do not modify these values. 
     */
    public static final Dimension FRAME_DIMENSION = new Dimension(1280, 720);
    /**
     * The title of the game.
     */
    public static final String GAME_TITLE = "Pokémon - Version Unimol";
    /**
     * The icon of the game.
     */
    public static final BufferedImage GAME_ICON = Resources.getImage("/resources/Logo.png");
    /**
     * The cursor of the game.
     */
    public static final BufferedImage GAME_CURSOR = Resources.getImage("/resources/Logo.png");
    
    
    /**
     * <code>JPanel</code> coordinates. <code>JPanel</code>s are placed in the upper-left corner of <code>MainFrame</code>. 
     */
    public static final Point PANEL_LOCATION = new Point(0, 0);
    
    
    /**
     * Switch from <code>StartScreenPanel</code> to <code>MainMenuPanel</code>.
     */
    public static final int FROM_START_SCREEN_TO_MAIN_MENU = 1;
    /**
     * Switch from <code>MainMenuPanel</code> to <code>OptionMenuPanel</code>.
     */
    public static final int FROM_MAIN_MENU_TO_OPTION_MENU = 2;
    /**
     * Switch from <code>OptionMenuPanel</code> to <code>MainMenuPanel</code>.
     */
    public static final int FROM_OPTION_MENU_TO_MAIN_MENU = 3;
    /**
     * Switch from <code>MainMenuPanel</code> to <code>TutorialPanel</code>.
     */
    public static final int FROM_MAIN_MENU_TO_TUTORIAL_SCREEN = 4;
    /**
     * Switch from <code>TutorialPanel</code> to <code>MapPanel</code>.
     */
    public static final int FROM_TUTORIAL_TO_MAP = 5;
    /**
     * Switch from <code>MainMenuPanel</code> to <code>MapPanel</code>.
     */
    public static final int FROM_MAIN_MENU_TO_MAP = 6;
    /**
     * Switch from <code>MapPanel</code> to <code>BedgePanel</code>.
     */
    public static final int FROM_MAP_TO_BADGE = 7;
    /**
     * Switch from <code>BedgePanel</code> to <code>MapPanel</code>.
     */
    public static final int FROM_BADGE_TO_MAP = 8;
    /**
     * Switch from <code>MapPanel</code> to <code>OptionMenuPanel</code>.
     */
    public static final int FROM_MAP_TO_OPTION = 9;
    /**
     * Switch from <code>OptionMenuPanel</code> to <code>MapPanel</code>.
     */
    public static final int FROM_OPTION_MENU_TO_MAP = 10;
    /**
     * Switch from <code>MapPanel</code> to <code>BattlePanel</code> for a random Battle.
     */
    public static final int FROM_MAP_TO_RANDOM_BATTLE = 11;
    /**
     * Switch from <code>MapPanel</code> to <code>BattlePanel</code> for Piacentino Battle.
     */
    public static final int FROM_MAP_TO_PIACENTINO_BATTLE = 12;
    /**
     * Switch from <code>MapPanel</code> to <code>BattlePanel</code> for Palomba Battle.
     */
    public static final int FROM_MAP_TO_PALOMBA_BATTLE = 13;
    /**
     * Switch from <code>MapPanel</code> to <code>BattlePanel</code> for Oliveto Battle.
     */
    public static final int FROM_MAP_TO_OLIVETO_BATTLE = 14;
    /**
     * Switch from <code>BattlePanel</code> to <code>MapPanel</code>.
     */
    public static final int FROM_BATTLE_TO_MAP = 15;
    
    
    //MainFrame containter
    private static Container container;
    
    //JPanels of the game
    private static JPanel startScreenPanel, mainMenuPanel, optionMenuPanel, tutorialPanel, badgePanel, mapPanel, battlePanel;
    
    
    /**
     * Creates a new <code>MainFrame</code> and show the <code>StartScreenPanel</code>.
     */
    public MainFrame() {
        this.initMainFrame();
        
        startScreenPanel = new StartScreenPanel();
        initNewPanel(startScreenPanel);
    }
    
    /**
     * This method switches <code>JPanel</code>s. You must specify the status.
     * 
     * @param pStatus the status to switch
     */
    public static void switchPanel(int pStatus) {
        switch(pStatus){
            case MainFrame.FROM_START_SCREEN_TO_MAIN_MENU:                
                MainFrame.cleanAndDestroyExistentPanel(startScreenPanel);
                mainMenuPanel = new MainMenuPanel();
                MainFrame.initNewPanel(mainMenuPanel);
                break;
                
            case MainFrame.FROM_MAIN_MENU_TO_OPTION_MENU:
                MainFrame.cleanAndDestroyExistentPanel(mainMenuPanel);
                optionMenuPanel = new OptionMenuPanel(OptionMenuPanel.OPENED_FROM_MAIN_MENU);
                MainFrame.initNewPanel(optionMenuPanel);
                break;
            
            case MainFrame.FROM_OPTION_MENU_TO_MAIN_MENU:
                MainFrame.cleanAndDestroyExistentPanel(optionMenuPanel);
                mainMenuPanel = new MainMenuPanel();
                MainFrame.initNewPanel(mainMenuPanel);
                break; 
                
            case MainFrame.FROM_MAIN_MENU_TO_TUTORIAL_SCREEN:
                MainFrame.cleanAndDestroyExistentPanel(mainMenuPanel);
                tutorialPanel = new TutorialPanel();
                MainFrame.initNewPanel(tutorialPanel);
                break; 
            
            case MainFrame.FROM_TUTORIAL_TO_MAP:
                MainFrame.cleanAndDestroyExistentPanel(tutorialPanel);
                mapPanel = new MapPanel(false);//with menu no shown
                MainFrame.initNewPanel(mapPanel);
                break;
                
            case MainFrame.FROM_MAIN_MENU_TO_MAP:
                MainFrame.cleanAndDestroyExistentPanel(mainMenuPanel);
                mapPanel = new MapPanel(false);//with menu no shown
                MainFrame.initNewPanel(mapPanel);
                break;
                  
            case MainFrame.FROM_MAP_TO_BADGE:
                MainFrame.cleanAndDestroyExistentPanel(mapPanel);
                badgePanel = new BadgePanel();
                MainFrame.initNewPanel(badgePanel);
                break; 
                
            case MainFrame.FROM_BADGE_TO_MAP:
                MainFrame.cleanAndDestroyExistentPanel(badgePanel);
                mapPanel = new MapPanel(true);//with opened menu
                MainFrame.initNewPanel(mapPanel);
                break;
                
            case MainFrame.FROM_MAP_TO_OPTION:
                MainFrame.cleanAndDestroyExistentPanel(mapPanel);
                optionMenuPanel = new OptionMenuPanel(OptionMenuPanel.OPENED_FROM_MAP);
                MainFrame.initNewPanel(optionMenuPanel);
                break;
                
            case MainFrame.FROM_OPTION_MENU_TO_MAP:
                MainFrame.cleanAndDestroyExistentPanel(optionMenuPanel);
                mapPanel = new MapPanel(true);
                MainFrame.initNewPanel(mapPanel);
                break;
                
            case MainFrame.FROM_MAP_TO_RANDOM_BATTLE:
                MainFrame.cleanAndDestroyExistentPanel(mapPanel);
                battlePanel = new BattlePanel(GameController.randomBattle());
                MainFrame.initNewPanel(battlePanel);
                break;
                    
            case MainFrame.FROM_MAP_TO_PIACENTINO_BATTLE:
                MainFrame.cleanAndDestroyExistentPanel(mapPanel);
                battlePanel = new BattlePanel(GameController.startPiacentinoBattle());
                MainFrame.initNewPanel(battlePanel);
                break;
            
            case MainFrame.FROM_MAP_TO_PALOMBA_BATTLE:
                MainFrame.cleanAndDestroyExistentPanel(mapPanel);
                battlePanel = new BattlePanel(GameController.startPalombaBattle());
                MainFrame.initNewPanel(battlePanel);
                break;
                    
            case MainFrame.FROM_MAP_TO_OLIVETO_BATTLE:
                MainFrame.cleanAndDestroyExistentPanel(mapPanel);
                battlePanel = new BattlePanel(GameController.startsOlivetoBattle());
                MainFrame.initNewPanel(battlePanel);
                break;
                
            case MainFrame.FROM_BATTLE_TO_MAP:
                MainFrame.cleanAndDestroyExistentPanel(battlePanel);
                mapPanel = new MapPanel(false);
                MainFrame.initNewPanel(mapPanel);
                break;
        }
    }
    
    
    private void initMainFrame() {
        this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(MainFrame.GAME_CURSOR, new Point(0,0), "Pokemon Mouse Cursor"));
        Toolkit.getDefaultToolkit().getBestCursorSize(10, 10);
        
        this.setIconImage(MainFrame.GAME_ICON);
        this.setTitle(MainFrame.GAME_TITLE);
        this.setSize(MainFrame.FRAME_DIMENSION);
        this.setLocationRelativeTo(null);
        
        this.setLayout(null);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setUndecorated(true);
        
        container = this.getContentPane();
    }
    
    private static void initNewPanel(JPanel pPanel){
        pPanel.setLocation(MainFrame.PANEL_LOCATION);
        container.add(pPanel);
        pPanel.validate();
        pPanel.setVisible(true);
        pPanel.setFocusable(true);
        pPanel.requestFocusInWindow();
        pPanel.repaint();
    }
    private static void cleanAndDestroyExistentPanel(JPanel pPanel) {
        pPanel.invalidate();
        pPanel.setVisible(false);
        pPanel.removeAll();
        container.remove(pPanel);
        pPanel = null;
        //the GarbageCollecotr will destroy pPanel
    }
}
