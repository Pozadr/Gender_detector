package pl.pozadr.genderdetector.service.tokens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.genderdetector.repository.TokensRepository;
import pl.pozadr.genderdetector.util.Gender;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokensServiceImpl implements TokensService {
    private final TokensRepository tokensRepository;

    @Autowired
    public TokensServiceImpl(TokensRepository tokensRepository) {
        this.tokensRepository = tokensRepository;
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
        return new ArrayList<>(tokensRepository.getTokens(Gender.MALE, firstToken, lastToken));
    }

    private List<String> getFemaleTokensFromRange(long firstToken, long lastToken) {
        return new ArrayList<>(tokensRepository.getTokens(Gender.FEMALE, firstToken, lastToken));
    }

}
