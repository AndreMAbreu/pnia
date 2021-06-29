package project.pnia.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import project.pnia.services.AggregateService;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@EnableSpringDataWebSupport
class AggregateControllerTest {

    @Mock
    private AggregateService aggregateService;

    @InjectMocks
    private AggregateController aggregateController;

    private MockMvc mockMvc;

    private static final String BASE_URL = "/aggregate";


    @BeforeEach
    public void setup(){
        mockMvc = standaloneSetup(aggregateController).build();

    }


    @Test
    void shouldCallAggregateServiceWithRequestedPhoneList() throws Exception {
        String body = "[]";
        makeGetRequestFor(body).andExpect(status().isOk());
        verify(aggregateService, times(1)).createAggregatedPhoneNumbers(new ArrayList<>());
    }



    private ResultActions makeGetRequestFor(String body) throws Exception {
        return mockMvc.perform(post(AggregateControllerTest.BASE_URL)
            .content(body)
            .contentType(MediaType.APPLICATION_JSON));
    }
}
