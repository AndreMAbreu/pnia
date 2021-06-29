package project.pnia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.pnia.services.AggregateService;

import java.util.List;


@RestController
@RequestMapping(path = "/aggregate")
@RequiredArgsConstructor
public class AggregateController {

    private final AggregateService aggregateService;

    @PostMapping(value= "")
    public ResponseEntity<?> createAggregatePhoneNumbers(@RequestBody List<String> phoneNumberList) {
        return ResponseEntity.ok(aggregateService.createAggregatedPhoneNumbers(phoneNumberList));
    }

}
