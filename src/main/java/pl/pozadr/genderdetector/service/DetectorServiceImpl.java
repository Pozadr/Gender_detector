package pl.pozadr.genderdetector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.genderdetector.repository.DetectorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetectorServiceImpl implements DetectorService {

    private DetectorRepository detectorRepository;
    @Autowired
    public DetectorServiceImpl(DetectorRepository detectorRepository) {
        this.detectorRepository = detectorRepository;
    }

    @Override
    public String checkFirstTokenInName(String name) {
        return "";
    }

    @Override
    public String checkAllTokensInName(String name) {
        return "";
    }

    @Override
    public List<String> getTokens(Integer pageNo, Integer pageSize) {
        return new ArrayList<>();
    }
}
