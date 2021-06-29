package project.pnia.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import project.pnia.data.dto.PhoneNumberDTO;

import javax.xml.ws.Response;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
class HttpClientServiceTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    private HttpClientService httpClientService;

    @BeforeEach
    public void setup(){

        when(restTemplateBuilder.build()).thenReturn(restTemplate);
       httpClientService = new HttpClientService(restTemplateBuilder);

    }

    @Test
    void shouldExecutePostRequest() {

        String url = "aggregate/sector/+351";
        PhoneNumberDTO  phoneNumberDTO = new PhoneNumberDTO();
        ResponseEntity<PhoneNumberDTO> response = ResponseEntity.ok(phoneNumberDTO);

        when(restTemplate.getForEntity(url, PhoneNumberDTO.class)).thenReturn(response);

        PhoneNumberDTO result =  httpClientService.getHttpRequest(url, PhoneNumberDTO.class);

        Assertions.assertEquals(phoneNumberDTO, result);
    }

    @Test
    void shouldCatchTheExceptionAndReturnNull() {

        String url = "aggregate/sector/+351";

        when(restTemplate.getForEntity(url, PhoneNumberDTO.class)).thenThrow();

        PhoneNumberDTO result =  httpClientService.getHttpRequest(url, PhoneNumberDTO.class);

        Assertions.assertNull(result);
    }
}
