package pl.pozadr.genderdetector.repository;

import pl.pozadr.genderdetector.util.Gender;

import java.util.List;

public interface TokensRepository {
    List<String> getTokens(Gender gender, long first, long last);
}
