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

@Service
public class DetectorServiceImpl implements DetectorService {
    Logger logger = LoggerFactory.getLogger(DetectorServiceImpl.class);
    private final DetectorRepository detectorRepository;

    @Autowired
    public DetectorServiceImpl(DetectorRepository detectorRepository) {
        this.detectorRepository = detectorRepository;
    }

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

    @Override
    public String checkFirstTokenInName(String inputName) {
        if (inputName == null) {
            throw new IllegalArgumentException("Given name is null");
        } else if (inputName.isBlank()) {
            throw new IllegalArgumentException("Given name is blank");
        }

        String firstToken = inputName.split(" ")[0];
        if (isMale(firstToken)) {
            return Gender.MALE.toString();
        }
        if (isFemale(firstToken)) {
            return Gender.FEMALE.toString();
        }
        return Gender.INCONCLUSIVE.toString();
    }

    @Override
    public String checkAllTokensInName(String inputName) {
        if (inputName == null) {
            throw new IllegalArgumentException("Given name is null");
        } else if (inputName.isBlank()) {
            throw new IllegalArgumentException("Given name is blank");
        }

        String[] inputNameArr = inputName.split(" ");
        int genderMarker = getGenderMarker(inputNameArr);
        return getGenderFromMarker(genderMarker);
    }

    private String getGenderFromMarker(int genderMarker) {
        if (genderMarker > 0) {
            return Gender.MALE.toString();
        } else if (genderMarker < 0) {
            return Gender.FEMALE.toString();
        }
        return Gender.INCONCLUSIVE.toString();
    }

    private int getGenderMarker(String[] inputNameArr) {
        int genderMarker = 0;
        for (String token : inputNameArr) {
            if (isMale(token)) {
                genderMarker++;
            } else if (isFemale(token)) {
                genderMarker--;
            }
        }
        return genderMarker;
    }

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
