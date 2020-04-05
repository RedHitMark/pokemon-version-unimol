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
import pokemon.GUI.Capobianco;
import pokemon.GUI.MainFrame;
import pokemon.GUI.NameForm;
import pokemon.GUI.VerticalMenu;
import pokemon.logic.GameController;
import pokemon.logic.exceptions.PokemonException;
import pokemon.utils.Language;
import pokemon.utils.Resources;
import pokemon.utils.Sound;

/**
 * The <code>TutorialPanel</code> class is the tutorial of the game.
 * At first, there is <code>IntroDimAnimation</code>, then Capobianco start to explain game.
 * Then you can chosee type and name of player.
 * At the end there is <code>OutroDimAnimation</code>.
 * 
 * @author Marco Russodivito
 */
public class TutorialPanel extends JPanel {
    
    private static final Dimension TUTORIAL_PANEL_DIMENSION = new Dimension(1280, 720);
    
    
    public static final BufferedImage BACKGROUND_IMAGE = Resources.getImage("/resources/Panels/TutorialPanel/TutorialPanel - background.png");
    
    
    private static final Point DIALOG_BOX_LOCATION = new Point(5, 535);
    private static final Dimension DIALOG_BOX_DIMENSION = new Dimension(1270, 180);
    private static final String[][] FIRST_PART_DIALOGS = {
        {"Hi, Sorry to keep you waiting.", "Welcome in the University of the Studies of Molise.", "I am the prof. John HeadWhite", "But they named me WhiteBoss", "This university is populated by strange selvatic creatures called STUDENTS", "Many of them train themself to pass exams fighting against teachers", "Now, let's speak about you!", "Who you are?"},
        {"Ciao, scusa se ti ho fatto attendere.", "Benvenuto all'Università degli Studi del Molise.", "Sono il prof. Giuvann Capbianc", "Ma tutti mi chiamano Gio'Ca", "Quest'università è popolata da strane crature selvatiche chiamate STUDENTI", "Molti di loro si allenano tra loro per superare gli esami combattendo contro i professori", "Adesso parlami di te!", "Chi sei?"},
    };
    private static final String[][] SECOND_PART_DIALOGS = {
        {", you have just arrived in Pesche", "Let me teach your first attack", "WhiteBoss use THROWING PEN", "You have just learned THROWING PEN", "Your personal university career is getting start", "Good luck"},
        {", sei appena arrivato a Pesche", "Lascia che ti insegni il tuo primo attaco", "Gio'Ca usa LANCIO PENNARELLO", "Hai appena imparato LANCIO PENNARELLO", "La tua personale carriera universitaria sta per iniziare", "Buona Fortuna"},
    };
    
    
    private static final Point CAPOBIANCO_LOCATION = new Point(440,80);
    private static final Dimension CAPOBIANCO_DIMENSION = new Dimension(270,330 );
    
    
    private static final Point TYPE_MENU_LOCATION = new Point(920, 10);
    private static final Dimension TYPE_MENU_DIMENSION = new Dimension(350, 415);
    private static final String[][] TYPE_TEXT = {
        {"Fire", "Water", "Grass"},
        {"Fuoco", "Acqua", "Erba"}
    };
    private static final String[][] TYPE_EXPLANATION_TEXTS = {
        {"You are fast and aggressive", "You are slow but resistent", "You are a freelance"},
        {"Sei aggressivo e veloce", "Sei lento ma resistente", "Sei un tuttofare"}
    };
    
    private static final BufferedImage[] TYPE_IMAGE = {Resources.getImage("/resources/Player/Fire/front.png"), Resources.getImage("/resources/Player/Water/front.png"), Resources.getImage("/resources/Player/Grass/front.png")};

    
    public static final Sound TUTORIAL_SONG = Resources.getSound("/resources/Panels/TutorialPanel/TutorialPanel - sound.wav");
    
    //use for generate a new player
    private String newPlayerName;
    private int newPlayerType;
    
    //dialog box and flag
    private DialogBox dialogBox;
    private boolean activeDialogBox;
    
    //use to move in the dialogs
    boolean currentDialogTerminated;
    
    //capobianco and flag
    private Capobianco capobianco;
    private boolean activeCapobianco;
    
    //typeMenu and flag
    private VerticalMenu typeMenu;
    private boolean activeTypeMenu;
    
    //name forma and flag
    private NameForm nameForm;
    private boolean activeNameForm;
    
    //background music
    private Clip backgroundMusic;
    
