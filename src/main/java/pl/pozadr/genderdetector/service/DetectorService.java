package pl.pozadr.genderdetector.detector.service;

import java.util.List;

public interface DetectorService {
    String checkFirstTokenInName(String name);

    String checkAllTokensInName(String name);

    List<String> getTokens(Integer pageNo, Integer pageSize);
}
