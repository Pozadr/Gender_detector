package pl.pozadr.genderdetector.dto.request;

import pl.pozadr.genderdetector.exceptions.MethodParameterNotValidException;
import pl.pozadr.genderdetector.util.GenderMethods;

import javax.validation.constraints.NotEmpty;

public class GenderRequestDto {
    @NotEmpty(message = "Given name can not be empty.")
    String name;

    @NotEmpty(message = "Given method can not be empty.")
    String method;

    public String getName() {
        return name;
    }

    public String getMethod() {
        return method;
    }

    public boolean isMethodParamValid() {
        boolean isMethodFirstToken = GenderMethods.FIRST_TOKEN.toString().equalsIgnoreCase(method.trim());
        boolean isMethodAllTokens = GenderMethods.ALL_TOKENS.toString().equalsIgnoreCase(method.trim());

        if (isMethodFirstToken || isMethodAllTokens) {
            return true;
        }
        throw new MethodParameterNotValidException("Given method is undefined.");
    }
}
