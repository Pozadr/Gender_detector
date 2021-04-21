package pl.pozadr.genderdetector.service.tokens;

import pl.pozadr.genderdetector.dto.request.TokensRequestDto;

import java.util.List;

public interface TokensService {
    List<String> getTokens(TokensRequestDto tokensRequestDto);
}
