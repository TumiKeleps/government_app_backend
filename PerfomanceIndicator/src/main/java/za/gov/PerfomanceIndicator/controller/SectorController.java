package za.gov.PerfomanceIndicator.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
    origins = "*",
    allowedHeaders = {"x-api-key", "Content-Type"},
    methods = {RequestMethod.GET, RequestMethod.POST}
)
@RequestMapping("/api/sectors")
public class SectorController {

 

    // API to get all sectors
    @GetMapping
    public Object getAllSectors() {
        return za.gov.PerfomanceIndicator.model.SectorEnum.values();
    }

   
}