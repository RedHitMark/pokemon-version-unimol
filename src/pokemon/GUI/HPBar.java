package pokemon.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import pokemon.logic.characters.Pokemon;
import pokemon.utils.Language;
import pokemon.utils.Resources;

/**
 * The class <code>HPBar</code> is an hp bar to show the life of a <code>Pokemon</code>. 
 * 
 * @author Marco Russodivito
 */
public class HPBar extends Rectangle {
    private static final int FULL_HP = 48; 
    private static final int MEDIUM_HP = 28;
    private static final int LOW_HP = 10;
    private static final int NO_HP = 0;
    
    //different bars for different language
    private static final BufferedImage[] HP_BAR_IMAGES = {Resources.getImage("/resources/HPBar/HPBar_0.png"), Resources.getImage("/resources/HPBar/HPBar_1.png")};
    
    //colors
    private static final Color HIGH_HP_UPPER_COLOR = new Color(88, 208, 128);
    private static final Color HIGH_HP_DOWN_COLOR = new Color(112, 248, 168);
    private static final Color MEDIUM_HP_UPPER_COLOR = new Color(200, 168, 8);
    private static final Color MEDIUM_HP_DOWN_COLOR = new Color(248, 224, 56);
    private static final Color LOW_HP_UPPER_COLOR = new Color(168, 64, 72);
    private static final Color LOW_HP_DOWN_COLOR = new Color(248, 88, 56);
    
    private int maxHP;
    private int hp
;    
    /**
     * Creates an <code>HPBar</code>.
     * Keep 11:1 pixel proporsion for visual reason.
     * 
     * @param pX the X coordinate of the upper-left corner of the <code>HPBar</code>.
     * @param pY the Y coordinate of the upper-left corner of the <code>HPBar</code>.
     * @param pWidth the width of the <code>HPBar</code>. 
     * @param pHeight the height of the <code>HPBar</code>.
     * @param pPokemon the <code>Pokemon</code> which refers the <code>HPBar</code>.
     */
    public HPBar(int pX, int pY, int pWidth, int pHeight, Pokemon pPokemon) {
        super(pX, pY, pWidth, pHeight);
        
        this.updateHP(pPokemon);
    }
    
    /**
     * Update <code>HPBar</code>. 
     * 
     * @param pPokemon the <code>Pokemon</code> which refers the <code>HPBar</code> that need an updateHP.
     */
    public void updateHP(Pokemon pPokemon) {
        this.maxHP = pPokemon.getMaxHp();
        this.hp = pPokemon.getHp();
    }
    
    /**
     * Draws <code>HPBar</code>.
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void draw(Graphics g, ImageObserver obs) {
        int ratio = getRatioHP(); //based on 48 parts
        
        //drawbar
        g.drawImage(HPBar.HP_BAR_IMAGES[Language.getLanguage()], this.x, this.y, this.width, this.height, obs);
        
        
        if(ratio > 28 ) { //high hp
            g.setColor(HPBar.HIGH_HP_UPPER_COLOR);
            g.fillRect(this.x + 16*this.width/66, this.y+2*this.height/6, (ratio*this.width)/66, this.height/6);
            g.setColor(HPBar.HIGH_HP_DOWN_COLOR);
            g.fillRect(this.x +16*this.width/66, this.y+3*this.height/6, (ratio*this.width)/66, this.height/6);
        } else if (ratio <= 28 && ratio >= 10) { //medium hp
            g.setColor(HPBar.MEDIUM_HP_UPPER_COLOR);
            g.fillRect(this.x + 16*this.width/66, this.y+2*this.height/6, (ratio*this.width)/66, this.height/6);
            g.setColor(HPBar.MEDIUM_HP_DOWN_COLOR);
            g.fillRect(this.x +16*this.width/66, this.y+3*this.height/6, (ratio*this.width)/66, this.height/6);
        } else { //low hp
            g.setColor(HPBar.LOW_HP_UPPER_COLOR);
            g.fillRect(this.x + 16*this.width/66, this.y+2*this.height/6, (ratio*this.width)/66, this.height/6);
            g.setColor(HPBar.LOW_HP_DOWN_COLOR);
            g.fillRect(this.x +16*this.width/66, this.y+3*this.height/6, (ratio*this.width)/66, this.height/6);
        }
    }

    private int getRatioHP() {
        if (this.hp == HPBar.NO_HP)
            return HPBar.NO_HP;
        
        if (this.hp == this.maxHP) 
            return HPBar.FULL_HP;
        
        //to prevent low hp with empty bar.
        return ((HPBar.FULL_HP*this.hp)/this.maxHP) + 1;
    }
    
    
}
