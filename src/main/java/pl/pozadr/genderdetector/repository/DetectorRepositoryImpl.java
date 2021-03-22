package pl.pozadr.genderdetector.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DetectorRepositoryImpl implements DetectorRepository{

    @Override
    public boolean isContaining(String pathToFile, String input) {
        return false;
    }

    @Override
    public List<String> getTokens(int first, int last) {
        return new ArrayList<>();
    }
}
