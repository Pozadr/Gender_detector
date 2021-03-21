package pl.pozadr.genderdetector.detector.service;

public interface DetectorService {
    String checkFirstToken(String name);

    String checkAllTokens(String name);
}
