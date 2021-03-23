package pl.pozadr.genderdetector.validators;

import pl.pozadr.genderdetector.config.AppConstants;
import pl.pozadr.genderdetector.exceptions.MethodParameterException;
import pl.pozadr.genderdetector.exceptions.NameParameterException;
import pl.pozadr.genderdetector.exceptions.PageNumberParameterException;
import pl.pozadr.genderdetector.exceptions.PageSizeParameterException;

public final class ControllerParametersValidator {
    private ControllerParametersValidator() {
    }


    public static boolean validateGetTokensParams(int pageNo, int pageSize) {

        return isPageNoValid(pageNo) && isPageSizeValid(pageSize);
    }

    public static boolean validateGetGenderParams(String name, String method) {
        return isNameParamValid(name) && isMethodParamValid(method);
    }

    public static boolean isMethodFirstToken(String method) {
        return method.equalsIgnoreCase(CheckMethod.FIRST_TOKEN.toString());
    }


    private static boolean isPageSizeValid(int pageSize) {
        if (pageSize > AppConstants.PAGE_SIZE_LIMIT
                || pageSize < 0) {
            throw new PageSizeParameterException("Given pageSize is out of range. Minimum = 0 , maximum = "
                    + AppConstants.PAGE_SIZE_LIMIT);
        }
        return true;
    }

    private static boolean isPageNoValid(int pageNo) {
        if (pageNo < 0) {
            throw new PageNumberParameterException("Given pageNo is out of range. Minimum = 0 , maximum =  "
                    + Integer.MAX_VALUE);
        }
        return true;
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
