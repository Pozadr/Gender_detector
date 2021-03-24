# Gender detector

## Overview
Application to detecting gender by given name. Compares the name content to flatDB tokens and checks if they match.
The app responds with "MALE", "FEMALE" or “INCONCLUSIVE” accordingly to match name tokens with flatDB content.

There are two ways of checking gender:
1. FIRST_TOKEN: Check the first token by the given name. 
   <br>Example: the given name: Adrian Anna Kowalski; response: MALE; cause: Adrian.
2. ALL_TOKENS: Check all tokens by the given name.
   <br>Example: the given name: Adrian Adam Anna Kowalski; response: MALE; cause: Adrian, Adam > Anna.
   
User can get all available flatDB tokens in range using pagination. There is page number and page size required.
There is possibility to run application with BufferedReader or StreamAPI to process files. StreamAPI is set as a default
setting.

## User stories
- [x] User can detect the gender checking first token.
- [x] User can detect the gender checking all tokens.
- [x] User can get all available tokens from requested range - pagination.

## REST API
### Get gender for given name
`GET /gender-detector/v1/`
#### Request
      localhost:8080/gender-detector/v1/?name=<name>&method=<first_token/all_tokens>
#### Response
      {
         "requestName": "<name>",
         "requestMethod": "<first_token / all_tokens>",
         "gender": "<MALE / FEMALE / INCONCLUSIVE>"
      }
### Get available tokens
`GET /gender-detector/v1/tokens/`
#### Request
      localhost:8080/gender-detector/v1/tokens?pageNo=<page_number>&pageSize=<page_size>
#### Response
      {
         "requestPageNumber": <page_number>,
         "requestPageSize": <page_size>,
            "tokens": [
            "<token1>",
            "<token2>",
            ...
            ]
      }
### Swagger UI
`GET /swagger-ui.html`

      localhost:8080/swagger-ui.html

## Setup
To set up method of files processing use _application.properties_. The default value is STREAM_API.

      algorithm-version=<BUFFERED_READER / STREAM_API>

To pack application to _.jar_ file
   
      mvn package

To run application from _.jar_ file
      
      java -jar genderdetector-0.0.1-SNAPSHOT.jar 

## Technologies
- Java 11
- Maven
- Spring Boot 2.4.4
- Swagger 2







