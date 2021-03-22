package pl.pozadr.genderdetector.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DetectorRepositoryImpl implements DetectorRepository {
    Logger logger = LoggerFactory.getLogger(DetectorRepositoryImpl.class);

    @Override
    public boolean isContaining(String pathToFile, String input) {
        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader(pathToFile))) {
            String fileLineContent;
            while ((fileLineContent = fileBufferReader.readLine()) != null) {
                if (fileLineContent.equalsIgnoreCase(input)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Error: file not found. {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Error during reading data. {}", e.getMessage());
        }
        return false;
    }

    @Override
    public List<String> getTokens(int first, int last) {
        return new ArrayList<>();
    }
}
