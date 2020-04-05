package pokemon.logic.characters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import pokemon.logic.exceptions.PokemonException;

/**
 * The class <code>Enemy</code> manage every <code>Pokemon Enemy</code> of the game.
 * 
 * @author Marco Russodivito
 */
public class Enemy extends Pokemon {
    
    private BufferedImage battleImage;
    
    /**
     * Generates an <code>Enemy</code> with name, type, level and battle image requested.
     * 
     * @param pName the name of <code>Enemy</code>
     * @param pType the type of <code>Enemy</code>
     * @param pLevel the level of <code>Enemy</code>
     * @param pBattleImage the battle image of <code>Enemy</code>.
     * @throws PokemonException if parameters don't respect PRE-CONDITIONS
     */
    public Enemy(String pName, int pType, int pLevel, BufferedImage pBattleImage) throws PokemonException {
        super(pName, pType, pLevel);
        
        this.battleImage = pBattleImage;
    }
    
    /**
     * Draws <code>Enemy</code> on <code>BattlePanel</code>.
     * 
     * @param pX the X coordinate of the upper-left corner of <code>Enemy</code> on <code>BattlePanel</code>.
     * @param pY the Y coordinate of the upper-left corner of <code>Enemy</code> on <code>BattlePanel</code>.
     * @param pWidth the width of <code>Enemy</code> on <code>BattlePanel</code>.
     * @param pHeight the height of <code>Enemy</code> on <code>BattlePanel</code>.
     * @param g the Graphics object.
     * @param obs the ImageObserver object. 
     */
    public void drawBattleImage(int pX, int pY, int pWidth, int pHeight, Graphics g, ImageObserver obs) {
        g.drawImage(this.battleImage, pX, pY, pWidth, pHeight, obs);
    }
}
