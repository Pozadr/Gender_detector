package pl.pozadr.genderdetector.repository.tokens;

import java.util.List;

public interface TokensRepository {
    List<String> getTokens(String pathToFile, long first, long last);
}
