package pokemon.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import pokemon.logic.characters.Player;
import pokemon.utils.Language;
import pokemon.utils.Resources;

/**
 * The class <code>EXPBar</code>
 * 
 * @author Marco Russodivito
 */
public class EXPBar extends Rectangle {
    private static final int NO_EXP = 0;
    private static final int FULL_EXP = 64;
    
    private static final BufferedImage[] HP_BAR_IMAGES = {Resources.getImage("/resources/EXPBar/ExpBar_0.png"), Resources.getImage("/resources/EXPBar/ExpBar_1.png")};
    
    private static final Color BAR_COLOR = new Color(64, 200, 248);
    
    private int maxEXP;
    private int exp;
    
    /**
     * Creates an <code>EXPBar</code>.
     * Keep 41:3 pixel proporsion for visual reason.
     * 
     * @param pX the X coordinate of the upper-left corner of the <code>EXPBar</code>.
     * @param pY the Y coordinate of the upper-left corner of the <code>EXPBar</code>.
     * @param pWidth the width of the <code>EXPBar</code>. 
     * @param pHeight the height of the <code>EXPBar</code>.
     * @param pPokemon the <code>Pokemon</code> which refers the <code>EXPBar</code>.
     */
    public EXPBar(int pX, int pY, int pWidth, int pHeight, Player pPokemon) {
        super(pX, pY, pWidth, pHeight);
        
        this.updateEXP(pPokemon);
    }
    
    /**
     * Update <code>EXPBar</code>. 
     * 
     * @param pPokemon the <code>Pokemon</code> which refers the <code>EXPBar</code> that need an updateEXP.
     */
    public void updateEXP(Player pPokemon) {
        this.maxEXP = pPokemon.getTotalExpToLevelUp();
        this.exp = pPokemon.getExp();
    }
    
    /**
     * Draws <code>EXPBar</code>.
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void draw(Graphics g, ImageObserver obs) {
        int ratio = getRatioEXP();// in 64 parts
        
        //drawbar
        g.drawImage(EXPBar.HP_BAR_IMAGES[Language.getLanguage()], this.x, this.y, this.width, this.height, obs);
        
        //progress
        g.setColor(EXPBar.BAR_COLOR);
        g.fillRect(this.x + (16*this.width)/82, this.y+2*this.height/6,  (ratio*this.width)/82, 2*this.height/6);
        
        
    }

    private int getRatioEXP() {
        if (this.exp == EXPBar.NO_EXP)
            return EXPBar.NO_EXP;
        
        if (this.exp == this.maxEXP) 
            return EXPBar.FULL_EXP;
        
        //to prevent full exp bug.
        return ((EXPBar.FULL_EXP*this.exp)/this.maxEXP) + 1;
    }
}
