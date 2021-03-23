package pl.pozadr.genderdetector.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class DetectorRepositoryImpl implements DetectorRepository {

    @Value("${algorithm-version}")
    private String algorithmVersion;
    Logger logger = LoggerFactory.getLogger(DetectorRepositoryImpl.class);

    @EventListener(ApplicationReadyEvent.class)
    public void algorithmVersionInfo() {
        switch (algorithmVersion.toUpperCase()) {
            case "BUFFERED_READER": {
                logger.info("Algorithm version: " + algorithmVersion);
                break;
            }
            default: {
                logger.info("Algorithm version: default -> Stream API");
                break;
            }
        }
    }

    @Override
    public boolean isContaining(String pathToFile, String input) {
        switch (algorithmVersion.toUpperCase()) {
            case "BUFFERED_READER": {
                logger.debug("Algorithm version: " + algorithmVersion);
                return isContainingBufferedReader(pathToFile, input);
            }
            default: {
                logger.debug("Algorithm version: default -> Stream API");
                return isContainingStreamApi(pathToFile, input);
            }
        }
    }

    @Override
    public List<String> getTokens(String pathToFile, long first, long last) {
        List<String> result = new ArrayList<>();
        long currentTokenPosition = 0L;

        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader(pathToFile))) {
            String fileLineContent;
            while ((fileLineContent = fileBufferReader.readLine()) != null) {
                if (currentTokenPosition >= first && currentTokenPosition <= last) {
                    result.add(fileLineContent);
                }
                currentTokenPosition++;
            }
        } catch (FileNotFoundException e) {
            logger.error("Error: file not found. {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Error during reading data. {}", e.getMessage());
        }
        return result;
    }

    private boolean isContainingBufferedReader(String pathToFile, String input) {
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

    private boolean isContainingStreamApi(String pathToFile, String input) {
        try (Stream<String> inputStream = Files.lines(Paths.get(pathToFile), StandardCharsets.UTF_8)) {
            return inputStream.anyMatch(line -> line.equalsIgnoreCase(input));
        } catch (IOException e) {
            logger.error("Error during reading data. {}", e.getMessage());
        }
        return false;
    }

}
