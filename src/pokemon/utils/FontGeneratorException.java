/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemon.utils;

/**
 *
 * @author Marco Russodivito
 */
public class FontGeneratorException extends Exception {

    public FontGeneratorException() {
        super("Font Generator Error");
    }
    
    public FontGeneratorException(String pMessage) {
        super(pMessage);
    }
    
}
