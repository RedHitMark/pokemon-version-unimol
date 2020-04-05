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
 * The class <code>DialogBox</code> manage every dialog of the game.
 * The dialog is shown on two line.
 * 
 * @author Marco Russodivito
 */
public class DialogBox extends Rectangle {
    private static final BufferedImage BACKGROUND_BOX_IMAGE = Resources.getImage("/resources/DialogBox/DialogBox - background.png");
    private static final BufferedImage END_DIALOG_IMAGE = Resources.getImage("/resources/DialogBox/DialogBox - end.png");
    
    private static final Color SHADOW_TEXT_COLOR = new Color(208, 208, 208);
    private static final Color FOREGROUND_TEXT_COLOR = new Color(96, 96, 96);
    
    private Font pokemonFont;
    
    private String dialog;
    
    private boolean termianted; 
    
    /**
     * Creates an empty <code>PokeButton</code>.
     * Use <code>setDialog()</code> to set dialog.
     * 
     * @param pX the X coordinate of the upper-left corner of the <code>DialogBox</code>.
     * @param pY the Y coordinate of the upper-left corner of the <code>DialogBox</code>.
     * @param pWidth the width of the <code>DialogBox</code>.
     * @param pHeight the height of the <code>DialogBox</code>.
     */
    public DialogBox(int pX, int pY, int pWidth, int pHeight) {
        super(pX, pY, pWidth, pHeight);
        this.pokemonFont = FontGenerator.getPokemonFont(Font.PLAIN, (int) 2*this.height/7);
        
        this.dialog = null;
        this.termianted = false;
    }

    /**
     * Sets the dialog and flag if the dialog is terminated.
     * 
     * @param pDialog the dialog to set.
     * @param pTeminated <code>true</code> if dialog is terminate, <code>false</code> otherwise.
     */
    public void setDialog(String pDialog, boolean pTeminated) {
        this.dialog = pDialog;
        this.termianted = pTeminated;
    }

    /**
     * Draws <code>DialogBox</code>, text inside and end dialog image.
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void draw(Graphics g, ImageObserver obs) {
        FontMetrics fm = g.getFontMetrics(this.pokemonFont);
        
        //background
        g.drawImage(DialogBox.BACKGROUND_BOX_IMAGE, this.x, this.y, this.width, this.height, obs);
        
        //divide dialog in two lines
        StringBuilder firstLineDialog = new StringBuilder("");
        StringBuilder secondLineDialog = new StringBuilder("");
        if(this.dialog != null) {
            String[] words = this.dialog.split(" "); //divide string in an array of words
            
            boolean stopFirstLine = false;
            for (int i = 0; i < words.length; i++) {
                if( !(stopFirstLine) && (fm.stringWidth( firstLineDialog + words[i] + " " ) < (18*this.width/20)) ) {
                    firstLineDialog.append(words[i]).append(" ");
                } else {
                    stopFirstLine = true;
                    secondLineDialog.append(words[i]).append(" ");
                }
            }
        }
        
        //draw two lines
        g.setFont(pokemonFont);
        g.setColor(DialogBox.SHADOW_TEXT_COLOR);
        g.drawString(firstLineDialog.toString(), this.x + this.width/20 + 2, this.y + this.height/5 + ((this.height/5 - fm.getHeight()) / 2) + fm.getAscent() + 2);
        g.drawString(secondLineDialog.toString(), this.x + this.width/20 + 2, this.y + 3*this.height/5 + ((this.height/5 - fm.getHeight()) / 2) + fm.getAscent() + 2);
        g.setColor(DialogBox.FOREGROUND_TEXT_COLOR);
        g.drawString(firstLineDialog.toString(), this.x + this.width/20, this.y + this.height/5 + ((this.height/5 - fm.getHeight()) / 2) + fm.getAscent());
        g.drawString(secondLineDialog.toString(), this.x + this.width/20, this.y + 3*this.height/5 + ((this.height/5 - fm.getHeight()) / 2) + fm.getAscent());
        
        //Draw end dialog image when the dialog is termianted
        if(this.termianted) {
            if(secondLineDialog.length() == 0) {
                g.drawImage(DialogBox.END_DIALOG_IMAGE, this.x + this.width/20 + fm.stringWidth(firstLineDialog.toString()), this.y + this.height/5 + this.height/15, this.height/6, this.height/6, obs);
            } else {
                g.drawImage(DialogBox.END_DIALOG_IMAGE, this.x + this.width/20 + fm.stringWidth(secondLineDialog.toString()), this.y + 3*this.height/5 + this.height/15, this.height/6, this.height/6, obs);
            }
        }
    }
}
