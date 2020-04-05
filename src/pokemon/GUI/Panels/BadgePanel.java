package pokemon.GUI.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import pokemon.GUI.EXPBar;
import pokemon.GUI.HPBar;
import pokemon.GUI.MainFrame;
import pokemon.logic.GameController;
import pokemon.logic.characters.Player;
import pokemon.utils.FontGenerator;
import pokemon.utils.Language;
import pokemon.utils.Resources;

/**
 * The class <code>BadgePanel</code> is the badge of the player.
 * This <code>JPanel</code> shows all the information about player.
 * 
 * @author Marco Russodivito
 */
public class BadgePanel extends JPanel {
    private static final Dimension BADGE_PANEL = new Dimension(1280, 720);
    
    private static final BufferedImage BACKGROUND_IMAGE = Resources.getImage("/resources/Panels/BadgePanel/BadgePanel - background.png");
    
    private static final BufferedImage PIACENTINO_MEDAL = Resources.getImage("/resources/Panels/BadgePanel/Medals/piacentino.png");
    private static final BufferedImage PALOMBA_MEDAL = Resources.getImage("/resources/Panels/BadgePanel/Medals/palomba.png");
    private static final BufferedImage OLIVETO_MEDAL = Resources.getImage("/resources/Panels/BadgePanel/Medals/oliveto.png");
    
    private static final Font FONT = FontGenerator.getPokemonFont(Font.PLAIN, 50);
    
    private static final Color FOREGROUND_TEXT = new Color(30 , 30 , 30);
    private static final Color SHADOW_TEXT = new Color(170, 170, 170);
    
    private static final String[] EXAM_TEXT = {"EXAMS", "ESAMI"};
    
    //player and bars to show information.
    private Player player;
    private HPBar hpBar;
    private EXPBar expBar;
    
    /**
     * Creates the <code>BadgePanel</code>.
     * Initialize all <code>BadgePanel</code>'s components.
     */
    public BadgePanel() {
        this.initBadgePanel();
        this.player = GameController.getPlayer();
        
        this.hpBar = new HPBar(115, 230, 330, 30, this.player);
        this.expBar = new EXPBar(115, 260, 420, 30, this.player);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //Background
        g.drawImage(BadgePanel.BACKGROUND_IMAGE, 0, 0, BadgePanel.BADGE_PANEL.width, BadgePanel.BADGE_PANEL.height, this);
        
        //Number
        FontMetrics fm = g.getFontMetrics(BadgePanel.FONT);
        g.setFont(BadgePanel.FONT);
        g.setColor(BadgePanel.SHADOW_TEXT);
        g.drawString("N. " + this.player.getNumber(), 107 + (512 - fm.stringWidth("N. " + player.getNumber()))/2 + 2, 85 + (45 + fm.getHeight())/2 + 2);
        g.setColor(BadgePanel.FOREGROUND_TEXT);
        g.drawString("N. " + this.player.getNumber(), 107 + (512 - fm.stringWidth("N. " + player.getNumber()))/2, 85 + (45 + fm.getHeight())/2);
        
        //Name
        g.setFont(BadgePanel.FONT);
        g.setColor(BadgePanel.SHADOW_TEXT);
        g.drawString(this.player.getName() + "      L. " + this.player.getLevel(), 140, 220);
        g.setColor(BadgePanel.FOREGROUND_TEXT);
        g.drawString(this.player.getName() + "      L. " + this.player.getLevel(), 140-2, 220-2);
        
        //hp bar and hp
        this.hpBar.draw(g, this);
        g.setFont(BadgePanel.FONT.deriveFont(40f));
        g.setColor(BadgePanel.SHADOW_TEXT);
        g.drawString(this.player.getHp()+ "/" + this.player.getMaxHp(), 455, 260);
        g.setColor(BadgePanel.FOREGROUND_TEXT);
        g.drawString(this.player.getHp()+ "/" + this.player.getMaxHp(), 455-2, 260-2);
        
        //expbar and exp 
        this.expBar.draw(g, this);
        g.setFont(BadgePanel.FONT.deriveFont(40f));
        g.setColor(BadgePanel.SHADOW_TEXT);
        g.drawString(this.player.getExp()+ "/" + this.player.getTotalExpToLevelUp(), 550, 290);
        g.setColor(BadgePanel.FOREGROUND_TEXT);
        g.drawString(this.player.getExp()+ "/" + this.player.getTotalExpToLevelUp(), 550-2, 290-2);
        
        //attack
        g.setFont(BadgePanel.FONT);
        g.setColor(BadgePanel.SHADOW_TEXT);
        g.drawString("ATK " + this.player.getAttack(), 140, 345);
        g.setColor(BadgePanel.FOREGROUND_TEXT);
        g.drawString("ATK " + this.player.getAttack(), 140-2, 345-2);
        //defense
        g.setFont(BadgePanel.FONT);
        g.setColor(BadgePanel.SHADOW_TEXT);
        g.drawString("DEF " + this.player.getDefense(), 140, 415);
        g.setColor(BadgePanel.FOREGROUND_TEXT);
        g.drawString("DEF " + this.player.getDefense(), 140-2, 415-2);
        //speed
        g.setFont(BadgePanel.FONT);
        g.setColor(BadgePanel.SHADOW_TEXT);
        g.drawString("SPD " + this.player.getSpeed(), 140, 490);
        g.setColor(BadgePanel.FOREGROUND_TEXT);
        g.drawString("SPD " + this.player.getSpeed(), 140-2, 490-2);
        
        //avatar
        this.player.drawFront(825, 180, 300, 300, g, this);
        
        
        //draw exams text
        g.setFont(BadgePanel.FONT.deriveFont(Font.BOLD));
        g.setColor(Color.WHITE);
        g.drawString(BadgePanel.EXAM_TEXT[Language.getLanguage()], 580, 540);
        
        //medals
        if (this.player.isPiacentinoDefeated()) {
            g.drawImage(BadgePanel.PIACENTINO_MEDAL, 481, 548, 63, 63, this);
        }
        if (this.player.isPalombaDefeated()) {
            g.drawImage(BadgePanel.PALOMBA_MEDAL, 609, 548, 63, 63, this);
        }
        if (this.player.isOlivetoDefeated()) {
            g.drawImage(BadgePanel.OLIVETO_MEDAL, 737, 548, 63, 63, this);
        }  
        
        //Border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, BadgePanel.BADGE_PANEL.width - 1, BadgePanel.BADGE_PANEL.height - 1);
    }
    
    private void initBadgePanel() {
        this.setSize(BadgePanel.BADGE_PANEL);
        this.setLayout(null);
        this.setFocusable(true);
        
        this.addKeyListener(new EscapeListener());
    }
    
    
    
    private static class EscapeListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){ //exit from badge panel 
                MainFrame.switchPanel(MainFrame.FROM_BADGE_TO_MAP);
            }
        }
    }
}