    //use for intro and outro dim animation
    private Thread introThread;
    private Thread outroThread;
    private float dimOffset;
    
    
    /**
     * Create the <code>TutorialPanel</code>.
     * Initialize all <code>TutorialPanel</code>'s components.
     */
    public TutorialPanel() {
        this.initTutorialPanel();
        
        //init dialog box
        this.activeDialogBox = false;
        this.dialogBox = new DialogBox(TutorialPanel.DIALOG_BOX_LOCATION.x, TutorialPanel.DIALOG_BOX_LOCATION.y, TutorialPanel.DIALOG_BOX_DIMENSION.width, TutorialPanel.DIALOG_BOX_DIMENSION.height);
        
        this.currentDialogTerminated = false;
        
        //init capobianco
        this.capobianco = new Capobianco(TutorialPanel.CAPOBIANCO_LOCATION.x, TutorialPanel.CAPOBIANCO_LOCATION.y, TutorialPanel.CAPOBIANCO_DIMENSION.width, TutorialPanel.CAPOBIANCO_DIMENSION.height);
        this.activeCapobianco = false;
        
        //init menu
        this.typeMenu = new VerticalMenu(TutorialPanel.TYPE_MENU_LOCATION.x, TutorialPanel.TYPE_MENU_LOCATION.y, TutorialPanel.TYPE_MENU_DIMENSION.width, TutorialPanel.TYPE_MENU_DIMENSION.height, TutorialPanel.TYPE_TEXT[Language.getLanguage()]);
        this.activeTypeMenu = false;
        
        //init the name form
        this.nameForm = new NameForm();
        this.activeNameForm = false;
        
        this.backgroundMusic = TutorialPanel.TUTORIAL_SONG.loop();
        
        this.dimOffset = 0;
        this.introThread = new Thread(new IntroDimAnimation());
        this.introThread.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        //Background dimmable
        BufferedImage dimmableBackgroundImage = new RescaleOp(this.dimOffset , 0, null).filter(TutorialPanel.BACKGROUND_IMAGE, null);
        g.drawImage(dimmableBackgroundImage, 0, 0, TutorialPanel.TUTORIAL_PANEL_DIMENSION.width, TutorialPanel.TUTORIAL_PANEL_DIMENSION.height, this);
        
        //capobianco
        if(this.activeCapobianco) {
            this.capobianco.draw(g, this);
        }
        
        //type menu
        if(this.activeTypeMenu) {
            this.typeMenu.draw(g, this);
            g.drawImage(TutorialPanel.TYPE_IMAGE[this.typeMenu.getEntrySelected()], 470,100,342,342, this);
        }
        
        //dialog box
        if(this.activeDialogBox) {
            this.dialogBox.draw(g, this);
        }
        
        //name form
        if(this.activeNameForm) {
            this.nameForm.draw(g, this);
        }
        
        //Border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, TutorialPanel.TUTORIAL_PANEL_DIMENSION.width - 1, TutorialPanel.TUTORIAL_PANEL_DIMENSION.height - 1);
    }
    
    private void initTutorialPanel() {
        this.setSize(TutorialPanel.TUTORIAL_PANEL_DIMENSION);
        this.setFocusable(true);
        this.setLayout(null);
    }
    
    
    private class FirstPartDialogBoxListener extends KeyAdapter {
        private int currentDialog;
        
