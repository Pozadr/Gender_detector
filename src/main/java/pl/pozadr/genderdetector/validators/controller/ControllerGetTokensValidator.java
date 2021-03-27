package pl.pozadr.genderdetector.validators.controller;

import pl.pozadr.genderdetector.exceptions.MethodParameterNotValidException;

public class ControllerGetTokensValidator {
    private ControllerGetTokensValidator() {
    }

    public static boolean validateGetTokensParams(int pageNo, int pageSize, int pageSizeLimit) {
        return isPageNoValid(pageNo) && isPageSizeValid(pageSize, pageSizeLimit);
    }


    private static boolean isPageSizeValid(int pageSize, int pageSizeLimit) {
        if (pageSize > pageSizeLimit || pageSize < 0) {
            throw new MethodParameterNotValidException("Given pageSize is out of range. Minimum = 0 , maximum = "
                    + pageSizeLimit);
        }
        return true;
    }

    private static boolean isPageNoValid(int pageNo) {
        if (pageNo < 0) {
            throw new MethodParameterNotValidException("Given pageNo is out of range. Minimum = 0 , maximum =  "
                    + Integer.MAX_VALUE);
        }
        return true;
    }

}
