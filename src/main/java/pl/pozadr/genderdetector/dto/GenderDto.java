package pl.pozadr.genderdetector.dto;

public class GenderDto {
    private String requestName;
    private String requestMethod;
    private String gender;

    public GenderDto(String requestName, String requestMethod, String gender) {
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
