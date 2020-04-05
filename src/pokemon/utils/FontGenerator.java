package pokemon.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

/**
 * The class <code>FontGenerator</code> manages and generates fonts.
 * This class provides methods to load and get font a generic font or PokemonFont.
 * 
 * @author Marco Russodivito
 */
public class FontGenerator {
    private static final String POKEMON_FONT_NAME = "Power green";
    
    private static boolean fontLoaded = false;

    
    /**
     * Creates a new <code>Font</code> with name, style and point size specified.
     * 
     * @param pName the name of the <code>Font</code>.
     * @param pStyle the style of the <code>Font</code>.
     * @param pSize the point size of the <code>Font</code>.
     * @return the <code>Font</code> created.
     */
    public static Font getFont(String pName, int pStyle, int pSize) {
        return new Font(pName, pStyle, pSize);
    }
    
    /**
     * Creates the Pokémon Emerald <code>Font</code> with style and point size specified.
     * 
     * @param pStyle the style of Pokémon Emerald <code>Font</code>.
     * @param pSize the point size of Pokémon Emerald <code>Font</code>.
     * @return Pokemon Emerald <code>Font</code>; Arial <code>Font</code> if error occurs.
     */
    public static Font getPokemonFont(int pStyle, int pSize) {
        if (!fontLoaded) {
            try {
                FontGenerator.loadPokemonFont();
            } catch (FontGeneratorException ex) {
                return getFont("Arial", pStyle, pSize);
            }
        }
        
        return getFont(POKEMON_FONT_NAME, pStyle, pSize);
    }
    
    private static void loadPokemonFont() throws FontGeneratorException {
        try {
            GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Resources.getResourceAsStream("/resources/FontGenerator/Pokemon font.ttf")));
        } catch (FontFormatException | IOException ex) {
            fontLoaded = false;
            throw new FontGeneratorException();
        }
        
        fontLoaded = true;
    }
}
