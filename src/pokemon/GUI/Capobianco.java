package pokemon.GUI;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import pokemon.utils.Resources;

/**
 * The class <code>Capobianco</code> is the tutor in tutorial panel.
 * The class provide methos to move Capobianco's arm and pen.  
 * 
 * @author Marco Russodivito
 */
public class Capobianco extends Rectangle {
    private static final String CAPOBIANCO_NAME = "Capobianco";
    
    private static final BufferedImage[] CAPOBIANCO_BODY_IMAGE = {Resources.getImage("/resources/Capobianco/Capobianco0.png"),Resources.getImage("/resources/Capobianco/Capobianco1.png")};
    private static final BufferedImage[] CAPOBIANCO_PEN_IMAGE = {Resources.getImage("/resources/Capobianco/Pen0.png"),Resources.getImage("/resources/Capobianco/Pen1.png")};
    private static final BufferedImage[] CAPOBIANCO_EXPLOSION_IMAGE = {Resources.getImage("/resources/Capobianco/Explosion0.png"),Resources.getImage("/resources/Capobianco/Explosion1.png")};
    
    private int armStatus;
    private int explosionStatus;
    private boolean exploded;
    private boolean stop;
    
    //pen offset
    private int penOffset;
    
    /**
     * Create <code>Capobianco</code> and init is status
     * 
     * @param pX the X coordinate of the upper-left corner of <code>Capobianco</code>.
     * @param pY the Y coordinate of the upper-left corner of <code>Capobianco</code>.
     * @param pWidth the width of <code>Capobianco</code>.
     * @param pHeight the height of <code>Capobianco</code>
     */
    public Capobianco(int pX, int pY, int pWidth, int pHeight) {
        super(pX, pY, pWidth, pHeight);
        
        this.armStatus = 0;
        this.explosionStatus = 0;
        this.stop = false;
        
        this.penOffset = 0;
    }
    
    /**
     * Move <code>Capobianco</code>'s arm and his pen.
     */
    public void moveArm() {
        this.armStatus = 1;
    }
    
    /**
     * Move <code>Capobianco</code>'s pen. Use it after <code>moveArm()</code>.
     */
    public void movePen() {
        this.penOffset++;
    }
    
    /**
     * Start expolsion. Use it after <code>movePen()</code>.
     */
    public void explodePen() {
        this.exploded = true;
    }
    
    /**
     * Toogle explosion frame. Use it after <code>explodePen()</code>.
     */
    public void switchExplosionStatus() {
        this.explosionStatus = (this.explosionStatus==0)? 1 : 0;
    }
    
    /**
     * Stop explosion.
     */
    public void stopExplosion() {
        this.stop = true;
    }
    
    /**
     * Draws <code>Capobianco</code> and his pen
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void draw(Graphics g, ImageObserver obs) {
        //drawbody
        g.drawImage(CAPOBIANCO_BODY_IMAGE[armStatus], this.x, this.y, this.width, this.height, obs);
        
        //draw pen
        if(!exploded){
            g.drawImage(CAPOBIANCO_PEN_IMAGE[armStatus], (this.x-penOffset), (this.y+penOffset), this.width, this.height, obs);
        }
        
        //draw explosion
        if(exploded && !stop) {
            g.drawImage(CAPOBIANCO_EXPLOSION_IMAGE[this.explosionStatus], (this.x-penOffset), (this.y+penOffset), this.width, this.height, obs);
        }    
    }

}
