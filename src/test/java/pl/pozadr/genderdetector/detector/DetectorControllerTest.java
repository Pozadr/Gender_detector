package pl.pozadr.genderdetector.detector;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.pozadr.genderdetector.controller.DetectorController;
import pl.pozadr.genderdetector.service.DetectorService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(DetectorController.class)
public class DetectorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private DetectorService detectorService;


    @ParameterizedTest
    @MethodSource("dataBadRequest")
    public void shouldReturnBadRequestStatusWithMessage(String name, String method, String expectedMessage)
            throws Exception {
        // given
        // when
        // then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/gender-detector/v1")
                .param("name", name)
                .param("method", method))

                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn();

        String resultMessage = mvcResult.getResolvedException().getMessage();
        Assertions.assertEquals(expectedMessage, resultMessage);
    }

    private static Stream<Arguments> dataBadRequest() {
        return Stream.of(
                Arguments.of("Adrian Anna Adam", "UNDEFINED", "Given method is undefined."),
                Arguments.of("Adrian Anna Adam", "", "Given method is undefined."),
                Arguments.of("Adrian Anna Adam", "null", "Given method is undefined."),
                Arguments.of("", "FIRST_TOKEN", "Given name is blank."),
                Arguments.of("", "ALL_TOKENS", "Given name is blank.")
        );
    }

    @Test
    public void shouldReturnGenderAndStatusOk() throws Exception {
        // given
        String name = "Adrian Anna Adam";
        String method = "FIRST_TOKEN";
        String expectedGenderResponse = "MALE";
        // when
        when(detectorService.checkFirstTokenInName(any())).thenReturn(expectedGenderResponse);
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/gender-detector/v1")
                .param("name", name)
                .param("method", method))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Is.is(expectedGenderResponse)));
    }

    @Test
    public void shouldReturnTokens() throws Exception {
        // given
        List<String> tokens = List.of("Adrian", "Anna", "Adam");
        int lastGivenToken = tokens.size() - 1;
        String lastTokenJsonExpression = "$[" + (tokens.size() - 1) + "]";
        // when
        when(detectorService.getTokens(any(), any())).thenReturn(tokens);
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/gender-detector/v1/tokens")
                .param("pageNo", "1")
                .param("pageSize", String.valueOf(tokens.size())))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Is.is(tokens.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Is.is(tokens.get(0))))
                .andExpect(MockMvcResultMatchers.jsonPath(lastTokenJsonExpression, Is.is(tokens.get(lastGivenToken))));
    }


}
