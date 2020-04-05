package pokemon.utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.imageio.ImageIO;

/**
 * Resources è un gestore delle risorse interne di un'applicazione. La classe
 * fornisce diversi metodi per leggere immagini, suoni o testo
 *
 * @author Oneiros
 */
public class Resources {

    //La classe di riferimento dalla quale leggere le risorse
    static Class source = Resources.class;

    /**
     * Costruttore privato (Resources è una classe statica)
     */
    private Resources() {
    }

    /**
     * Imposta la classe di riferimento dalla quale leggere le risorse. La
     * classe di riferimento di default è Resources stessa.
     *
     * @param source la classe di riferimento dalla quale leggere le risorse
     */
    public static void setSourceClass(Class source) {
        Resources.source = source;
    }

    /**
     * Restituisce un'immagine presente nelle risorse dell'applicazione
     *
     * @param path Il path relativo dell'immagine (ad esempio:
     * "/path/myImage.png")
     * @return L'immagine corrispondente al path
     */
    public static BufferedImage getImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getResource(path));
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
        return image;
    }

    /**
     * Restituisce un suono WAVE presente nelle risorse dell'applicazione
     *
     * @param path Il path relativo del suono (ad esempio: "/path/mySound.wav")
     * @return Il suono corrispondente al path
     */
    public static Sound getSound(String path) {
        Sound sound = null;
        try {
            //System.out.println("\n\n\n\n" + getResource(path).toURI() + "\n\n\n\n");
            //File file = new File(getResource(path).toURI());
            sound = new Sound(getResource(path));
        } catch (NullPointerException | SoundException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
        return sound;
    }

    /**
     * Restituisce il testo contenuto in un file di testo (.txt) presente nelle
     * risorse dell'applicazione
     *
     * @param path Il path relativo del file di testo (ad esempio:
     * "/path/myText.txt")
     * @return Il testo contenuto nel file
     */
    public static String getText(String path) {

        StringBuilder builder = new StringBuilder();

        try (InputStream byteStream = getResourceAsStream(path);
                InputStreamReader txtStream = new InputStreamReader(byteStream, "ISO-8859-1");
                BufferedReader reader = new BufferedReader(txtStream)) {

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }

        return builder.toString();
    }

    /**
     * Estrae un file interno al jar nella stessa cartella del jar e ne
     * restituisce il path. Nel caso in cui il programma risulti non essere
     * eseguito da un jar ma da un file .class, il metodo ritorna direttamente
     * il path del file presente nelle risorse del file .class
     *
     * @param path Il path del file interno al jar
     * @return Il path del file estratto
     * @throws java.io.IOException se viene generata una IOException
     */
    public static String extract(String path) throws IOException {
        return extract(path, null);
    }

    /**
     * Estrae un file interno al jar nella cartella specificata e ne restituisce
     * il path. Nel caso in cui il programma risulti non essere eseguito da un
     * jar ma da un file .class, il metodo ritorna direttamente il path del file
     * presente nelle risorse del file .class
     *
     * @param path Il path del file interno al jar
     * @param destinationPath Il path della cartella di destinazione
     * @return Il path del file estratto
     * @throws java.io.IOException se viene generata una IOException
     */
    public static String extract(String path, String destinationPath) throws IOException {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        String runningPath = getRunningPath();
        if (isRunningFromJar()) {
            if (destinationPath == null || destinationPath.isEmpty()) {
                destinationPath = runningPath.substring(0, runningPath.lastIndexOf("/"));
            }
            JarFile jar = new JarFile(runningPath);
            JarEntry file = jar.getJarEntry(path);
            String fileName = file.getName();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
            File destination = new File(destinationPath + File.separator + fileName);
            if (!destination.exists()) {
                if (file.isDirectory()) {
                    destination.mkdir();
                }
                try (
                        InputStream input = jar.getInputStream(file);
                        FileOutputStream output = new FileOutputStream(destination)) {
                    while (input.available() > 0) {
                        output.write(input.read());
                    }
                }
            }
            return destination.getAbsolutePath();
        } else {
            return runningPath + path;
        }
    }

    /**
     * Restituisce il path dal quale si sta eseguendo l'applicazione
     * @return il path dal quale si sta eseguendo l'applicazione
     */
    public static String getRunningPath() {
        String runningPath = Resources.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            return new URI(runningPath).getPath();
        } catch (URISyntaxException ex) {
            return runningPath;
        }
    }

    /**
     * @return True se si sta eseguendo l'applicazione da un file .jar, False
     * altrimenti
     */
    public static boolean isRunningFromJar() {
        String className = Resources.class.getName().replace('.', '/');
        String classJar = Resources.class.getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar:");
    }

    /**
     * Chiama il metodo getResource() sulla classe di riferimento
     *
     * @param resource Il path del file interno al jar
     * @return il path della classe di riferimento
     * @see java.lang.Class#getResource(java.lang.String)
     */
    public static URL getResource(String resource) {
        return Resources.source.getResource(resource);
    }

    /**
     * Chiama il metodo getResourceAsStream() sulla classe di riferimento
     *
     * @param resource Il path del file interno al jar
     * @return il path della classe di riferimento
     * @see java.lang.Class#getResourceAsStream(java.lang.String)
     */
    public static InputStream getResourceAsStream(String resource) {
        return Resources.source.getResourceAsStream(resource);
    }
}
