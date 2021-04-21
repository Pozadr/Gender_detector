package pl.pozadr.genderdetector.dto.response;

/**
 * Data Transfer Object.
 * Contains data about API request and response.
 */
public class GenderResponseDto {
    private final String requestName;
    private final String requestMethod;
    private final String gender;

    public GenderResponseDto(String requestName, String requestMethod, String gender) {
        this.requestName = requestName;
        this.requestMethod = requestMethod;
        this.gender = gender;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getGender() {
        return gender;
    }

    public String getRequestMethod() {
        return requestMethod;
    }
}
