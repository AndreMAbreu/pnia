package project.pnia.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Service
@Slf4j
public class HttpClientService {

    private final RestTemplate restTemplate;

    public HttpClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public <T> T getHttpRequest(String endPoint, Class<T> responseClass) {

        log.info("connecting to {}", endPoint);

        try {
            ResponseEntity<T> response = restTemplate.getForEntity(endPoint, responseClass);
            log.info("Response with status code: {}", response.getStatusCode());

            if (!Objects.equals(response.getStatusCode(), HttpStatus.OK)) return null;

            return response.getBody();
        }
        catch (Exception e) {
            log.info("Response with message: {}", e.getMessage());
            return null;
        }

    }

}
