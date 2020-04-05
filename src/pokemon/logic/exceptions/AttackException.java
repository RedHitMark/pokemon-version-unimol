package pokemon.logic.exceptions;

/**
 *
 * @author Marco Russodivito
 */
public class AttackException extends Exception {

    public AttackException() {
        super("Attack Value Error");
    }
    
    public AttackException(String pMessage) {
        super(pMessage);
    }
    
}