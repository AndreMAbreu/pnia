package project.pnia.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.pnia.data.dto.PhoneNumberDTO;
import project.pnia.data.enums.BusinessSectorType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AggregateServiceTest {

    @Mock
    private HttpClientService httpClientService;

    @Mock
    private PrefixService prefixService;

    @InjectMocks
    private AggregateService aggregateService;

    @Test
    void shouldValidAggregatePhoneNumbers() throws IOException {
        String validPhoneNumber = "+351 963587610";
        String formattedPhoneNumber = "+351963587610";
        String apiURL = "https://challenge-business-sector-api.meza.talkdeskstg.com/sector/+351963587610";

        when(prefixService.getPrefix("+351963587610")).thenReturn("351");

        PhoneNumberDTO returnedPhoneNumberDTO = new PhoneNumberDTO();
        returnedPhoneNumberDTO.setNumber(formattedPhoneNumber);
        returnedPhoneNumberDTO.setSector(BusinessSectorType.BANKING);
        when(httpClientService.getHttpRequest(apiURL, PhoneNumberDTO.class)).thenReturn(returnedPhoneNumberDTO);

        List<String> list = new ArrayList<>();
        list.add(validPhoneNumber);

        Map<String, Map<String, Long>> result = aggregateService.createAggregatedPhoneNumbers(list);

        Map<String, Map<String, Long>> expectedMap = new HashMap<>();
        Map<String, Long> expectedSectorMap = new HashMap<>();
        expectedSectorMap.put("Banking", 1L);
        expectedMap.put("351", expectedSectorMap);
        Assertions.assertEquals(expectedMap, result);

    }

    @Test
    void shouldDiscardPhoneNumbersWithInvalidPrefix() throws IOException {
        String invalidPhoneNumber = "+351 963587610";

        when(prefixService.getPrefix(any())).thenReturn(null);
        List<String> list = new ArrayList<>();
        list.add(invalidPhoneNumber);
        Map<String, Map<String, Long>> result = aggregateService.createAggregatedPhoneNumbers(list);

        Assertions.assertEquals(new HashMap<>(), result);

    }

    @Test
    void shouldDiscardPhoneNumbersWithInvalidBusinessSector() throws IOException {
        String invalidPhoneNumber = "+351 963587610";

        when(prefixService.getPrefix(any())).thenReturn("351");
        when(httpClientService.getHttpRequest(any(), any())).thenReturn(null);

        List<String> list = new ArrayList<>();
        list.add(invalidPhoneNumber);
        Map<String, Map<String, Long>> result = aggregateService.createAggregatedPhoneNumbers(list);

        Assertions.assertEquals(new HashMap<>(), result);

    }

}
