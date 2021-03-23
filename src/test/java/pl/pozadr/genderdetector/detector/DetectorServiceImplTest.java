package pl.pozadr.genderdetector.detector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.pozadr.genderdetector.config.AppConstants;
import pl.pozadr.genderdetector.repository.DetectorRepository;
import pl.pozadr.genderdetector.service.DetectorServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class DetectorServiceImplTest {
    private DetectorServiceImpl detectorService;
    private DetectorRepository detectorRepository;

    @BeforeEach
    private void setup() {
        detectorRepository = mock(DetectorRepository.class);
        detectorService = new DetectorServiceImpl(detectorRepository);
    }

    @ParameterizedTest
    @MethodSource("dataCheckFirstTokenIllegalArgumentException")
    public void checkFirstToken_shouldThrowsIllegalArgumentException(String name) {
        // given
        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> detectorService.checkFirstTokenInName(name));
    }

    private static Stream<Arguments> dataCheckFirstTokenIllegalArgumentException() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of("")
        );
    }

    @ParameterizedTest
    @MethodSource("dataCheckFirstToken")
    public void checkFirstToken_shouldReturnMaleFemaleInconclusive(String inputName, String expectedResults,
                                                                   boolean repoIsContainingMale,
                                                                   boolean repoIsContainingFemale) {
        // given
        // when
        when(detectorRepository.isContaining(eq(AppConstants.PATH_TO_MALE_FLAT_FILE), anyString()))
                .thenReturn(repoIsContainingMale);
        when(detectorRepository.isContaining(eq(AppConstants.PATH_TO_FEMALE_FLAT_FILE), anyString()))
                .thenReturn(repoIsContainingFemale);

        String result = detectorService.checkFirstTokenInName(inputName);
        // then
        Assertions.assertEquals(expectedResults, result);
    }

    private static Stream<Arguments> dataCheckFirstToken() {
        return Stream.of(
                Arguments.of("Adrian Adam", "MALE", true, false),
                Arguments.of("   Adrian    Adam   ", "MALE", true, false),
                Arguments.of("ADriAn ANnA", "MALE", true, false),
                Arguments.of("   AdRIaN  AdrIan    marIA      AnNa  ", "MALE", true, false),

                Arguments.of("Anna Katarzyna", "FEMALE", false, true),
                Arguments.of("   Anna     Katarzyna     ", "FEMALE", false, true),
                Arguments.of("KaROlinA AdAm", "FEMALE", false, true),
                Arguments.of("    ANNa    KataRZyna   ", "FEMALE", false, true),

                Arguments.of("Xxxx Katarzyna", "INCONCLUSIVE", false, false),
                Arguments.of("   Xxxx    Katarzyna    ", "INCONCLUSIVE", false, false),
                Arguments.of("XxXXxZx ADaM", "INCONCLUSIVE", false, false),
                Arguments.of("     XxXXxZx    ADaM    ", "INCONCLUSIVE", false, false)
        );
    }


    @ParameterizedTest
    @MethodSource("dataCheckAllTokensIllegalArgumentException")
    public void checkAllTokens_shouldThrowsIllegalArgumentException(String name) {
        // given
        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> detectorService.checkAllTokensInName(name));
    }

    private static Stream<Arguments> dataCheckAllTokensIllegalArgumentException() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of("")
        );
    }

    @ParameterizedTest
    @MethodSource("dataCheckAllTokens")
    public void checkAllTokens_shouldReturnMaleFemaleInconclusive(String inputName, String expectedResults,
                                                                  boolean repoIsContainingMale,
                                                                  boolean repoIsContainingFemale) {
        // given
        // when
        when(detectorRepository.isContaining(eq(AppConstants.PATH_TO_MALE_FLAT_FILE), anyString()))
                .thenReturn(repoIsContainingMale);
        when(detectorRepository.isContaining(eq(AppConstants.PATH_TO_FEMALE_FLAT_FILE), anyString()))
                .thenReturn(repoIsContainingFemale);
        String result = detectorService.checkAllTokensInName(inputName);
        // then
        Assertions.assertEquals(expectedResults, result);
    }

    private static Stream<Arguments> dataCheckAllTokens() {
        return Stream.of(
                Arguments.of("Adrian Adam Anna", "MALE", true, false),
                Arguments.of("  Adrian    Adam    Anna    ", "MALE", true, false),
                Arguments.of("ADRIan AdAM ANnA", "MALE", true, false),
                Arguments.of("    ADRIan    AdAM   ANnA   ", "MALE", true, false),

                Arguments.of("Adrian Anna", "INCONCLUSIVE", false, false),
                Arguments.of("Karolina Adam", "INCONCLUSIVE", false, false),
                Arguments.of("  Karolina   Adam   ", "INCONCLUSIVE", false, false),
                Arguments.of("KaROlinA ADAm", "INCONCLUSIVE", false, false),
                Arguments.of("   KaROlinA    ADAm  xxXXZZzz", "INCONCLUSIVE", false, false),
                Arguments.of("Xxxxxxx Katarzyna", "INCONCLUSIVE", false, false),
                Arguments.of("Xxxx Yyyy Zzzz", "INCONCLUSIVE", false, false),
                Arguments.of("Xxxxxxx Adam", "INCONCLUSIVE", false, false),
                Arguments.of("  ANna    AdriAn    KaTArzyna   ADAm   ", "INCONCLUSIVE", false, false),

                Arguments.of("Anna Katarzyna", "FEMALE", false, true),
                Arguments.of("   Anna   Katarzyna   ", "FEMALE", false, true),
                Arguments.of("   AnnA    KaTArzyna   ", "FEMALE", false, true)
        );
    }

    @ParameterizedTest
    @MethodSource("dataGetTokensIllegalArgumentException")
    public void getTokens_shouldThrowsIllegalArgumentException(Integer pageNo, Integer pageSize) {
        // given
        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> detectorService.getTokens(pageNo, pageSize));
    }

    private static Stream<Arguments> dataGetTokensIllegalArgumentException() {
        return Stream.of(
                Arguments.of(null, 4),
                Arguments.of(1, null)
        );
    }

    @Test
    public void getTokens_shouldReturnListOfTokens() {
        // given
        List<String> expectedResults = getExpectedResults();
        int pageNo = 1;
        int pageSize = expectedResults.size();
        // when
        when(detectorRepository.getTokens(eq(AppConstants.PATH_TO_MALE_FLAT_FILE), anyLong(), anyLong())).
                thenReturn(mockDataMales());
        when(detectorRepository.getTokens(eq(AppConstants.PATH_TO_FEMALE_FLAT_FILE), anyLong(), anyLong())).
                thenReturn(mockDataFemales());
        List<String> result = detectorService.getTokens(pageNo, pageSize);
        // then
        Assertions.assertEquals(expectedResults, result);
    }

    private List<String> getExpectedResults() {
        List<String> maleResult = mockDataMales();
        List<String> femaleResult = mockDataFemales();
        List<String> expectedResults = new ArrayList<>();
        expectedResults.addAll(maleResult);
        expectedResults.addAll(femaleResult);
        return expectedResults;
    }

    private List<String> mockDataMales() {
        return List.of("Adrian", "Karol", "Steve");
    }

    private List<String> mockDataFemales() {
        return List.of("Karolina", "Anna", "Angelika");
    }


}
