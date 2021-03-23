package pl.pozadr.genderdetector.service;


import java.util.List;

public interface DetectorService {
    String checkFirstTokenInName(String inputName);

    String checkAllTokensInName(String inputName);

    List<String> getTokens(Integer pageNo, Integer pageSize);
}
