package za.gov.PerfomanceIndicator.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import za.gov.PerfomanceIndicator.model.ProgressRatingEnum;

@RestController
@CrossOrigin(
    origins = "*",
    allowedHeaders = {"x-api-key", "Content-Type"},
    methods = {RequestMethod.GET, RequestMethod.POST}
)
@RequestMapping("/api/progress")
public class ProgressController {


    // API to get all sectors
    @GetMapping
    public Object getAllProgress() {
        return ProgressRatingEnum.values();
    }

    // API to add a new sector
   
}
