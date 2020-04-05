package pokemon.GUI.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import pokemon.GUI.MainFrame;
import pokemon.GUI.PokeOptionBox;
import pokemon.utils.FontGenerator;
import pokemon.utils.LanguageException;
import pokemon.utils.Language;
import pokemon.utils.Resources;
import pokemon.utils.Sound;

/**
 * The <code>OptionMenuPanel</code> class is the option menu of the game.
 * You can change the <code>Language</code> and the <code>Sound</code> volume.
 * 
 * @author Marco Russodivito
 */
public class OptionMenuPanel extends JPanel {
    
    /**
     * <code>OptionMenuPanel</code> is opened from <code>MainMenuPanel</code>.
     */
    public static final int OPENED_FROM_MAIN_MENU = 0;
    /**
     * <code>OptionMenuPanel</code> is opened from <code>MapPanel</code>.
     */
    public static final int OPENED_FROM_MAP = 1;
    
    
    private static final Dimension OPTION_MENU_PANEL_DIMENSION = new Dimension(1280, 720);
    
    private static final BufferedImage BACKGROUND_IMAGE = Resources.getImage("/resources/Panels/OptionMenuPanel/OptionMenuPanel - background.png");
    
    private static final String[] OPTION_HEADER_TEXT = {"OPTION", "OPZIONI"};
    private static final Font OPTION_HEADER_STRING_FONT = FontGenerator.getPokemonFont(Font.PLAIN, 70);
    private static final Color OPTION_HEADER_FOREGROUND_COLOR = new Color(248, 176, 80);
    private static final Color OPTION_HEADER_SHADOW_COLOR = new Color(192, 120, 0);
    private static final Point OPTION_HEADER_STRING_LOCATION = new Point(140, 95);
    
    
    private static final String[][] OPTION_TEXT = {
        {"LANGUAGE", "SOUND", "BACK"},
        {"LINGUA", "SUONO", "INDIETRO"},
    };
    private static final String[][] LANGUAGE_OPTIONS = {
        {"English", "Italian"},
        {"Inglese", "Italiano"},
    };
    private static final String[][] SOUND_OPTIONS = {
        {"Off", "Low", "Medium", "High"},
        {"Off", "Basso", "Medio", "Alto"},
    };
    
    private static final int LANGUAGE = 0;
    private static final int SOUND = 1;
    private static final int BACK = 2; 
    
    private static final Sound CLICK_SOUND = Resources.getSound("/resources/Click.wav");
    
    private PokeOptionBox[] optionBoxes;
    private int boxSelected;
    private final int openedFrom;
    
    /**
     * Creates the <code>OptionMenuPanel</code>.
     * Initialize all <code>OptionMenuPanel</code>'s components.
     * 
     * @param pOpenedFrom the opening provenienze. Should be <code>OPENED_FROM_MAIN_MENU</code> or <code>OPENED_FROM_MAP</code> otherwise you cannot exit from this panel.
     */
    public OptionMenuPanel(int pOpenedFrom) {
        this.initOptionMenuPanel();
        
        this.openedFrom = pOpenedFrom;
        
        this.initBoxes();
    }
    
