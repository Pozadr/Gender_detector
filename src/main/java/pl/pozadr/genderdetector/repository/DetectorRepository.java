package pl.pozadr.genderdetector.repository;

import java.util.List;

public interface DetectorRepository {
    boolean isContaining(String pathToFile, String input);
    List<String> getTokens(int first, int last);
}
