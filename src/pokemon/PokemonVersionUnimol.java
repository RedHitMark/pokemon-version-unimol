package pokemon;

import pokemon.GUI.MainFrame;

/**
 * The main class of <b>Pokémon - Version Unimol</b>
 * 
 * @author Marco Russodivito
 */
public class PokemonVersionUnimol {
    
    /**
     * The starting point of game, create and show the <code>MainFrame</code>.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
