package pokemon.utils;

/**
 *
 * @author Marco Russodivito
 */
public class LanguageException extends Exception {

    public LanguageException() {
        super("Language Error");
    }
    
    public LanguageException(String pMessage) {
        super(pMessage);
    }
    
}
