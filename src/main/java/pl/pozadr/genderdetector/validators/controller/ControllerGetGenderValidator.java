package pl.pozadr.genderdetector.validators.controller;

import pl.pozadr.genderdetector.exceptions.MethodParameterNotValidException;

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
        boolean isMethodFirstToken = CheckMethod.FIRST_TOKEN.toString().equalsIgnoreCase(method.trim());
        boolean isMethodAllTokens = CheckMethod.ALL_TOKENS.toString().equalsIgnoreCase(method.trim());

        if (isMethodFirstToken || isMethodAllTokens) {
            return true;
        }
        throw new MethodParameterNotValidException("Given method is undefined.");
    }

    public static boolean isMethodFirstToken(String method) {
        return method.trim().equalsIgnoreCase(CheckMethod.FIRST_TOKEN.toString());
    }

}
