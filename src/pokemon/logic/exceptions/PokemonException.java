package pokemon.logic.exceptions;

/**
 *
 * @author Marco Russodivito
 */
public class PokemonException extends Exception {

    public PokemonException() {
        super("Pokemon Value Error");
    }
    
    public PokemonException(String pMessage) {
        super(pMessage);
    }
    
}
