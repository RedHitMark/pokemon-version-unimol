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
import pokemon.logic.characters.Player;
import pokemon.logic.characters.Pokemon;
import pokemon.utils.FontGenerator;
import pokemon.utils.Resources;

/**
 * The class <code>PokemonBox</code> shows infomration about <code>Pokemon</code> during battles.
 * 
 * @author Marco Russodivito
 */
public class PokemonBox extends Rectangle {
    
    private static final BufferedImage BACKGROUND = Resources.getImage("/resources/PokemonBox.png");
    
    private static final Color TEXT_FOREGROUND_COLOR = new Color(96, 96, 96);
    private static final Color TEXT_SHADOW_COLOR = new Color(208, 208, 208);
            
    private Pokemon pokemon;
    private HPBar hpBar;
    private EXPBar expBar;
    private Font font; 
    
    /**
     * Creates a <code>PokemonBox</code>.
     * 
     * @param pX the X coordinate of the upper-left corner of the <code>PokemonBox</code>.
     * @param pY the Y coordinate of the upper-left corner of the <code>PokemonBox</code>.
     * @param pWidth the width of the <code>PokemonBox</code>. 
     * @param pHeight the height of the <code>PokemonBox</code>.
     * @param pPokemon the <code>Pokemon</code> which refers the <code>PokemonBox</code>.
     */
    public PokemonBox(int pX, int pY, int pWidth, int pHeight, Pokemon pPokemon) {
        super(pX, pY, pWidth, pHeight);
        
        this.font = FontGenerator.getPokemonFont(Font.PLAIN, this.height/5);
        
        this.pokemon = pPokemon;
        this.hpBar = new HPBar(this.x + this.width - this.width/15 - 66*this.height/30, this.y + 2*this.height/5, 66*this.height/30, this.height/5, this.pokemon);
        
        if (this.pokemon instanceof Player) {
            this.expBar = new EXPBar(this.x, this.y + this.height, this.width, 6*this.width/82, (Player) this.pokemon);
        }
    }
    
    /**
     * Update <code>PokemonBox</code>. 
     * 
     * @param pPokemon the <code>Pokemon</code> which refers the <code>PokemonBox</code> that need an updatePokemonStatus.
     */
    public void updatePokemonStatus(Pokemon pPokemon) {
        this.pokemon = pPokemon;
        
        this.hpBar.updateHP(this.pokemon);
        if (this.pokemon instanceof Player) {
            this.expBar.updateEXP((Player) this.pokemon);
        }
    }
    
    /**
     * Draws <code>PokemonBox</code>.
     * Draw the hp text and the exp bar if and only if <code>Pokemon</code> is a <code>Player</code>
     * 
     * @param g the Graphics object.
     * @param obs the ImageObserver object.
     */
    public void draw(Graphics g, ImageObserver obs) {
        g.drawImage(PokemonBox.BACKGROUND, this.x, this.y, this.width, this.height, obs);
        
        FontMetrics fm = g.getFontMetrics(this.font);
        g.setFont(this.font);
        
        //name
        g.setColor(PokemonBox.TEXT_SHADOW_COLOR);
        g.drawString(this.pokemon.getName(), this.x + this.width/15 + 2, this.y + this.height/5 + fm.getHeight() + 2);
        g.setColor(PokemonBox.TEXT_FOREGROUND_COLOR);
        g.drawString(this.pokemon.getName(), this.x + this.width/15, this.y + this.height/5 + fm.getHeight());
        //level
        g.setColor(PokemonBox.TEXT_SHADOW_COLOR);
        g.drawString("L. " + this.pokemon.getLevel(), this.x + this.width - this.width/15 - fm.stringWidth("L. " + this.pokemon.getLevel()) + 2, this.y + this.height/5 + fm.getHeight() + 2);
        g.setColor(PokemonBox.TEXT_FOREGROUND_COLOR);
        g.drawString("L. " + this.pokemon.getLevel(), this.x + this.width - this.width/15 - fm.stringWidth("L. " + this.pokemon.getLevel()), this.y + this.height/5 + fm.getHeight());
        
        //hp bar
        this.hpBar.draw(g, obs);
        
        //exp bar and hp text
        if(this.pokemon instanceof Player) {
            g.setColor(PokemonBox.TEXT_SHADOW_COLOR);
            g.drawString( this.pokemon.getHp() + "/" + this.pokemon.getMaxHp(), this.x + this.width - this.width/15 - fm.stringWidth(this.pokemon.getHp() + "/" + this.pokemon.getMaxHp()) + 2, this.y + 4*this.height/5 + 2);
            g.setColor(PokemonBox.TEXT_FOREGROUND_COLOR);
            g.drawString( this.pokemon.getHp() + "/" + this.pokemon.getMaxHp(), this.x + this.width - this.width/15 - fm.stringWidth(this.pokemon.getHp() + "/" + this.pokemon.getMaxHp()), this.y + 4*this.height/5);
            this.expBar.draw(g, obs);
        }
    }
    
}
