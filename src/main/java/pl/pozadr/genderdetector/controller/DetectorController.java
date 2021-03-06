package pl.pozadr.genderdetector.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pozadr.genderdetector.dto.request.GenderRequestDto;
import pl.pozadr.genderdetector.dto.request.TokensRequestDto;
import pl.pozadr.genderdetector.dto.response.GenderResponseDto;
import pl.pozadr.genderdetector.dto.response.TokensResponseDto;
import pl.pozadr.genderdetector.service.gender.GenderService;
import pl.pozadr.genderdetector.service.tokens.TokensService;

import javax.validation.Valid;
import java.util.List;

/**
 * Application API. Provides a description of Swagger for readability in the Swagger UI.
 * http://localhost:8080/swagger-ui.html
 */
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/gender-detector")
@RestController
public class DetectorController {
    private final GenderService genderService;
    private final TokensService tokensService;

    @Autowired
    public DetectorController(GenderService genderService, TokensService tokensService) {
        this.genderService = genderService;
        this.tokensService = tokensService;
    }

    @PostMapping("/v1")
    @ApiOperation(value = "Checks the gender according to the method on the given name.", response = GenderResponseDto.class)
    public ResponseEntity<GenderResponseDto> getGender(@RequestBody @Valid GenderRequestDto genderRequestDto) {
        genderRequestDto.isMethodParamValid();
        String gender = genderService.checkGender(genderRequestDto);
        return ResponseEntity.ok(new GenderResponseDto(
                genderRequestDto.getName(), genderRequestDto.getMethod(), gender));
    }

    @PostMapping("/v1/tokens")
    @ApiOperation(value = "Gets tokens in the pagination range.", response = TokensResponseDto.class)
    public ResponseEntity<TokensResponseDto> getTokens(@RequestBody @Valid TokensRequestDto tokensRequestDto) {
        List<String> tokens = tokensService.getTokens(tokensRequestDto);
        return ResponseEntity.ok(new TokensResponseDto(tokensRequestDto.getPageNo(), tokensRequestDto.getPageSize(),
                tokens));
    }

}
