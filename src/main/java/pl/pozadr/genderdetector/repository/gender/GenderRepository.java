package pl.pozadr.genderdetector.repository.gender;


public interface GenderRepository {
    boolean isContaining(String pathToFile, String input);
}
