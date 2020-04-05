package pokemon.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import pokemon.utils.FontGenerator;
import pokemon.utils.Resources;

/**
 * The class <code>PokeButton</code> is a selectable button with text inside.
 * 
 * @author Marco Russodivito
 */
public class PokeButton extends Rectangle {
    private static final BufferedImage BUTTON_SELECTED_IMAGE = Resources.getImage("/resources/PokeButton/PokeButton - selected.png");
    private static final BufferedImage BUTTON_DESELECTED_IMAGE = Resources.getImage("/resources/PokeButton/PokeButton - deselected.png");
    
    private static final Color SELECTED_TEXT_FOREGROUND_COLOR = new Color(96, 96, 96);
    private static final Color SELECTED_TEXT_SHADOW_COLOR = new Color(208, 208, 208);
    private static final Color DESELECTED_TEXT_FOREGROUND_COLOR = new Color(56, 56, 56);
    private static final Color DESELECTED_TEXT_SHADOW_COLOR = new Color(120, 120, 120);
    
    private String text;
    private Font font;
    
    private boolean selected;
    
    /**
     * Creates a <code>PokeButton</code> with text inside.
     * 
     * @param pX the X coordinate of the upper-left corner of the <code>PokeButton</code>.
     * @param pY the Y coordinate of the upper-left corner of the <code>PokeButton</code>.
     * @param pWidth the width of the <code>PokeButton</code>.
     * @param pHeight the height of the <code>PokeButton</code>.
     * @param pText the text in <code>PokeButton</code>.
     */
    public PokeButton(int pX,int pY, int pWidth, int pHeight, String pText) {
        super(pX, pY, pWidth, pHeight);
        
        this.text = pText;
        this.font = FontGenerator.getPokemonFont(Font.PLAIN, this.height/2);
                
        this.selected = false;
    }

    /**
     * Checks if the <code>PokeButton</code> is selected.
     * 
     * @return <code>true</code> if <code>PokeButton</code> is selected; <code>false</code> otherwise. 
     */
    public boolean isSelected() {
        return selected;
    }
    
    /**
     * Selects the <code>PokeButton</code>.
     */
    public void select() {
        this.selected = true;
    }
    
    /**
     * Deselects the <code>PokeButton</code>.
     */
    public void deselect() {
        this.selected = false;
    }
    
    /**
     * Draws <code>PokeButton</code> and text inside.
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void draw(Graphics g, ImageObserver obs) {
        g.setFont(this.font);
        FontMetrics fm = g.getFontMetrics(font);
        
        if (this.selected){
            g.drawImage(PokeButton.BUTTON_SELECTED_IMAGE, this.x, this.y, this.width, this.height, obs);
            
            g.setColor(PokeButton.SELECTED_TEXT_SHADOW_COLOR);
            g.drawString(this.text, this.x + this.height/3 + 2, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent() + 2);
            g.setColor(PokeButton.SELECTED_TEXT_FOREGROUND_COLOR);
            g.drawString(this.text, this.x + this.height/3, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent());
        } else {
            g.drawImage(PokeButton.BUTTON_DESELECTED_IMAGE, this.x, this.y, this.width, this.height, obs);
            
            g.setColor(PokeButton.DESELECTED_TEXT_SHADOW_COLOR);
            g.drawString(this.text, this.x + this.height/3 + 2, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent() + 2);
            g.setColor(PokeButton.DESELECTED_TEXT_FOREGROUND_COLOR);
            g.drawString(this.text, this.x + this.height/3, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent());
        }
    }
}
