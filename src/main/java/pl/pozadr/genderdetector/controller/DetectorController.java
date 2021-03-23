package pl.pozadr.genderdetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pozadr.genderdetector.dto.GenderDto;
import pl.pozadr.genderdetector.dto.TokensDto;
import pl.pozadr.genderdetector.service.DetectorService;
import pl.pozadr.genderdetector.validators.ControllerParametersValidator;

import java.util.List;


@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/gender-detector")
@RestController
public class DetectorController {
    private final DetectorService detectorService;

    @Autowired
    public DetectorController(DetectorService detectorService) {
        this.detectorService = detectorService;
    }


    @GetMapping("/v1")
    public ResponseEntity<GenderDto> getGender(@RequestParam String name, @RequestParam String method) {
        boolean parametersValid = ControllerParametersValidator.validateGetGenderParams(name, method);
        boolean isMethodFirstToken = ControllerParametersValidator.isMethodFirstToken(method);

        String gender = (parametersValid && isMethodFirstToken) ?
                detectorService.checkFirstTokenInName(name)
                : detectorService.checkAllTokensInName(name);

        return ResponseEntity.ok(new GenderDto(name, method, gender));
    }

    @GetMapping("/v1/tokens")
    public ResponseEntity<TokensDto> getTokens(@RequestParam int pageNo, @RequestParam int pageSize) {
        ControllerParametersValidator.validateGetTokensParams(pageNo, pageSize);
        List<String> tokens = detectorService.getTokens(pageNo, pageSize);
        return ResponseEntity.ok(new TokensDto(pageNo, pageSize, tokens));
    }

}
