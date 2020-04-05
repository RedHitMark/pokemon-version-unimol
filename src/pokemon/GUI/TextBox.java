/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * The class <code>TextBox</code> is a selectable text field.
 * This class provides methods to draw text left, right or center align.
 * 
 * @author Marco Russodivito
 */
public class TextBox extends Rectangle {
    
    private static final BufferedImage SELECTOR = Resources.getImage("/resources/TextBox/TextBox - selector.png");
    
    private static final Color TEXT_FOREGROUND_COLOR = new Color(96, 96, 96);
    private static final Color TEXT_SHADOW_COLOR = new Color(208, 208, 208);
    
    private String text;
    private Font font;
    private boolean selected;
    
    /**
     * Creates <code>TextBox</code> not no selcted with text inside.
     * 
     * @param pX the X coordinate of the upper-left corner of the <code>TextBox</code>.
     * @param pY the Y coordinate of the upper-left corner of the <code>TextBox</code>.
     * @param pWidth the width of the <code>TextBox</code>.
     * @param pHeight the height of the <code>TextBox</code>. 
     * @param pText the text inside the <code>TextBox</code>.
     */
    public TextBox(int pX, int pY, int pWidth ,int pHeight, String pText) {
        super(pX, pY, pWidth, pHeight);
        
        this.text = pText;
        this.font = FontGenerator.getPokemonFont(Font.PLAIN, this.height);
        this.selected = false;
    }
    
    
    /**
     * Selects the <code>TextBox</code>.
     */
    public void select() {
        this.selected = true;
    }
    
    /**
     * Deselect the <code>TextBox</code>.
     */
    public void deselect() {
        this.selected = false;
    }
    
    /**
     * Draw the <code>TextBox</code> with text center align and the selector.
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void drawCenter(Graphics g, ImageObserver obs) {
        if(text != null){
            FontMetrics fm = g.getFontMetrics(this.font);
            
            g.setFont(this.font);
            g.setColor(TextBox.TEXT_SHADOW_COLOR);
            g.drawString(this.text, this.x + (this.width - fm.stringWidth(this.text))/2 + 2, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent() + 2);
            g.setColor(TextBox.TEXT_FOREGROUND_COLOR);
            g.drawString(this.text, this.x + (this.width - fm.stringWidth(this.text))/2 , this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent());

            if(this.selected) {
                g.drawImage(TextBox.SELECTOR, this.x + ((this.width - fm.stringWidth(this.text))/2) - this.height, this.y, this.height, this.height, obs);
            }
        }    
    }
    
    /**
     * Draw the <code>TextBox</code> with text left align and the selector.
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void drawLeft(Graphics g, ImageObserver obs) {
        if(text != null){
            FontMetrics fm = g.getFontMetrics(this.font);

            g.setFont(this.font);
            g.setColor(TextBox.TEXT_SHADOW_COLOR);
            g.drawString(this.text, this.x + 2, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent() + 2);
            g.setColor(TextBox.TEXT_FOREGROUND_COLOR);
            g.drawString(this.text, this.x, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent());

            if(this.selected) {
                g.drawImage(TextBox.SELECTOR, this.x - this.height, this.y, this.height, this.height, obs);
            }
        }    
    }
    
    /**
     * Draw the <code>TextBox</code> with text right align and the selector.
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void drawRight(Graphics g, ImageObserver obs) {
        if(text != null) { 
            FontMetrics fm = g.getFontMetrics(this.font);

            g.setFont(this.font);
            g.setColor(TextBox.TEXT_SHADOW_COLOR);
            g.drawString(this.text, this.x + this.width - fm.stringWidth(this.text) + 2, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent() + 2);
            g.setColor(TextBox.TEXT_FOREGROUND_COLOR);
            g.drawString(this.text, this.x + this.width - fm.stringWidth(this.text), this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent());

            if(this.selected) {
                g.drawImage(TextBox.SELECTOR, this.x + this.width - fm.stringWidth(this.text) - this.height, this.y, this.height, this.height, obs);
            }
        }    
    }
}
