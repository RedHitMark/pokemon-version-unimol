package pokemon.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import pokemon.utils.FontGenerator;

/**
 * The class <code>PokeOptionBox</code> is a selectable area with text and switchable options.
 * 
 * @author Marco Russodivito
 */
public class PokeOptionBox extends Rectangle {
    private static final Color SELECTED_BACKGROUND_COLOR = new Color(248, 248, 248);
    private static final Color DESELECTED_BACKGROUND_COLOR = new Color(192, 192, 192);
    
    private static final Color SELECTED_TEXT_FOREGROUND_COLOR = new Color(248, 176, 80);
    private static final Color SELECTED_TEXT_SHADOW_COLOR = new Color(192, 120, 0);
    
    private static final Color DESELECTED_TEXT_FOREGROUND_COLOR = new Color(192, 136, 64);
    private static final Color DESELECTED_TEXT_SHADOW_COLOR = new Color(144, 96, 0);
    
    private static final Color SELECTED_OPTION_FOREGROUND_COLOR = new Color(248, 48, 24);
    private static final Color SELECTED_OPTION_SHADOW_COLOR = new Color(248, 136, 128);
    
    private static final Color DESELECTED_OPTION_FOREGROUND_COLOR = new Color(72, 72, 72);
    private static final Color DESELECTED_OPTION_SHADOW_COLOR = new Color(208, 208, 208);
    
    private String text;
    private Font textFont;
    private String[] options;
    private Font optionsFont;
    private int optionChoise;
    private boolean selected;
    
    /**
     * Creates a <code>PokeOptionBox</code> without text or options.
     * Use <code>setText()</code> and <code>setOptions</code> to add those fields
     * 
     * @param pX the X coordinate of the upper-left corner of the <code>PokeOptionBox</code>.
     * @param pY the Y coordinate of the upper-left corner of the <code>PokeOptionBox</code>.
     * @param pWidth the width of the <code>PokeOptionBox</code>.
     * @param pHeight the height of the <code>PokeOptionBox</code>.
     */
    public PokeOptionBox(int pX, int pY, int pWidth, int pHeight) {
        super(pX, pY, pWidth, pHeight);
        
        this.textFont = FontGenerator.getPokemonFont(Font.PLAIN, 4*this.height/5);
        this.optionsFont = FontGenerator.getPokemonFont(Font.PLAIN, this.height/2);
        
        this.selected = false;
    }

    /**
     * Sets the text.
     * 
     * @param pText the text to set
     */
    public void setText(String pText) {
        this.text = pText;
    }
    /**
     * Sets the options and selection
     * 
     * @param pOptions the option to set
     * @param pOptionChoise the option choise - Should be beetween 0 and <code>(pOptions.lenght - 1)</code> otherwise you cannot switch options. 
     */
    public void setOptions(String[] pOptions, int pOptionChoise) {
        this.options = pOptions;
        this.optionChoise = pOptionChoise;
    }
    
    /**
     * Returns the option choise
     * @return the option choise
     */
    public int getOptionChoise() {
        return this.optionChoise;
    }
    
    /**
     * Selects the area.
     */
    public void select() {
        this.selected = true;
    }
    /**
     * Deselect the area.
     */
    public void deselect() {
        this.selected = false;
    }
    
    /**
     * Switch to the next option.
     */
    public void nextOption() {
        if(this.options != null) {
            if(this.optionChoise < this.options.length - 1) {
                this.optionChoise++;
            }
        }    
    }
    /**
     * Switch to the previuos option.
     */
    public void previousOption() {
        if(this.options != null) {
            if(this.optionChoise > 0) {
                this.optionChoise--;
            }
        }
    }
    
    /**
     * Draws <code>PokeOptionBox</code> and text inside.
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void draw(Graphics g, ImageObserver obs) {
        //background
        if(this.selected) {
            g.setColor(PokeOptionBox.SELECTED_BACKGROUND_COLOR);
            g.fillRect(this.x, this.y, this.width, this.height);
        } else {
            g.setColor(PokeOptionBox.DESELECTED_BACKGROUND_COLOR);
            g.fillRect(this.x, this.y, this.width, this.height);
        }    
        
        
        //text field
        if(this.text != null) {
            g.setFont(this.textFont);
            FontMetrics fm = g.getFontMetrics(this.textFont);
            
            if(this.selected) {
                g.setColor(SELECTED_TEXT_SHADOW_COLOR);
                g.drawString(this.text, this.x + this.width/20 + 2, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent() + 2);
                g.setColor(SELECTED_TEXT_FOREGROUND_COLOR);
                g.drawString(this.text, this.x + this.width/20, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent());
            } else {
                g.setColor(DESELECTED_TEXT_SHADOW_COLOR);
                g.drawString(this.text, this.x + this.width/20 + 2, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent() + 2);
                g.setColor(DESELECTED_TEXT_FOREGROUND_COLOR);
                g.drawString(this.text, this.x + this.width/20, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent());
            }
        }
        
        //choises field
        if (this.options != null) {
            g.setFont(this.optionsFont);
            FontMetrics fm = g.getFontMetrics(this.optionsFont);
                    
            for (int i = 0; i < options.length; i++) {
                if(i == this.optionChoise) {
                    g.setColor(PokeOptionBox.SELECTED_OPTION_SHADOW_COLOR);
                    g.drawString(this.options[i], this.x + this.width/2 + i*this.width/(2*options.length) + 2, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent());
                    g.setColor(PokeOptionBox.SELECTED_OPTION_FOREGROUND_COLOR);
                    g.drawString(this.options[i], this.x + this.width/2 + i*this.width/(2*options.length), this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent());
                } else {
                    g.setColor(PokeOptionBox.DESELECTED_OPTION_SHADOW_COLOR);
                    g.drawString(this.options[i], this.x + this.width/2 + i*this.width/(2*options.length) + 2, this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent() + 2);
                    g.setColor(PokeOptionBox.DESELECTED_OPTION_FOREGROUND_COLOR);
                    g.drawString(this.options[i], this.x + this.width/2 + i*this.width/(2*options.length), this.y + ((this.height - fm.getHeight()) / 2) + fm.getAscent());
                }
            }
        }
    }
}
