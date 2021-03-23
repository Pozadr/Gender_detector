package pl.pozadr.genderdetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pozadr.genderdetector.exceptions.PageSizeParameterException;
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

    // TODO: pageSize limit.

    @GetMapping("/v1")
    public ResponseEntity<String> getGender(@RequestParam String name, @RequestParam String method) {
        boolean parametersValid = ControllerParametersValidator.validateGetGenderParams(name, method);
        boolean isMethodFirstToken = ControllerParametersValidator.isMethodFirstToken(method);

        String gender = (parametersValid && isMethodFirstToken) ?
                detectorService.checkFirstTokenInName(name)
                : detectorService.checkAllTokensInName(name);

        return ResponseEntity.ok(gender);
    }

    @GetMapping("/v1/tokens")
    public ResponseEntity<List<String>> getTokens(@RequestParam int pageNo, @RequestParam int pageSize) {
        ControllerParametersValidator.validateGetTokensParams(pageNo, pageSize);
        return ResponseEntity.ok(detectorService.getTokens(pageNo, pageSize));
    }

}
