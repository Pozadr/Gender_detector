package pl.pozadr.genderdetector.detector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.pozadr.genderdetector.repository.DetectorRepository;
import pl.pozadr.genderdetector.repository.DetectorRepositoryImpl;
import pl.pozadr.genderdetector.service.DetectorServiceImpl;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class DetectorServiceImplTest {
    private DetectorServiceImpl detectorService;
    private DetectorRepository detectorRepository;

    @BeforeEach
    private void setup() {
        detectorRepository = mock(DetectorRepositoryImpl.class);
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
    public void checkFirstToken_shouldReturnMaleFemaleInconclusive(String inputName, String expectedResults) {
        // given
        // when
        String result = detectorService.checkFirstTokenInName(inputName);
        // then
        Assertions.assertEquals(result, expectedResults);
    }

    private static Stream<Arguments> dataCheckFirstToken() {
        return Stream.of(
                Arguments.of("Adrian Adam", "MALE"),
                Arguments.of("Adrian Anna", "MALE"),
                Arguments.of("Karolina Adam", "FEMALE"),
                Arguments.of("Anna Katarzyna", "FEMALE"),
                Arguments.of("Xxxxxxx Katarzyna", "INCONCLUSIVE"),
                Arguments.of("Xxxxxxx Adam", "INCONCLUSIVE")
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
    public void checkAllTokens_shouldReturnMaleFemaleInconclusive(String inputName, String expectedResults) {
        // given
        // when
        String result = detectorService.checkAllTokensInName(inputName);
        // then
        Assertions.assertEquals(result, expectedResults);
    }

    private static Stream<Arguments> dataCheckAllTokens() {
        return Stream.of(
                Arguments.of("Adrian Adam", "MALE"),
                Arguments.of("Adrian Anna", "INCONCLUSIVE"),
                Arguments.of("Karolina Adam", "INCONCLUSIVE"),
                Arguments.of("Anna Katarzyna", "FEMALE"),
                Arguments.of("Anna Adrian Katarzyna Adam", "INCONCLUSIVE"),
                Arguments.of("Anna Katarzyna", "FEMALE"),
                Arguments.of("Xxxxxxx Katarzyna", "FEMALE"),
                Arguments.of("Xxxx Yyyy Zzzz", "INCONCLUSIVE"),
                Arguments.of("Xxxxxxx Adam", "MALE")
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
