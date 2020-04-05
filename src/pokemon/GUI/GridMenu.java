/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemon.GUI;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import pokemon.utils.Resources;

/**
 * The class <code>GridMenu</code> is a grid menu with max 4 entry inside.
 * This class provides methods to select and switch between entries. 
 * 
 * @author Marco Russodivito
 */
public class GridMenu extends Rectangle {
    
    private static final BufferedImage BACKGROUND = Resources.getImage("/resources/FightBox.png");
    
    private TextBox[] entryBoxes;
    
    private int entrySelected;
    
    /**
     * Creates a <code>GridMenu</code> with entries.
     * Be careful - DO NOT ADD more than 4 entries - TODO throw Exception
     * 
     * @param pX the X coordinate of the upper-left corner of the <code>GridMenu</code>.
     * @param pY the Y coordinate of the upper-left corner of the <code>GridMenu</code>.
     * @param pWidth the width of the <code>GridMenu</code>.
     * @param pHeight the height of the <code>GridMenu</code>.
     * @param pEntries the entries of <code>GridMenu</code>.
     */
    public GridMenu(int pX, int pY, int pWidth, int pHeight, String[] pEntries) {
        super(pX, pY, pWidth, pHeight);
        
        this.entryBoxes = new TextBox[pEntries.length];
        
        //this.textBox[0] = new TextBox(this.x + this.width/11, this.y + this.height/5, (4*this.width)/11, this.height/5);
        //this.textBox[1] = new TextBox(this.x + 6*this.width/11 , this.y + this.height/5, (4*this.width)/11, this.height/5);
        //this.textBox[2] = new TextBox(this.x + this.width/11, this.y + 3*this.height/5, (4*this.width)/11, this.height/5);
        //this.textBox[3] = new TextBox(this.x + 6*this.width/11 , this.y + 3*this.height/5, (4*this.width)/11, this.height/5);
        for (int i = 0; i < this.entryBoxes.length; i++) {
            int xMultiplay = (i%2 != 0)?  6 : 1;
            int yMultiplay = (i>1)?  3 : 1;
            this.entryBoxes[i] = new TextBox(this.x + xMultiplay*this.width/11, this.y + yMultiplay*this.height/5,(4*this.width)/11, this.height/5, pEntries[i]);
        }
        
        this.entrySelected = 0;
        this.entryBoxes[0].select();
    }
    
    /**
     * Select the next entry.
     */
    public void nextSelection() {
        if( this.entrySelected < (this.entryBoxes.length - 1) ) {
            this.entryBoxes[this.entrySelected].deselect();
            this.entrySelected++;
            this.entryBoxes[this.entrySelected].select();
        }
    }
    
    /**
     * Selects the previous entry.
     */
    public void previousSelection() {
        if( this.entrySelected > 0) {
            this.entryBoxes[this.entrySelected].deselect();
            this.entrySelected--;
            this.entryBoxes[this.entrySelected].select();
        }
    }
    
    /**
     * Resets the entry entrySelected.
     */
    public void resetEntrySelection() {
        this.entryBoxes[this.entrySelected].deselect();
        this.entrySelected = 0;
        this.entryBoxes[this.entrySelected].select();
    }
    
    /**
     * Return the entry selected.
     * 
     * @return the entry selected.
     */
    public int getEntrySelected() {
        return this.entrySelected;
    }
    
    /**
     * Draw the <code>GridMenu</code>
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void draw(Graphics g, ImageObserver obs) {
        g.drawImage(GridMenu.BACKGROUND, this.x, this.y, this.width, this.height, obs);
        
        for (TextBox entryBox : this.entryBoxes) {
            entryBox.drawCenter(g, obs);  
        }
    }
}
