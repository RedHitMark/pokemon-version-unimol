package pokemon.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import javax.sound.sampled.*;

/**
 * La classe Sound modella, riproduce e gestisce un suono in formato WAVE.
 *
 * @author Lorenzo
 * @author Marco Russodivito - Volume management
 */
public class Sound {
    /**
     * Mute <code>Sound</code>.
     */
    public static final float MUTE = -80f;
    /**
     * Low <code>Sound</code>.
     */
    public static final float LOW = -6f;
    /**
     * Medium <code>Sound</code>.
     */
    public static final float MEDIUM = 0f;
    /**
     * High <code>Sound</code>.
     */
    public static final float HIGH = 6f;
    
    
    private static final Line.Info INFO = new Line.Info(Clip.class);
    private static HashMap<String, Sound> map;
    private URL soundUrl;
    private Clip readyClip;

    //default volume is MEDIUM
    private static float volume = Sound.MEDIUM;
    
    /**
     * Crea un nuovo suono a partire dal path assoluto di un file WAVE.
     *
     * @param path Il path assoluto di un file WAVE.
     * @throws SoundException Se il path non risulta puntare ad un corretto file
     * WAVE
     * @throws java.net.MalformedURLException Se il path è in un formato errato
     */
    public Sound(String path) throws SoundException, MalformedURLException {
        this(new URL(path));
    }

    /**
     * Crea un nuovo suono a partire da un file WAVE
     *
     * @param url Il file WAVE
     * @throws SoundException Se il file non risulta essere un corretto file
     * WAVE
     */
    public Sound(URL url) throws SoundException {
        if (url == null) {
            throw new SoundException("Cannot read " + url.getPath());
        }
        this.soundUrl = url;
        this.readyClip = this.getNewClip();
    }

    
    /**
     * Returns the volume of the game
     * @return the volume of the game
     * 
     * @author Marco Russodivito
     */
    public static int getVolume() {
        if (volume == Sound.MUTE)
            return 0;
        
        if (volume == Sound.LOW)
            return 1;
        
        if (volume == Sound.MEDIUM)
            return 2;
        
        return 3;
    }

    /**
     * Sets the volume of the game
     * @param pVolume the volume to set
     * 
     * @author Marco Russodivito
     */
    public static void setVolume(float pVolume) {
        Sound.volume = pVolume;
    }
    
    /**
     * Riproduce una volta il suono
     *
     * @return Un oggetto di tipo Clip che permette di gestire il suono
     */
    public Clip play() {
        return play(1);
    }

    /**
     * Riproduce all'infinito il suono
     *
     * @return Un oggetto di tipo Clip che permette di gestire il suono
     */
    public Clip loop() {
        return play(-1);
    }

    /**
     * Riproduce n volte il suono
     *
     * @param times Il numero di volte da riprodurre il suono (un numero
     * negativo provoca una riproduzione in loop)
     * @return Un oggetto di tipo Clip che permette di gestire il suono
     */
    public Clip play(int times) {
        Clip clip = null;
        try {
            clip = getNewClip();
        } catch (SoundException ex) {
            throw new RuntimeException(ex);
        }
        if (clip != null && times != 0) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
            clip.loop(times - 1);
        }
        return clip;
    }
    
    /**
     * Crea e restituisce un nuovo Clip del suono, pronto per essere riprodotto
     *
     * @return Un oggetto di tipo Clip che permette di gestire il suono
     * @throws SoundException Se non è possibile ottenere un Clip dal suono
     */
    public final Clip getNewClip() throws SoundException {
        try {
            if (this.readyClip == null) {
                this.readyClip = Sound.getNewClip(this.soundUrl);
            }
            Clip c = this.readyClip;
            this.readyClip = Sound.getNewClip(this.soundUrl);
            return c;
        } catch (SoundException ex) {
            this.readyClip = null;
            throw ex;
        }
    }

    /**
     * Restituisce una HashMap (sempre la stessa istanza) utilizzabile per
     * gestire con comodità una collezione di suoni. Ad ogni nuovo suono
     * aggiunto a tale struttura dati è necessario associare un nome mnemonico
     * per poter richiamare successivamente il suono stesso
     *
     * @return Una struttura dati di tipo HashMap per la gestione multipla di
     * suoni
     */
    public static HashMap<String, Sound> getMap() {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    /**
     * Crea e restituisce un nuovo Clip di un file WAVE, pronto per essere
     * riprodotto
     *
     * @param clipURL Il file WAVE dal quale ottenere un Clip
     * @return Un oggetto di tipo Clip che permette di gestire il suono
     * @throws SoundException Se non è possibile ottenere un Clip dal file
     */
    public static Clip getNewClip(URL clipURL) throws SoundException {
        Clip clip = null;
        try {
            clip = (Clip) AudioSystem.getLine(INFO);
            clip.open(AudioSystem.getAudioInputStream(clipURL));
        } catch (IOException | UnsupportedAudioFileException ex) {
            throw new SoundException(clipURL.getFile(), ex);
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
        return clip;
    }
}