package pl.pozadr.genderdetector.validators.controller;

import pl.pozadr.genderdetector.exceptions.MethodParameterNotValidException;
import pl.pozadr.genderdetector.util.CheckGenderMethods;

public class ControllerGetGenderValidator {
    private ControllerGetGenderValidator() {
    }

    public static boolean validateParams(String name, String method) {
        return isNameParamValid(name) && isMethodParamValid(method);
    }


    private static boolean isNameParamValid(String name) {
        if (name.isBlank()) {
            throw new MethodParameterNotValidException("Given name is blank.");
        }
        return true;
    }

    private static boolean isMethodParamValid(String method) {
        boolean isMethodFirstToken = CheckGenderMethods.FIRST_TOKEN.toString().equalsIgnoreCase(method.trim());
        boolean isMethodAllTokens = CheckGenderMethods.ALL_TOKENS.toString().equalsIgnoreCase(method.trim());

        if (isMethodFirstToken || isMethodAllTokens) {
            return true;
        }
        throw new MethodParameterNotValidException("Given method is undefined.");
    }

}