        public FirstPartDialogBoxListener() {
            this.currentDialog = 0;
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            
            //when you press space you can draw whole current dialog, pass to the next dialog, or start selection type
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                if(!currentDialogTerminated) { // draw whole current dialog
                    currentDialogTerminated = true;
                    repaint();
                } else if(currentDialog < (FIRST_PART_DIALOGS[Language.getLanguage()].length - 1)) { //pass to next dialog
                    currentDialog++;
                    currentDialogTerminated = false;
                    Thread drawThread = new Thread(new DrawDialog(FIRST_PART_DIALOGS[Language.getLanguage()][currentDialog]));
                    drawThread.start();
                } else { // start selection type 
                    removeKeyListener(this);
                    activeCapobianco = false;
                    
                    activeTypeMenu = true;
                    dialogBox.setDialog(TYPE_EXPLANATION_TEXTS[Language.getLanguage()][typeMenu.getEntrySelected()], false);
                    
                    addKeyListener(new TypeListener());
                    repaint();
                    //start selection type
                }
            }
        }
    }
    
    private class TypeListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) { // change selection and change text
            
            if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                typeMenu.previousEntry();
                dialogBox.setDialog(TutorialPanel.TYPE_EXPLANATION_TEXTS[Language.getLanguage()][typeMenu.getEntrySelected()], false);
                repaint();
            }
            
            if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                typeMenu.nextEntry();
                dialogBox.setDialog(TutorialPanel.TYPE_EXPLANATION_TEXTS[Language.getLanguage()][typeMenu.getEntrySelected()], false);
                repaint();
            }
            
            if(e.getKeyCode() == KeyEvent.VK_SPACE) { // pass to name form
                removeKeyListener(this);
                activeTypeMenu = false;
                //memorize the type
                newPlayerType = typeMenu.getEntrySelected();
                
                activeNameForm = true;
                try {
                    nameForm.setPlayerType(typeMenu.getEntrySelected());
                } catch (PokemonException ex) {
                    //constant value
                }
                addKeyListener(new NameListener());
                
                repaint();
            }
        }
    }
    
    private class NameListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                nameForm.moveSelector(NameForm.UP);
                repaint();
            }
            
            if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                nameForm.moveSelector(NameForm.DOWN);
                repaint();
            }
            
            if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                nameForm.moveSelector(NameForm.RIGHT);
                repaint();
            }
            
            if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                nameForm.moveSelector(NameForm.LEFT);
                repaint();
            }
            
            if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                nameForm.deleteLetter();
                repaint();
            }
            
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                nameForm.typeLetter();
                repaint();
            }
            
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                removeKeyListener(this);
                activeNameForm = false;
                //memorize name
                newPlayerName = nameForm.getName();
                
                activeCapobianco = true;
                activeDialogBox = true;
                
                //start the drawing string thread
                currentDialogTerminated = false;
                addKeyListener(new SecondPartDialogBoxListener());
                Thread drawThread = new Thread(new DrawDialog(newPlayerName + TutorialPanel.SECOND_PART_DIALOGS[Language.getLanguage()][0]));
                drawThread.start();
                
                repaint();
            }
        }
    }
    
    private class SecondPartDialogBoxListener extends KeyAdapter {
        private int currentDialog;
        
        public SecondPartDialogBoxListener() {
            this.currentDialog = 0;
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            if(!currentDialogTerminated) { //terminate the dialog
                currentDialogTerminated = true;
                repaint();
            } else if(this.currentDialog < (TutorialPanel.SECOND_PART_DIALOGS[Language.getLanguage()].length - 1)) { // move to next dialog
                
                if(this.currentDialog == 1) { // capobianco animation
                    Thread capobiancoThread = new Thread(new CapobiancoThrowPen());
                    capobiancoThread.start();
                }
                
                this.currentDialog++;
                currentDialogTerminated = false;
                
                Thread drawThread = new Thread(new DrawDialog(TutorialPanel.SECOND_PART_DIALOGS[Language.getLanguage()][this.currentDialog]));
                drawThread.start();
            } else { //dimm screen and start the game
                
                removeKeyListener(this);
                activeCapobianco = false;
                activeDialogBox = false;
                GameController.newGame(newPlayerName, newPlayerType);
                
                outroThread = new Thread( new OutroDimAnimation());
                outroThread.start();
            }
        }
    }
    
    
    
    private class IntroDimAnimation implements Runnable {

        @Override
        public void run() {
            //first dimm the background then show capobianco and after 1 second the dialogbox
            int counter = 0;
            
            try {
                
                while (dimOffset <= 1) {
                    dimOffset = (float) counter/1000;
                    counter++;

                    Thread.sleep(2);
                    repaint();
                }

                activeCapobianco = true;
                repaint();
                
                Thread.sleep(1000);

                activeDialogBox = true;
                repaint();
                
                addKeyListener(new FirstPartDialogBoxListener());
                Thread th = new Thread(new DrawDialog(TutorialPanel.FIRST_PART_DIALOGS[Language.getLanguage()][0]));
                th.start();
            
            } catch (InterruptedException ex) {
                    Logger.getLogger(TutorialPanel.class.getName()).log(Level.SEVERE, null, ex);
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
                    charCounter++;
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
    
    private class CapobiancoThrowPen implements Runnable {

        @Override
        public void run() {
            capobianco.moveArm();
            repaint();
            
            int timercounter = 0;
            while(timercounter <= 1800) {
                try {
                    capobianco.movePen();
                    repaint();
                    
                    Thread.sleep(7);
                    timercounter += 7;
                } catch (InterruptedException ex) {
                    Logger.getLogger(TutorialPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            capobianco.explodePen();
            repaint();
            
            timercounter = 0;
            while(timercounter <= 500) {
                try {
                    capobianco.switchExplosionStatus();
                    repaint();
                    
                    Thread.sleep(100);
                    timercounter += 50;
                } catch (InterruptedException ex) {
                    Logger.getLogger(TutorialPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            capobianco.stopExplosion();
            repaint();
        }
        
    }
    
    private class OutroDimAnimation implements Runnable {

        @Override
        public void run() {
            int counter = 1000;
            
            while (dimOffset >= 0) {
                dimOffset = (float) counter/1000;
                counter--;
                try {
                    Thread.sleep(2);
                    repaint();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TutorialPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            backgroundMusic.stop();
            MainFrame.switchPanel(MainFrame.FROM_TUTORIAL_TO_MAP);
        }

    }
}
