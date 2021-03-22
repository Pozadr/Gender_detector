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

    // TODO: pageSize limit.

    @EventListener(ApplicationReadyEvent.class)
    public void test() {
        System.out.println(detectorService.checkFirstTokenInName("Adrian Adam Ariel")); // MALE
        System.out.println(detectorService.checkFirstTokenInName("Eva Adam Ariel")); // FEMALE
        System.out.println(detectorService.checkFirstTokenInName("xxxx Adam Ariel")); // INCONCLUSIVE

        System.out.println(detectorService.checkAllTokensInName("Eva Tomas Anna Trinity")); // FEMALE
        System.out.println(detectorService.checkAllTokensInName("Adrian Tomas Anna Henry")); // MALE
        System.out.println(detectorService.checkAllTokensInName("Eva Tomas Adrian Trinity")); // INCONCLUSIVE

        System.out.println();

        detectorService.getTokens(3, 200).forEach(System.out::println);
    }

}
