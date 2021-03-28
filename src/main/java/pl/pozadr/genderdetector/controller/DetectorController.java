package pl.pozadr.genderdetector.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pozadr.genderdetector.dto.GenderDto;
import pl.pozadr.genderdetector.dto.TokensDto;
import pl.pozadr.genderdetector.service.gender.GenderService;
import pl.pozadr.genderdetector.service.tokens.TokensService;
import pl.pozadr.genderdetector.validators.controller.ControllerGetGenderValidator;
import pl.pozadr.genderdetector.validators.controller.ControllerGetTokensValidator;

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
    @Value("${page-size-limit}")
    private int pageSizeLimit;

    @Autowired
    public DetectorController(GenderService genderService, TokensService tokensService) {
        this.genderService = genderService;
        this.tokensService = tokensService;
    }

    @GetMapping("/v1")
    @ApiOperation(value = "Checks the gender according to the method on the given name.", response = GenderDto.class)
    public ResponseEntity<GenderDto> getGender(
            @ApiParam(value = "Name to check gender.", required = true) @RequestParam String name,
            @ApiParam(value = "Method: FIRST_TOKEN / ALL_TOKENS", required = true) @RequestParam String method
    ) {
        ControllerGetGenderValidator.validateParams(name, method);
        String gender = genderService.checkGender(name, method);
        return ResponseEntity.ok(new GenderDto(name, method, gender));
    }

    @GetMapping("/v1/tokens")
    @ApiOperation(value = "Gets tokens in the pagination range.", response = TokensDto.class)
    public ResponseEntity<TokensDto> getTokens(
            @ApiParam(value = "Page number.", required = true) @RequestParam int pageNo,
            @ApiParam(value = "Page size", required = true) @RequestParam int pageSize
    ) {
        ControllerGetTokensValidator.validateGetTokensParams(pageNo, pageSize, pageSizeLimit);
        List<String> tokens = tokensService.getTokens(pageNo, pageSize);
        return ResponseEntity.ok(new TokensDto(pageNo, pageSize, tokens));
    }

}
