package pokemon.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import pokemon.logic.characters.Player;
import pokemon.logic.characters.Pokemon;
import pokemon.logic.exceptions.PokemonException;

/**
 * The class <code>Save</code> manage the file "Pokémon - Version Unimol.sav" to save and load the game.
 * 
 * @author Marco Russodivito
 */
public class Save {

    private static final String FILE_NAME = "\\Pokémon - Version Unimol.sav";
    
    /**
     * Reads a <code>Player</code> from file "Pokémon - Version Unimol.sav".
     * 
     * @return the <code>Player</code> loaded form file PokémonVersionUnimol.sav.
     * @throws SaveException if the file filePokémonVersionUnimol.sav. contains illegal values.
     */
    public static Player load() throws SaveException {
        File save;
        
        //player values
        String name;
        int type, x, y, level, exp, hpRemaing;
        boolean piacentinoDefeated, palombaDefeated, olivetoDefeated;
        int number;
        
        try {
            save = new File(System.getProperty("user.dir") + Save.FILE_NAME);
            
            if(!save.exists()) {
                throw new SaveException("Save not present");
            }
            
            if(!save.canRead()) {
                save.setReadable(true);
            }
            
            BufferedReader saveReader = new BufferedReader(new FileReader(save));
            
            name = saveReader.readLine();//first line
            type = Integer.parseInt(saveReader.readLine().replace(" ", ""));//second line
            x = Integer.parseInt(saveReader.readLine().replace(" ", "")); //third line
            y = Integer.parseInt(saveReader.readLine().replace(" ", "")); // forth
            level = Integer.parseInt(saveReader.readLine().replace(" ", "")); //fifth line
            exp = Integer.parseInt(saveReader.readLine().replace(" ", "")); //sixth line
            hpRemaing = Integer.parseInt(saveReader.readLine().replace(" ", "")); //seventh line
            piacentinoDefeated = saveReader.readLine().replace(" ", "").equals("true"); //eight line
            palombaDefeated = saveReader.readLine().replace(" ", "").equals("true"); //ninth line
            olivetoDefeated = saveReader.readLine().replace(" ", "").equals("true"); //tenth line
            number = Integer.parseInt(saveReader.readLine().replace(" ", "")); //eleventh line
            
            saveReader.close();
            return new Player(name, type, x, y, level, exp, hpRemaing, piacentinoDefeated, palombaDefeated, olivetoDefeated, number);
        } catch (IOException | NumberFormatException | PokemonException ex) {
            throw new SaveException();
        }
    }
    
    /**
     * Checks if the file "Pokémon - Version Unimol.sav" is loadable. 
     * 
     * @return <code>true</code> if the file PokémonVersionUnimol.sav is loadable; <code>false</code> otherwise.
     */
    public static boolean isSaveLoadable() {
        try {
            Save.load();
            return true;
        } catch(SaveException see) {
            return false;
        }
    }
    
    /**
     * Saves the <code>Player</code> in the file "Pokémon - Version Unimol.sav".
     * 
     * @param pPlayer <code>Player</code> to save.
     * @throws SaveException if the <code>Player</code> cannot be saved.
     */
    public static void save(Pokemon pPlayer) throws SaveException {
        File save;
        
        try {
            save = new File(System.getProperty("user.dir") + Save.FILE_NAME);
            
            if(!save.exists()) {
                save.createNewFile();
            }
            if(!save.canWrite()) {
                save.setWritable(true);
            }
            
            BufferedWriter saveWriter = new BufferedWriter(new FileWriter(save));
            saveWriter.write(pPlayer.toString());
            saveWriter.close();
            
            //to avoid malicious modification
            save.setWritable(false);
        } catch(IOException ex) {
            throw new SaveException();
        }
        
    }
}
