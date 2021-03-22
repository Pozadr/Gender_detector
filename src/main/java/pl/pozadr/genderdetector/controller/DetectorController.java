package pl.pozadr.genderdetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.pozadr.genderdetector.service.DetectorService;

@RestController
public class DetectorController {
    private DetectorService detectorService;

    @Autowired
    public DetectorController(DetectorService detectorService) {
        this.detectorService = detectorService;
    }


}
