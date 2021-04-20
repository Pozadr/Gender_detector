package pl.pozadr.genderdetector.service.gender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.genderdetector.repository.GenderRepository;
import pl.pozadr.genderdetector.util.CheckGenderMethods;
import pl.pozadr.genderdetector.util.Gender;


/**
 * Logic layer of application.
 */
@Service
public class GenderServiceImpl implements GenderService {
    Logger logger = LoggerFactory.getLogger(GenderServiceImpl.class);
    private final GenderRepository genderRepository;

    @Autowired
    public GenderServiceImpl(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Override
    public String checkGender(String inputName, String method) {
        if (isMethodFirstToken(method)) {
            return checkFirstTokenInName(inputName);
        }
        return checkAllTokensInName(inputName);
    }

    /**
     * Checks first token by given name.
     * Example: the given name: Adrian Anna Kowalski; response: MALE; cause first one: Adrian.
     *
     * @param inputName - input full name.
     * @return - gender: MALE/FEMALE/INCONCLUSIVE
     */
    private String checkFirstTokenInName(String inputName) {
        if (inputName == null) {
            throw new IllegalArgumentException("Given name is null");
        } else if (inputName.isBlank()) {
            throw new IllegalArgumentException("Given name is blank");
        }

        String firstToken = inputName.trim().split(" +")[0];
        if (isMale(firstToken)) {
            return Gender.MALE.toString();
        }
        if (isFemale(firstToken)) {
            return Gender.FEMALE.toString();
        }
        return Gender.INCONCLUSIVE.toString();
    }

    /**
     * Checks all tokens by given name.
     * Example: the given name: Adrian Adam Anna Kowalski; response: MALE; cause: Adrian, Adam > Anna.
     *
     * @param inputName - input full name.
     * @return - gender: MALE/FEMALE/INCONCLUSIVE
     */
    private String checkAllTokensInName(String inputName) {
        if (inputName == null) {
            throw new IllegalArgumentException("Given name is null");
        } else if (inputName.isBlank()) {
            throw new IllegalArgumentException("Given name is blank");
        }

        String[] inputNameArr = inputName.trim().split(" +");
        int genderMarker = getGenderMarker(inputNameArr);
        return getGenderFromGenderMarker(genderMarker);
    }

    /**
     * Encrypt gender marker to gender.
     * gender marker possitive -> MALE
     * gender marker negative -> FEMALE
     * gender marker equals 0 -> INCONCLUSIVE
     *
     * @param genderMarker - integer value of gender possibility.
     * @return - MALE/FEMALE/INCONCLUSIVE
     */
    private String getGenderFromGenderMarker(int genderMarker) {
        if (genderMarker > 0) {
            return Gender.MALE.toString();
        } else if (genderMarker < 0) {
            return Gender.FEMALE.toString();
        }
        return Gender.INCONCLUSIVE.toString();
    }

    /**
     * Creates the gender marker which describes possibility to be MALE/FEMALE/INCONCLUSIVE.
     *
     * @param inputNameArr - name split to tokens array
     * @return - integer value of gender possibility.
     */
    private int getGenderMarker(String[] inputNameArr) {
        int genderMarker = 0;
        for (String token : inputNameArr) {
            if (isMale(token.trim())) {
                genderMarker++;
            } else if (isFemale(token.trim())) {
                genderMarker--;
            }
        }
        return genderMarker;
    }

    private boolean isMale(String token) {
        return genderRepository.isContaining(token, Gender.MALE);
    }

    private boolean isFemale(String token) {
        return genderRepository.isContaining(token, Gender.FEMALE);
    }

    private boolean isMethodFirstToken(String method) {
        return method.trim().equalsIgnoreCase(CheckGenderMethods.FIRST_TOKEN.toString());
    }

}
