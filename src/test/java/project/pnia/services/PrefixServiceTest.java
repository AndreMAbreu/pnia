package project.pnia.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


import static org.mockito.Mockito.mockStatic;
import static project.pnia.data.constants.Constants.PREFIX_FILE_PATH;

@ExtendWith(SpringExtension.class)
class PrefixServiceTest {

    @InjectMocks
    private PrefixService prefixService;

    private final MockedStatic<Files> mockFiles = mockStatic(Files.class);

    private final List<String> prefixesList = List.of("1", "351", "89");


    @BeforeEach
    public void setup(){
        mockFiles.when(() -> Files.lines(Paths.get(PREFIX_FILE_PATH)))
            .thenReturn(prefixesList.stream());
    }

    @AfterEach
    public void close() {
        mockFiles.close();
    }

    @Test
    void shouldRetrieveTheCorrectPrefixPlus() throws IOException {

        String result = prefixService.getPrefix("+185697");

        Assertions.assertEquals("1", result);

    }

    @Test
    void shouldRetrieveTheCorrectPrefixZero() throws IOException {

        String result = prefixService.getPrefix("0035185697");

        Assertions.assertEquals("351", result);

    }

    @Test
    void shouldDiscardPhoneNumberWithInvalidPrefix() throws IOException {

        String result = prefixService.getPrefix("35185697");

        Assertions.assertNull(result);

    }

    @Test
    void shouldDiscardEmptyPhoneNumber() throws IOException {

        String result = prefixService.getPrefix("");

        Assertions.assertNull(result);

    }

}
