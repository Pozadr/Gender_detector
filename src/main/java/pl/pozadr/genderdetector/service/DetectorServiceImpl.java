package pl.pozadr.genderdetector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.genderdetector.config.AppConstants;
import pl.pozadr.genderdetector.repository.DetectorRepository;
import pl.pozadr.genderdetector.util.Gender;

import java.util.ArrayList;
import java.util.List;

/**
 * Logic layer of application.
 */
@Service
public class DetectorServiceImpl implements DetectorService {
    Logger logger = LoggerFactory.getLogger(DetectorServiceImpl.class);
    private final DetectorRepository detectorRepository;

    @Autowired
    public DetectorServiceImpl(DetectorRepository detectorRepository) {
        this.detectorRepository = detectorRepository;
    }

    /**
     * Returns list of male and female tokens.
     * Uses pagination to get tokens from flatFile.
     *
     * @param pageNo   - pagination page number
     * @param pageSize - pagination page size
     * @return - list of tokens
     */
    @Override
    public List<String> getTokens(Integer pageNo, Integer pageSize) {
        if (pageNo == null) {
            throw new IllegalArgumentException("Given pageNo is null");
        }
        if (pageSize == null) {
            throw new IllegalArgumentException("Given pageSize is null");
        }
        return getHalfMaleAndHalfFemaleTokens(pageNo, pageSize);
    }

    /**
     * Checks first token by given name.
     * Example: the given name: Adrian Anna Kowalski; response: MALE; cause first one: Adrian.
     *
     * @param inputName - input full name.
     * @return - gender: MALE/FEMALE/INCONCLUSIVE
     */
    @Override
    public String checkFirstTokenInName(String inputName) {
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
    @Override
    public String checkAllTokensInName(String inputName) {
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

    /**
     * Uses pagination to get tokens from flatFile.
     * Returns list of tokens in which half are the male tokens and half are the female tokens.
     *
     * @param pageNo   - pagination page number
     * @param pageSize - pagination page size
     * @return - list of tokens
     */
    private List<String> getHalfMaleAndHalfFemaleTokens(Integer pageNo, Integer pageSize) {
        long start = getPaginationStart(pageNo, pageSize);
        long end = getPaginationEnd(start, pageSize);

        List<String> maleTokens = getMaleTokensFromRange(start, end);
        List<String> femaleTokens =
                (pageSize % 2 == 0) ? getFemaleTokensFromRange(start, end)
                        : getFemaleTokensFromRange(start, end - 1);

        List<String> allTokens = new ArrayList<>();
        allTokens.addAll(maleTokens);
        allTokens.addAll(femaleTokens);
        return allTokens;
    }

    private long getPaginationStart(Integer pageNo, Integer pageSize) {
        return (pageNo == 1) ? 1 : Math.round(pageNo * pageSize / 4.0) + 1;
    }

    private long getPaginationEnd(long start, Integer pageSize) {
        return (start == 1) ? Math.round(pageSize / 2.0) : start - 1 + Math.round(pageSize / 2.0);
    }

    private List<String> getMaleTokensFromRange(long firstToken, long lastToken) {
        return new ArrayList<>(detectorRepository.getTokens(AppConstants.PATH_TO_MALE_FLAT_FILE, firstToken, lastToken));
    }

    private List<String> getFemaleTokensFromRange(long firstToken, long lastToken) {
        return new ArrayList<>(detectorRepository.getTokens(AppConstants.PATH_TO_FEMALE_FLAT_FILE, firstToken, lastToken));
    }

    private boolean isMale(String oneToken) {
        return detectorRepository.isContaining(AppConstants.PATH_TO_MALE_FLAT_FILE, oneToken);
    }

    private boolean isFemale(String oneToken) {
        return detectorRepository.isContaining(AppConstants.PATH_TO_FEMALE_FLAT_FILE, oneToken);
    }

}
