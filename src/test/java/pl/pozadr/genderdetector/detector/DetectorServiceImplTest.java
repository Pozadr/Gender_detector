package pl.pozadr.genderdetector.detector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.pozadr.genderdetector.detector.service.DetectorServiceImpl;

import java.util.stream.Stream;

class DetectorServiceImplTest {
    private DetectorServiceImpl detectorService;

    @BeforeEach
    private void setup() {
        detectorService = new DetectorServiceImpl();
    }

    @Test
    public void checkFirstToken_nameNull_shouldThrowsIllegalArgumentException() {
        // given
        String name = null;
        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> detectorService.checkFirstToken(name));
    }

    @Test
    public void checkFirstToken_nameBlank_shouldThrowsIllegalArgumentException() {
        // given
        String name = "";
        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> detectorService.checkFirstToken(name));
    }

    @ParameterizedTest
    @MethodSource("dataCheckFirstToken")
    public void checkFirstToken_shouldReturnMaleFemaleInconclusive(String inputName, String expectedResults) {
        // given
        // when
        String result = detectorService.checkFirstToken(inputName);
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


    @Test
    public void checkAllTokens_nameNull_shouldThrowsIllegalArgumentException() {
        // given
        String name = null;
        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> detectorService.checkAllTokens(name));
    }

    @Test
    public void checkAllTokens_nameBlank_shouldThrowsIllegalArgumentException() {
        // given
        String name = "";
        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> detectorService.checkAllTokens(name));
    }


    @ParameterizedTest
    @MethodSource("dataCheckAllTokens")
    public void checkAllTokens_shouldReturnMaleFemaleInconclusive(String inputName, String expectedResults) {
        // given
        // when
        String result = detectorService.checkAllTokens(inputName);
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

}
