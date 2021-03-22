package pl.pozadr.genderdetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;
import pl.pozadr.genderdetector.service.DetectorService;

@RestController
public class DetectorController {
    private final DetectorService detectorService;

    @Autowired
    public DetectorController(DetectorService detectorService) {
        this.detectorService = detectorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void test() {
        System.out.println(detectorService.checkFirstTokenInName("Adrian Adam Ariel"));
        System.out.println(detectorService.checkAllTokensInName("Eva Tomas Anna Jacob"));
    }

}
