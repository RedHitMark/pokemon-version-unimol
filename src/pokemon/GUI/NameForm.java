/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemon.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import pokemon.logic.characters.Pokemon;
import pokemon.logic.exceptions.PokemonException;
import pokemon.utils.FontGenerator;
import pokemon.utils.Language;
import pokemon.utils.Resources;

/**
 * The class <code>NameForm</code> is the graphical interface to insert player name.
 * This class provides methods to move selector, type and delete letters.
 * 
 * @author Marco Russodivito
 */
public class NameForm extends Rectangle {
    /**
     * Move the letter selector <code>UP</code>.
     */
    public static final int UP = 0;
    /**
     * Move the letter selector <code>DOWN</code>.
     */
    public static final int DOWN = 1;
    /**
     * Move the letter selector <code>RIGHT</code>.
     */
    public static final int RIGHT = 2;
    /**
     * Move the letter selector <code>LEFT</code>.
     */
    public static final int LEFT = 3;
    
    private static final Dimension NAME_FROM_DIMENSION = new Dimension(1280, 720);
    private static final Point NAME_FORM_LOCATION = new Point(0, 0);
    
    private static final BufferedImage BACKGROUND = Resources.getImage("/resources/NameForm/NameForm - background.png");
    private static final BufferedImage SELECTOR = Resources.getImage("/resources/NameForm/NameForm - selector.png");
    
    private static final String[] HEADER_TEXT = {"WHAT'S YOUR NAME?", "COME TI CHIAMI?"};
    
    private static final Color SHADOW_TEXT_COLOR = new Color(208, 208, 208);
    private static final Color FOREGROUND_TEXT_COLOR = new Color(96, 96, 96);
    
    private static final BufferedImage[] PLAYERS_IMAGE = {Resources.getImage("/resources/Player/Fire/map - DOWN_0.png"), Resources.getImage("/resources/Player/Water/map - DOWN_0.png"),  Resources.getImage("/resources/Player/Grass/map - DOWN_0.png")};
    
    private static final Font FONT = FontGenerator.getPokemonFont(Font.PLAIN, 50);
    
    private static final char[][] LETTERS = {
        {'A', 'B', 'C', 'D', 'E', 'F', ' '},
        {'G', 'H', 'I', 'J', 'K', 'L', ' '},
        {'M', 'N', 'O', 'P', 'Q', 'R', 'S'},
        {'T', 'U', 'V', 'W', 'X', 'Y', 'Z'}
    };
    
    
    private StringBuilder name; // max 11 chars
    private Point letterPosition;
    private int playerType;
           
    /**
     * Creates the <code>NameForm</code>.
     * Use <code>setPlayerImage()</code> to set player small image previously selected.
     */
    public NameForm() {
        super(NameForm.NAME_FORM_LOCATION.x, NameForm.NAME_FORM_LOCATION.y , NameForm.NAME_FROM_DIMENSION.width, NameForm.NAME_FROM_DIMENSION.height);
        
        this.letterPosition = new Point(0, 0);
        this.name = new StringBuilder("");
        
        //default 
        this.playerType = Pokemon.TYPE_FIRE;
    }
    
    /**
     * Sets player small image previously selected.
     * 
     * @param pPlayerType the type player previously selected.
     * @throws PokemonException if <code>pPlayerType</code> is not valid
     */
    public void setPlayerType(int pPlayerType) throws PokemonException {
        if( !(pPlayerType == Pokemon.TYPE_FIRE || pPlayerType == Pokemon.TYPE_WATER || pPlayerType == Pokemon.TYPE_GRASS) ) 
            throw new PokemonException();
        
        this.playerType = pPlayerType;
    }
    
    /**
     * Moves selector in the direction
     * 
     * @param pDirection direction to move. Should be <code>UP</code>, <code>DOWN</code>, <code>LEFT</code> or <code>RIGHT</code>
     */
    public void moveSelector(int pDirection) {
        switch (pDirection) {
            case NameForm.UP:
                if(letterPosition.y > 0) {
                    letterPosition.translate(0, -1);
                }
                break;
                
            case NameForm.DOWN:
                if(letterPosition.y < 3) {
                    letterPosition.translate(0, +1);
                }
                break;
                
            case NameForm.RIGHT:
                if(letterPosition.x < 6) {
                    letterPosition.translate(1, 0);
                }
                break;
                
            case NameForm.LEFT:
                if(letterPosition.x > 0) {
                    letterPosition.translate(-1, 0);
                }
                break;
        }
    }
    
    /**
     * Types the letter selected until <code>Pokemon.MAX_CHARS_NAME</code> chars
     */
    public void typeLetter() {
        if(this.name.length() < Pokemon.MAX_CHARS_NAME){
            this.name.append(NameForm.LETTERS[letterPosition.y][letterPosition.x]);
        }    
    }
    
    /**
     * Deletes the last letter typed.
     */
    public void deleteLetter() {
        if( this.name.length() != 0) {
            this.name.deleteCharAt(this.name.length() - 1);
        }    
    }
    
    /**
     * Returns the name typed
     * 
     * @return the name type.
     */
    public String getName() {
        return this.name.toString();
    }
    
    /**
     * Draws <code>NameForm</code>.
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void draw(Graphics g, ImageObserver obs) {
        
        //background
        g.drawImage(NameForm.BACKGROUND, 0, 0, NameForm.NAME_FROM_DIMENSION.width, NameForm.NAME_FROM_DIMENSION.height, obs);
        g.drawImage(NameForm.PLAYERS_IMAGE[this.playerType], 260, 85, 80, 80, obs);
        
        //letter selector
        g.drawImage(NameForm.SELECTOR, 386 + this.letterPosition.x*78, 330 + this.letterPosition.y*84, 60, 80, obs);
        
        //header
        g.setFont(NameForm.FONT);
        g.setColor(NameForm.SHADOW_TEXT_COLOR);
        g.drawString(NameForm.HEADER_TEXT[Language.getLanguage()], 440 + 2, 100 + 2);
        g.setColor(NameForm.FOREGROUND_TEXT_COLOR);
        g.drawString(NameForm.HEADER_TEXT[Language.getLanguage()], 440, 100);
        
        //name
        g.setFont(NameForm.FONT);
        for (int i = 0; i < name.length(); i++) {
            g.setColor(NameForm.SHADOW_TEXT_COLOR);
            g.drawString("" + name.charAt(i), 442 + 43*i + 2, 210 + 2);
            g.setColor(NameForm.FOREGROUND_TEXT_COLOR);
            g.drawString("" + name.charAt(i), 442 + 43*i, 210);
        }
        
        //Border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, NameForm.NAME_FROM_DIMENSION.width - 1, NameForm.NAME_FROM_DIMENSION.height - 1);
    }
}
