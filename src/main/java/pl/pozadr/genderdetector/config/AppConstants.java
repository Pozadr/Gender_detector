package pl.pozadr.genderdetector.config;

/**
 * Configuration constants for the entire application.
 */
public final class AppConstants {
    public static final String PATH_TO_MALE_FLAT_FILE = "./src/main/resources/flatDB/Male.txt";
    public static final String PATH_TO_FEMALE_FLAT_FILE = "./src/main/resources/flatDB/Female.txt";


    // TODO: different data path for .jar application and IDE tests needed. Below path for .jar build.
//    public static final String PATH_TO_MALE_FLAT_FILE = "./classes/flatDB/Male.txt";
//    public static final String PATH_TO_FEMALE_FLAT_FILE = "./classes/flatDB/Female.txt";


    private AppConstants() {

    }
}
