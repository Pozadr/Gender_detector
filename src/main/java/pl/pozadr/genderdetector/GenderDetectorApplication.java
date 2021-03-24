package pl.pozadr.genderdetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Gender detector application.
 * Provides API with features:
 * <ul>
 *   <li>Detects gender for given name.</li>
 *   <li>Returns all available tokens.</li>
 * </ul>
 *
 * @author - Adrian Pozorski
 * @see <a href=https://github.com/Pozadr</a>
 * @since 2021-03-24
 */
@SpringBootApplication
public class GenderDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenderDetectorApplication.class, args);
    }

}
