package project.pnia.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static project.pnia.data.constants.Constants.PREFIX_FILE_PATH;

@Service
public class PrefixService {

    public String getPrefix(String phoneNumber) throws IOException {

        if (isNull(phoneNumber) || phoneNumber.isEmpty()) return null;

        if (!phoneNumber.startsWith("+") && !phoneNumber.startsWith("00")) return null;

        String formattedPhoneNumber;
        String prefix = null;

        if (phoneNumber.startsWith("+")) {
            formattedPhoneNumber = phoneNumber.substring(1);
        } else {
            formattedPhoneNumber = phoneNumber.substring(2);
        }

        var path = Paths.get(PREFIX_FILE_PATH);
        try (Stream<String> input = Files.lines(path)) {

            Optional<String> matchedPrefix = input.filter(formattedPhoneNumber::startsWith).findFirst();

            if (matchedPrefix.isPresent()) {
                prefix = matchedPrefix.get();
            }
        }

        return prefix;
    }

}
