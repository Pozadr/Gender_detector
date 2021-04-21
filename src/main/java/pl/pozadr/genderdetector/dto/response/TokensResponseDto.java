package pl.pozadr.genderdetector.dto.response;

import java.util.List;

/**
 * Data Transfer Object.
 * Contains data about API request and response.
 */
public class TokensResponseDto {
    int requestPageNumber;
    int requestPageSize;
    List<String> tokens;

    public TokensResponseDto(Integer requestPageNumber, Integer requestPageSize, List<String> tokens) {
        this.requestPageNumber = requestPageNumber;
        this.requestPageSize = requestPageSize;
        this.tokens = tokens;
    }

    public int getRequestPageNumber() {
        return requestPageNumber;
    }

    public int getRequestPageSize() {
        return requestPageSize;
    }

    public List<String> getTokens() {
        return tokens;
    }
}
