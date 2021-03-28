package pl.pozadr.genderdetector.repository.tokens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerErrorException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TokensRepositoryImpl implements TokensRepository {
    Logger logger = LoggerFactory.getLogger(TokensRepositoryImpl.class);

    /**
     * Returns tokens from file in requested range.
     *
     * @param pathToFile - path to local file.
     * @param first      - first position of data in file.
     * @param last       - last position of data in file.
     * @return - List<String> tokens
     */
    @Override
    public List<String> getTokens(String pathToFile, long first, long last) {
        List<String> result = new ArrayList<>();
        long currentTokenPosition = 1L;

        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader(pathToFile))) {
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
}
