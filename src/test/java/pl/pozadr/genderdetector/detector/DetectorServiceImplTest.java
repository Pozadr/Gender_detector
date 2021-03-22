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
                Arguments.of("Adrian Anna", "MALE", true, false),
                Arguments.of("Karolina Adam", "FEMALE", false, true),
                Arguments.of("Anna Katarzyna", "FEMALE", false, true),
                Arguments.of("Xxxxxxx Katarzyna", "INCONCLUSIVE", false, false),
                Arguments.of("Xxxxxxx Adam", "INCONCLUSIVE", false, false)
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
                Arguments.of("Adrian Adam", "MALE", true, false),
                Arguments.of("Adrian Anna", "INCONCLUSIVE", false, false),
                Arguments.of("Karolina Adam", "INCONCLUSIVE", false, false),
                Arguments.of("Anna Katarzyna", "FEMALE", false, true),
                Arguments.of("Anna Adrian Katarzyna Adam", "INCONCLUSIVE", false, false),
                Arguments.of("Anna Katarzyna", "FEMALE", false, true),
                Arguments.of("Xxxxxxx Katarzyna", "FEMALE", false, true),
                Arguments.of("Xxxx Yyyy Zzzz", "INCONCLUSIVE", false, false),
                Arguments.of("Xxxxxxx Adam", "MALE", true, false)
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
        List<String> expectedResults = dataGetTokensReturnsListoOfTokens();
        int pageNo = 1;
        int pageSize = 3;
        // when
        when(detectorRepository.getTokens(anyInt(), anyInt())).thenReturn(dataGetTokensReturnsListoOfTokens());
        List<String> result = detectorService.getTokens(pageNo, pageSize);
        // then
        Assertions.assertEquals(result, expectedResults);
    }

    private List<String> dataGetTokensReturnsListoOfTokens() {
        return List.of("Adrian", "Anna", "Karolina");
    }


}
