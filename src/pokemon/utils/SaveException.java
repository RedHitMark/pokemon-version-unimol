package pokemon.utils;

/**
 *
 * @author Marco Russodivito
 */
public class SaveException extends Exception {
    
    public SaveException() {
        super("Save Error");
    }
    
    public SaveException(String pMessage) {
        super(pMessage);
    }
}
