package pl.pozadr.genderdetector.dto;

import java.util.List;

/**
 * Data Transfer Object.
 * Contains data about API request and response.
 */
public class TokensDto {
    Integer requestPageNumber;
    Integer requestPageSize;
    List<String> tokens;

    public TokensDto(Integer requestPageNumber, Integer requestPageSize, List<String> tokens) {
        this.requestPageNumber = requestPageNumber;
        this.requestPageSize = requestPageSize;
        this.tokens = tokens;
    }

    public Integer getRequestPageNumber() {
        return requestPageNumber;
    }

    public Integer getRequestPageSize() {
        return requestPageSize;
    }

    public List<String> getTokens() {
        return tokens;
    }
}
