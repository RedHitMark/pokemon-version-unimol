package pokemon.utils;

/**
 * The class <code>Language</code> manages languages.
 * This class provides methods to set and get the language of the game.
 * 
 * @author Marco Russodivito
 */
public class Language {
    /**
     * English Language.
     */
    public static final int ENGLISH = 0;
    /**
     * Italian Language.
     */
    public static final int ITALIAN = 1;
    
    
    //Default language is ENGLISH
    private static int language = Language.ENGLISH;
    
    
    /**
     * Returns the language of the game. 
     * 
     * @return the language of the game.
     */
    public static int getLanguage() {
        return language;
    }
        
    /**
     * Sets the language of the game.
     * 
     * @param pLanguage the language to set.
     * @throws LanguageException if language is not <code>ENGLISH</code> or <code>ITALIAN</code>.
     */
    public static void setLanguage(int pLanguage) throws LanguageException {
        if ( !(pLanguage == Language.ENGLISH || pLanguage == Language.ITALIAN) )
           throw new LanguageException();
        
       language = pLanguage;
    }
}
