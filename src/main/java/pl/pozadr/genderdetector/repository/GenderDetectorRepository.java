package pl.pozadr.genderdetector.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerErrorException;
import pl.pozadr.genderdetector.util.Gender;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class GenderDetectorRepository implements GenderRepository, TokensRepository {
    Logger logger = LoggerFactory.getLogger(GenderDetectorRepository.class);
    @Value("${algorithm-version}")
    private String algorithmVersion;
    @Value("${female-classpath}")
    private String femaleClasspath;
    @Value("${male-classpath}")
    private String maleClasspath;
    private final ResourceLoader resourceLoader;


    public GenderDetectorRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    /**
     * Returns tokens from file in requested range.
     *
     * @param first - first position of data in file.
     * @param last  - last position of data in file.
     * @return - List<String> tokens
     */
    @Override
    public List<String> getTokens(Gender gender, long first, long last) {
        List<String> result = new ArrayList<>();
        long currentTokenPosition = 1L;
        Resource resource = getResource(gender);
        InputStream inputStream;

        try {
            inputStream = resource.getInputStream();
        } catch (IOException exp) {
            logger.error("Error: wrong path to file. {}", exp.getMessage());
            throw new ServerErrorException("File processing error.", exp);
        }

        try (BufferedReader fileBufferReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String fileLineContent;
            while ((fileLineContent = fileBufferReader.readLine()) != null) {
                if (currentTokenPosition >= first && currentTokenPosition <= last) {
                    result.add(fileLineContent);
                }
                currentTokenPosition++;
            }
        } catch (FileNotFoundException exp) {
            logger.error("Error: file not found. {}", exp.getMessage());
            throw new ServerErrorException("File processing error.", exp);
        } catch (IOException exp) {
            logger.error("Error during reading data. {}", exp.getMessage());
            throw new ServerErrorException("File processing error.", exp);
        }
        return result;
    }

    /**
     * On start of application logs INFO message, which algorithm is used.
     */
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

    /**
     * Handles, which algorithm should be used to process the file.
     *
     * @param input - input data to compere.
     * @return - true/false
     */
    @Override
    public boolean isContaining(String input, Gender gender) {
        Resource resource = getResource(gender);
        try {
            switch (algorithmVersion.toUpperCase()) {
                case "BUFFERED_READER": {
                    logger.debug("Algorithm version: " + algorithmVersion);
                    return isContainingBufferedReader(resource, input);
                }
                default: {
                    logger.debug("Algorithm version: default -> Stream API");
                    return isContainingStreamApi(resource, input);
                }
            }
        } catch (IOException exp) {
            logger.error("Error: wrong path to file. {}", exp.getMessage());
            throw new ServerErrorException("File processing error.", exp);
        }
    }

    private Resource getResource(Gender gender) {
        if (gender.equals(Gender.FEMALE)) {
            return resourceLoader.getResource(femaleClasspath);
        }
        return resourceLoader.getResource(maleClasspath);
    }

    /**
     * Uses BufferedReader to process the file.
     * Checks if the file contains given input.
     *
     * @param input - input data to compere.
     * @return - true/false
     */
    private boolean isContainingBufferedReader(Resource resource, String input) throws IOException {
        InputStream inputStream = resource.getInputStream();

        try (BufferedReader fileBufferReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String fileLineContent;
            while ((fileLineContent = fileBufferReader.readLine()) != null) {
                if (fileLineContent.equalsIgnoreCase(input)) {
                    return true;
                }
            }
        } catch (FileNotFoundException exp) {
            logger.error("Error: file not found. {}", exp.getMessage());
            throw new ServerErrorException("File processing error.", exp);

        } catch (IOException exp) {
            logger.error("Error during reading data. {}", exp.getMessage());
            throw new ServerErrorException("File processing error.", exp);
        }
        return false;
    }

    /**
     * Uses Stream API to process the file.
     * Checks if the file contains given input.
     *
     * @param input - input data to compere.
     * @return - true/false
     */
    private boolean isContainingStreamApi(Resource resource, String input) {
        // "./src/main/resources/flatDB/Male.txt"
        try (Stream<String> inputStream = Files.lines(Paths.get(resource.getURI()), StandardCharsets.UTF_8)) {
            return inputStream.anyMatch(line -> line.equalsIgnoreCase(input));
        } catch (IOException exp) {
            logger.error("Error during reading data. {}", exp.getMessage());
            throw new ServerErrorException("File processing error.", exp);
        }
    }

}
