package pokemon.GUI;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import pokemon.utils.Resources;

/**
 * The class <code>VerticalMenu</code> is a vertical menu with entry inside.
 * This class provides methods to select and switch between entries. 
 * 
 * @author Marco Russodivito
 */
public class VerticalMenu extends Rectangle {
    
    private static final BufferedImage BACKGROUND = Resources.getImage("/resources/VerticalMenu/VerticalMenu - background.png");
    
    private TextBox[] entryBox;
    private int entrySelected;
    
    /**
     * Creates the <code>VerticalMenu</code> with entries.
     * 
     * @param pX the X coordinate of the upper-left corner of the <code>VerticalMenu</code>.
     * @param pY the Y coordinate of the upper-left corner of the <code>VerticalMenu</code>.
     * @param pWidth the width of the <code>VerticalMenu</code>.
     * @param pHeight the height of the <code>VerticalMenu</code>. 
     * @param pEntries the entries of <code>VerticalMenu</code>
     */
    public VerticalMenu(int pX, int pY, int pWidth, int pHeight, String[] pEntries){
        super(pX, pY, pWidth, pHeight);
        
        
        this.entryBox = new TextBox[pEntries.length];
        for (int i = 0; i < entryBox.length; i++) {
            this.entryBox[i] = new TextBox(this.x + this.width/4, this.y + this.height/10 + 2*i*((8*this.height/10)/(2*entryBox.length - 1)), 2*this.width/4, (8*this.height/10)/(2*entryBox.length - 1), pEntries[i]);
        }
        
        entryBox[0].select();
        this.entrySelected = 0;
    }
    
    /**
     * Switch to the next entry.
     */
    public void nextEntry() {
        if( this.entrySelected < (this.entryBox.length - 1) ) {
            this.entryBox[this.entrySelected].deselect();
            this.entrySelected++;
            this.entryBox[this.entrySelected].select();
        }
    }
    
    /**
     * Switch to the previous entry.
     */
    public void previousEntry() {
        if( this.entrySelected > 0) {
            this.entryBox[this.entrySelected].deselect();
            this.entrySelected--;
            this.entryBox[this.entrySelected].select();
        }
    }
    
    /**
     * Resets the entry selection.
     */
    public void resetEntrySelection() {
        this.entryBox[this.entrySelected].deselect();
        this.entrySelected = 0;
        this.entryBox[this.entrySelected].select();
    }
    
    /**
     * Returns the entry selected
     * 
     * @return the entry selected
     */
    public int getEntrySelected() {
        return this.entrySelected;
    }
    
    /**
     * Draw the <code>VericalMenu</code>
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void draw(Graphics g, ImageObserver obs) {
        //background
        g.drawImage(VerticalMenu.BACKGROUND, this.x, this.y, this.width, this.height, obs);
        
        //menu entries
        for (TextBox textBox : entryBox) {
            textBox.drawLeft(g, obs);
        }
    }
}
