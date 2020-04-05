package pokemon.GUI.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import pokemon.GUI.MainFrame;
import pokemon.utils.FontGenerator;
import pokemon.utils.Resources;
import pokemon.utils.Sound;

/**
 * The <code>StartScreenPanel</code> class is the start screen of the game.
 * At first, there is <code>IntroAnimation</code>, then you can press space to start the game
 * 
 * @author Marco Russodivito
 */
public class StartScreenPanel extends JPanel {
    
    private static final Dimension START_SCREEN_PANEL_DIMENSION = new Dimension(1280, 720);

    private static final BufferedImage BACKGROUND_IMAGE = Resources.getImage("/resources/Panels/StartScreenPanel/StartScreenPanel - background.png");
    private static final int INITIAL_GLOWING_EFFECT_OFFSET= 50;
    
    private static final BufferedImage PRESS_SPACE_IMAGE = Resources.getImage("/resources/Panels/StartScreenPanel/StartScreenPanel - pressSpace.png");
    
    private static final BufferedImage CLOUD_IMAGE = Resources.getImage("/resources/Panels/StartScreenPanel/StartScreenPanel - cloud.png");
    private static final Dimension CLOUD_IMAGE_DIMENSION = new Dimension(3835, 720);
    private static final Point INITIAL_CLOUD_LOCATION = new Point(0, 0);
    
    private static final BufferedImage POKEMON_LOGO_IMAGE = Resources.getImage("/resources/Panels/StartScreenPanel/StartScreenPanel - pokemon logo.png");
    private static final Point INITIAL_POKEMON_LOGO_LOCATION = new Point(0, 200);
    
    private static final BufferedImage GAME_LOGO_IMAGE = Resources.getImage("/resources/Panels/StartScreenPanel/StartScreenPanel - game logo.png");
    private static final Point INITIAL_GAME_LOGO_LOCATION = new Point(0, -100);
    
    private static final String CREDIT_STRING = "Marco Russodivito - 158940";
    private static final Font CREDIT_FONT = FontGenerator.getPokemonFont(Font.PLAIN, 32);
    private static final Color CREDIT_FOREGROUND_COLOR = Color.WHITE;
    private static final Color CREDIT_SHADOW_COLOR = Color.BLACK;
    private static final Point CREDIT_LOCATION = new Point(500, 710);
    
    
    private static final Sound START_SCREEN_SONG = Resources.getSound("/resources/Panels/StartScreenPanel/StartScreenPanel - sound.wav");
    
    
    
    private Color backgroundColor;
    private int backgroundGlowingEffectOffset;
    private boolean activateBackgroundImage;
    
    
    private Point pokemonLogoLocation;
    private boolean activePokemonLogo;
    
    
    private Point gameLogoLocation;
    private boolean activeGameLogo;
    
    
    private Point cloudLocation;
    private boolean activateCloud;
    
    
    private boolean activePressSpaceImage;
    private boolean startPressed;
    
    
    private boolean activeCredits;
    
    
    private Clip backgroundMusic;
    
    
    private Thread introThread;
    
    /**
     * Creates the <code>StartScreenPanel</code> and start the animation.
     * Initialize all <code>StartScreenPanel</code>'s components.
     */
    public StartScreenPanel() {
        this.initStartScreenPanel();
        
        //init background color and glowing effect
        this.backgroundColor = Color.BLACK;
        this.backgroundGlowingEffectOffset = StartScreenPanel.INITIAL_GLOWING_EFFECT_OFFSET;
        this.activateBackgroundImage = false;
        
        //init pokemon logo location
        this.pokemonLogoLocation = StartScreenPanel.INITIAL_POKEMON_LOGO_LOCATION;
        this.activePokemonLogo = false;
        
        //init pokemon logo location
        this.gameLogoLocation = StartScreenPanel.INITIAL_GAME_LOGO_LOCATION;
        this.activeGameLogo = false;
        
        //init cloud
        this.cloudLocation = StartScreenPanel.INITIAL_CLOUD_LOCATION;
        this.activateCloud = false;
        
        //init pressStartLogo
        this.activePressSpaceImage = false;
        this.startPressed = false;
        
        //init creditWrite
        this.activeCredits = false;
        

        //start background music
        this.backgroundMusic = StartScreenPanel.START_SCREEN_SONG.loop();
        
        //start thread for initial animation
        this.introThread = new Thread(new IntroAnimation());
        this.introThread.start();
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        //Flashing background or glowing background image 
        if (!this.activateBackgroundImage) {
            g.setColor(this.backgroundColor);
            g.fillRect(0, 0, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.width, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.height);
        } else {
            Color glowingColor = new Color(this.backgroundGlowingEffectOffset, this.backgroundGlowingEffectOffset, 0);
            g.drawImage(BACKGROUND_IMAGE, 0, 0, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.width, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.height, glowingColor, this);
        }
        
        //Cloud
        if (this.activateCloud) {
            g.drawImage(StartScreenPanel.CLOUD_IMAGE, this.cloudLocation.x, this.cloudLocation.y, StartScreenPanel.CLOUD_IMAGE_DIMENSION.width, StartScreenPanel.CLOUD_IMAGE_DIMENSION.height, this);
        }
        
        //Pokemon Logo
        if (this.activePokemonLogo) {
            g.drawImage(StartScreenPanel.POKEMON_LOGO_IMAGE, this.pokemonLogoLocation.x, this.pokemonLogoLocation.y, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.width, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.height, this);
        }
        
        //Game logo
        if (this.activeGameLogo) {
            g.drawImage(StartScreenPanel.GAME_LOGO_IMAGE, this.gameLogoLocation.x, this.gameLogoLocation.y, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.width, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.height, this);
        }
         
        if (this.activePressSpaceImage){
            g.drawImage(StartScreenPanel.PRESS_SPACE_IMAGE, 0, 0, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.width, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.height, this);
        }
        
        //Credit, my name and my numberID
        if (this.activeCredits) {
            g.setColor(StartScreenPanel.CREDIT_SHADOW_COLOR);
            g.setFont(StartScreenPanel.CREDIT_FONT);
            g.drawString(StartScreenPanel.CREDIT_STRING, StartScreenPanel.CREDIT_LOCATION.x + 2, StartScreenPanel.CREDIT_LOCATION.y + 2);
            g.setColor(StartScreenPanel.CREDIT_FOREGROUND_COLOR);
            g.setFont(StartScreenPanel.CREDIT_FONT);
            g.drawString(StartScreenPanel.CREDIT_STRING, StartScreenPanel.CREDIT_LOCATION.x, StartScreenPanel.CREDIT_LOCATION.y);
        }
        
        //Border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.width - 1, StartScreenPanel.START_SCREEN_PANEL_DIMENSION.height - 1);
    }
    
