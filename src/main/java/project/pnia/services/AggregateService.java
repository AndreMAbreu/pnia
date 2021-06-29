package project.pnia.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.pnia.data.dto.PhoneNumberDTO;
import project.pnia.data.model.PhoneNumberModel;
import project.pnia.utils.FormatUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class AggregateService {

    private final HttpClientService httpClientService;
    private final PrefixService prefixService;

    public Map<String, Map<String, Long>> createAggregatedPhoneNumbers(List<String> phoneNumbersRequest)  {

        if (isNull(phoneNumbersRequest) || phoneNumbersRequest.isEmpty()) return new HashMap<>();

        List<String> formattedPhoneNumbers = FormatUtils.formatPhoneList(phoneNumbersRequest);

        List<PhoneNumberModel> phoneNumbersModel = new ArrayList<>();

        formattedPhoneNumbers.forEach(phoneNumber -> {

            String validPrefix = getValidPrefix(phoneNumber);

            if (isNull(validPrefix)) return;

            var businessSectorDTO =
                httpClientService.getHttpRequest(FormatUtils.getBusinessSectorApiURL(phoneNumber), PhoneNumberDTO.class);

            if (isNull(businessSectorDTO) || isNull(businessSectorDTO.getSector())) return;

            phoneNumbersModel.add(new PhoneNumberModel(validPrefix, phoneNumber, businessSectorDTO.getSector()));

        });

        return aggregateList(phoneNumbersModel);
    }

    private String getValidPrefix(String phoneNumber) {
        try {
            return prefixService.getPrefix(phoneNumber);
        } catch (IOException e) {
            log.info("Phone number {} has an invalid prefix", phoneNumber);
        }
        return null;
    }


    private Map<String, Map<String, Long>> aggregateList(List<PhoneNumberModel> phoneNumbersModel) {
        Map<String, Map<String, Long>> responseMap = new HashMap<>();

        Map<String, List<PhoneNumberModel>> groupedByPrefix
            = phoneNumbersModel.stream().collect(
            Collectors.groupingBy(
                PhoneNumberModel::getPrefix));

        groupedByPrefix.forEach((prefix, list) -> {
            Map<String, Long> mapSector = list.stream().collect(
                Collectors.groupingBy(
                    phoneNumberModel -> phoneNumberModel.getSector().getName(), Collectors.counting()));

            responseMap.put(prefix, mapSector);
        });

        return responseMap;
    }

}
