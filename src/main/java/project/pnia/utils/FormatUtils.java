package project.pnia.utils;

import project.pnia.data.model.PhoneNumberModel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static project.pnia.data.constants.Constants.API_URL;

public class FormatUtils {

    private FormatUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatPhoneNumber(String phoneNumber) {
        var pattern = "\\s";

        return phoneNumber.replaceAll(pattern, "");
    }

    public static String getBusinessSectorApiURL(String phoneNumber) {
        return API_URL + FormatUtils.formatPhoneNumber(phoneNumber);
    }

    public static List<String> formatPhoneList(List<String> phoneNumbers) {

        List<String> removedWhiteSpaces = phoneNumbers.stream()
            .map(FormatUtils::formatPhoneNumber)
            .filter(phoneNumber -> !phoneNumber.isEmpty() && phoneNumber.length()>1)
            .collect(Collectors.toList());

        return new ArrayList<>(
            new LinkedHashSet<>(removedWhiteSpaces));
    }

    public static String getNumberWithoutPrefix(PhoneNumberModel phoneNumberModelList) {

        String phoneNumber = phoneNumberModelList.getNumber();

        if (!phoneNumber.startsWith("+") && !phoneNumber.startsWith("00")) return null;

        String formattedPhoneNumber;

        if (phoneNumber.startsWith("+")) {
            formattedPhoneNumber = phoneNumber.substring(1);
        } else {
            formattedPhoneNumber = phoneNumber.substring(2);
        }

        return formattedPhoneNumber;
    }
}
