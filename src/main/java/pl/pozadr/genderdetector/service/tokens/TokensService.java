package pl.pozadr.genderdetector.service.tokens;

import java.util.List;

public interface TokensService {
    List<String> getTokens(Integer pageNo, Integer pageSize);
}
