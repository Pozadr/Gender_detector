package pl.pozadr.genderdetector.validators;

import pl.pozadr.genderdetector.exceptions.MethodParameterException;
import pl.pozadr.genderdetector.exceptions.NameParameterException;

public final class ControllerParametersValidator {
    private ControllerParametersValidator() {
    }

    public static boolean isMethodFirstToken(String method) {
        return method.equalsIgnoreCase(CheckMethod.FIRST_TOKEN.toString());
    }

    public static boolean validateGetGenderParams(String name, String method) {
        return isNameParamValid(name) && isMethodParamValid(method);
    }

    private static boolean isNameParamValid(String name) {
        if (name.isBlank()) {
            throw new NameParameterException("Given name is blank.");
        }
        return true;
    }

    private static boolean isMethodParamValid(String method) {
        boolean isMethodFirstToken = CheckMethod.FIRST_TOKEN.toString().equalsIgnoreCase(method);
        boolean isMethodAllTokens = CheckMethod.ALL_TOKENS.toString().equalsIgnoreCase(method);

        if (isMethodFirstToken || isMethodAllTokens) {
            return true;
        }
        throw new MethodParameterException("Given method is undefined.");
    }

}