    private void initStartScreenPanel() {
        this.setSize(StartScreenPanel.START_SCREEN_PANEL_DIMENSION);
        this.setLayout(null);
        this.setFocusable(true);
    }
    
    
    
    
    
    private class SpaceKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                startPressed = true;
                backgroundMusic.stop();
                
                MainFrame.switchPanel(MainFrame.FROM_START_SCREEN_TO_MAIN_MENU);
            }
        }
    }
    
    
    
    private class IntroAnimation implements Runnable {

        @Override
        public void run() {
            
            try {
                //wait black screen one second
                Thread.sleep(1000);
                
                //active pokemon logo
                activePokemonLogo = true;
                repaint();
                
                //flash background at music rhytm
                //first flash
                backgroundColor = Color.BLACK;
                repaint();
                Thread.sleep(1000);
                backgroundColor = Color.WHITE;
                repaint();
                Thread.sleep(200);
                //second flash
                backgroundColor = Color.BLACK;
                repaint();
                Thread.sleep(2000);
                backgroundColor = Color.WHITE;
                repaint();
                Thread.sleep(200);
                //stop flashing
                backgroundColor = Color.BLACK;
                
                // move the pokemon logo
                while( pokemonLogoLocation.y >= 0 ) {
                    pokemonLogoLocation.translate(0, -2);
                    repaint();
                    Thread.sleep(5);
                }
                
                //active the game logo
                activeGameLogo = true;
                repaint();
                
                //move game logo
                while( gameLogoLocation.y <= 0 ) {
                    gameLogoLocation.translate(0, 2);
                    repaint();
                    Thread.sleep(5);
                }
                
                //wait one second, then flash the screen
                Thread.sleep(1000);
                backgroundColor = Color.WHITE;
                repaint();
                Thread.sleep(50);
                
                //activate background image, cloud, pressSpace and credits and start Threads
                activateBackgroundImage = true;
                Thread backgroundGlowingEffect = new Thread(new Glowing());
                backgroundGlowingEffect.start();
                repaint();

                activateCloud = true;
                Thread cloudMovingThread = new Thread(new CloudMoving());
                cloudMovingThread.start();
                repaint();

                activePressSpaceImage = true;
                Thread intermitentPressSpaceImage = new Thread(new IntermitentWriting());
                intermitentPressSpaceImage.start();
                repaint();

                activeCredits = true;
                repaint();
                
                //add key listener
                addKeyListener(new SpaceKeyListener());
                
            } catch (InterruptedException ex) {
                Logger.getLogger(StartScreenPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private class Glowing implements Runnable {
  
        @Override
        public void run() {
            
            try {
                
                int add = 0;
                
                while (!startPressed) {
                    if (backgroundGlowingEffectOffset <= 50) {
                        add = 1;
                        Thread.sleep(1000);
                    } else if (backgroundGlowingEffectOffset >= 255) {
                        add = -1;
                        Thread.sleep(1000);
                    }    
                    backgroundGlowingEffectOffset += add;
                    Thread.sleep(10);
                    repaint();
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(StartScreenPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class CloudMoving implements Runnable {

        @Override
        public void run() {
            
            try{
                int addY = 0;
                int counter = 0;
                
                while (!startPressed) {
                    cloudLocation.x = (cloudLocation.x <= -2555)? 0 : cloudLocation.x - 2;

                    if( counter == 5) {
                        if(cloudLocation.y <= 0) {
                            addY = 1;
                        } else if (cloudLocation.y >= 3){
                            addY = -1;
                        }
                        cloudLocation.y += addY;
                        counter = 0;
                    } else {
                        counter++;
                    }    
                    Thread.sleep(50);
                    repaint();
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(StartScreenPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
    
    private class IntermitentWriting implements Runnable {
  
        @Override
        public void run() { 
            try {

                while (!startPressed) {
                    activePressSpaceImage = !activePressSpaceImage;
                    
                    Thread.sleep(500);
                    repaint();
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(StartScreenPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
