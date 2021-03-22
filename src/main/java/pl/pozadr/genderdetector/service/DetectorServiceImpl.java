package pl.pozadr.genderdetector.detector.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetectorServiceImpl implements DetectorService {

    @Override
    public String checkFirstTokenInName(String name) {
        return null;
    }

    @Override
    public String checkAllTokensInName(String name) {
        return null;
    }

    @Override
    public List<String> getTokens(Integer pageNo, Integer pageSize) {
        return null;
    }
}