    private void initOptionMenuPanel() {
        this.setSize(OptionMenuPanel.OPTION_MENU_PANEL_DIMENSION);
        this.setLayout(null);
        this.setFocusable(true);
    }
    private void initBoxes() {
        this.optionBoxes = new PokeOptionBox[3];
        
        for (int i = 0; i < this.optionBoxes.length ; i++) {
           this.optionBoxes[i] = new PokeOptionBox(87, 182 + 100*i, 1106, 100); 
        }
        
        this.boxSelected = 0;
        this.optionBoxes[this.boxSelected].select();
        
        this.addKeyListener(new OptionBoxesListener());
    }
    private void updateSetting() {
        try {
            //update language
            Language.setLanguage(this.optionBoxes[OptionMenuPanel.LANGUAGE].getOptionChoise());
            
            //update sound settings
            switch(this.optionBoxes[OptionMenuPanel.SOUND].getOptionChoise()) {
                case 0:
                    Sound.setVolume(Sound.MUTE);
                    break;

                case 1:
                    Sound.setVolume(Sound.LOW);
                    break;

                case 2:
                    Sound.setVolume(Sound.MEDIUM);
                    break;

                case 3:
                    Sound.setVolume(Sound.HIGH);
                    break;
            }
        } catch (LanguageException ex) {
            //no exception -> constant value
        }
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        //Background
        g.drawImage(OptionMenuPanel.BACKGROUND_IMAGE, 0, 0, OptionMenuPanel.OPTION_MENU_PANEL_DIMENSION.width, OptionMenuPanel.OPTION_MENU_PANEL_DIMENSION.height, this);
  
        
        //Header
        g.setFont(OptionMenuPanel.OPTION_HEADER_STRING_FONT);
        g.setColor(OptionMenuPanel.OPTION_HEADER_SHADOW_COLOR);
        g.drawString(OptionMenuPanel.OPTION_HEADER_TEXT[Language.getLanguage()], OptionMenuPanel.OPTION_HEADER_STRING_LOCATION.x + 2, OptionMenuPanel.OPTION_HEADER_STRING_LOCATION.y + 2);
        g.setColor(OptionMenuPanel.OPTION_HEADER_FOREGROUND_COLOR);
        g.drawString(OptionMenuPanel.OPTION_HEADER_TEXT[Language.getLanguage()], OptionMenuPanel.OPTION_HEADER_STRING_LOCATION.x, OptionMenuPanel.OPTION_HEADER_STRING_LOCATION.y);
        
        //language settings
        this.optionBoxes[OptionMenuPanel.LANGUAGE].setText(OptionMenuPanel.OPTION_TEXT[Language.getLanguage()][OptionMenuPanel.LANGUAGE]);
        this.optionBoxes[OptionMenuPanel.LANGUAGE].setOptions(OptionMenuPanel.LANGUAGE_OPTIONS[Language.getLanguage()], Language.getLanguage());
        this.optionBoxes[OptionMenuPanel.LANGUAGE].draw(g, this);
        
        //sound settings
        this.optionBoxes[OptionMenuPanel.SOUND].setText(OptionMenuPanel.OPTION_TEXT[Language.getLanguage()][OptionMenuPanel.SOUND]);
        this.optionBoxes[OptionMenuPanel.SOUND].setOptions(OptionMenuPanel.SOUND_OPTIONS[Language.getLanguage()], Sound.getVolume());
        this.optionBoxes[OptionMenuPanel.SOUND].draw(g, this);
        
        //back button
        this.optionBoxes[OptionMenuPanel.BACK].setText(OptionMenuPanel.OPTION_TEXT[Language.getLanguage()][OptionMenuPanel.BACK]);
        this.optionBoxes[OptionMenuPanel.BACK].draw(g, this);
        
        //Border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, OptionMenuPanel.OPTION_MENU_PANEL_DIMENSION.width - 1, OptionMenuPanel.OPTION_MENU_PANEL_DIMENSION.height - 1);
    }
    
    
    
    private class OptionBoxesListener extends KeyAdapter {
        
        @Override
        public void keyReleased(KeyEvent e) {

            if( (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && (boxSelected > 0) ) {
                optionBoxes[boxSelected].deselect();
                boxSelected--;
                optionBoxes[boxSelected].select();
                repaint();
            }  
            if( (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && (boxSelected < optionBoxes.length - 1) ) {
                optionBoxes[boxSelected].deselect();
                boxSelected++;
                optionBoxes[boxSelected].select();
                repaint();
            }
            
            if( e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A ) {
                optionBoxes[boxSelected].previousOption();
                updateSetting();
                repaint();
            }
            if( e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D ) {
                optionBoxes[boxSelected].nextOption();
                updateSetting();
                repaint();
            }
            
            if ( (e.getKeyCode() == KeyEvent.VK_ESCAPE) ) {
                
                OptionMenuPanel.CLICK_SOUND.play();
                
                if (openedFrom == OptionMenuPanel.OPENED_FROM_MAIN_MENU) {
                    MainFrame.switchPanel(MainFrame.FROM_OPTION_MENU_TO_MAIN_MENU);
                } else if (openedFrom == OptionMenuPanel.OPENED_FROM_MAP) {
                    MainFrame.switchPanel(MainFrame.FROM_OPTION_MENU_TO_MAP);
                }  
                
            }
            
            if ( (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) && (boxSelected == OptionMenuPanel.BACK) ) {
                
                OptionMenuPanel.CLICK_SOUND.play();
                
                if (openedFrom == OptionMenuPanel.OPENED_FROM_MAIN_MENU) {
                    MainFrame.switchPanel(MainFrame.FROM_OPTION_MENU_TO_MAIN_MENU);
                } else if (openedFrom == OptionMenuPanel.OPENED_FROM_MAP) {
                    MainFrame.switchPanel(MainFrame.FROM_OPTION_MENU_TO_MAP);
                }
                
            }
        }   
    }
}
