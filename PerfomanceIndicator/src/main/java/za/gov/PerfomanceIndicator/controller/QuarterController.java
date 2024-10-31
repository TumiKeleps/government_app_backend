package za.gov.PerfomanceIndicator.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.gov.PerfomanceIndicator.model.QuarterEnum;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = { "x-api-key", "Content-Type" }, methods = { RequestMethod.GET,
        RequestMethod.POST })
@RequestMapping("/api/quarters")
public class QuarterController {
    @GetMapping
    public Object getAllQuarters() {
        return QuarterEnum.values();
    }
}
