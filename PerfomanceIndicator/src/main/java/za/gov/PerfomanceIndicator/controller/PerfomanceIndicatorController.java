package za.gov.PerfomanceIndicator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import za.gov.PerfomanceIndicator.DTO.PerformanceRequest;
import za.gov.PerfomanceIndicator.DTO.SearchRequestSector;
import za.gov.PerfomanceIndicator.DTO.SearchRequestSectorProvince;
import za.gov.PerfomanceIndicator.DTO.SearchRequestSectorProvinceOnly;
import za.gov.PerfomanceIndicator.model.ActualPerfomance;
import za.gov.PerfomanceIndicator.model.PerfomanceIndicatorModel;
import za.gov.PerfomanceIndicator.service.PerfomanceIndicatorService;



@RestController
@RequestMapping("/api/perfomance-indicators")

@CrossOrigin(
    origins = "*",
    allowedHeaders = {"x-api-key", "Content-Type"},
    methods = {RequestMethod.GET, RequestMethod.POST,RequestMethod.PATCH,RequestMethod.PUT}
)
public class PerfomanceIndicatorController {

    @Autowired
    private PerfomanceIndicatorService perfomanceIndicatorService;

    @GetMapping
    public ResponseEntity<List<PerfomanceIndicatorModel>> getAllPerfomanceIndicators() {
        List<PerfomanceIndicatorModel> indicators = perfomanceIndicatorService.getAllPerfomanceIndicators();
        return new ResponseEntity<>(indicators, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PerfomanceIndicatorModel> getPerfomanceIndicatorById(@PathVariable Long id) {
        Optional<PerfomanceIndicatorModel> indicator = perfomanceIndicatorService.getPerfomanceIndicatorById(id);
        return indicator.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //create Perfomance indicator
    @PostMapping
    public ResponseEntity<List<PerfomanceIndicatorModel>> createPerfomanceIndicator(
            @RequestBody PerfomanceIndicatorModel perfomanceIndicator) {
        // Ignore actualPerfomances during creation to avoid circular reference issues
        perfomanceIndicator.setActualPerfomances(null);
        List<PerfomanceIndicatorModel> createdIndicator = perfomanceIndicatorService
                .createPerfomanceIndicator(perfomanceIndicator);
        return new ResponseEntity<>(createdIndicator, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfomanceIndicatorModel> updatePerfomanceIndicator(@PathVariable Long id,
            @RequestBody PerfomanceIndicatorModel updatedPerfomanceIndicator) {
        try {
            PerfomanceIndicatorModel updatedIndicator = perfomanceIndicatorService.updatePerfomanceIndicator(id,
                    updatedPerfomanceIndicator);
            return new ResponseEntity<>(updatedIndicator, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PerfomanceIndicatorModel> patchPerfomanceIndicator(@PathVariable Long id,
            @RequestBody PerfomanceIndicatorModel patch) {
        try {
            PerfomanceIndicatorModel patchedIndicator = perfomanceIndicatorService.patchPerfomanceIndicator(id, patch);
            return new ResponseEntity<>(patchedIndicator, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfomanceIndicator(@PathVariable Long id) {
        try {
            perfomanceIndicatorService.deletePerfomanceIndicator(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   

   // create actual performance
    @PostMapping("/{id}/actual-perfomances")
    public ResponseEntity<PerfomanceIndicatorModel> addActualPerfomance(
            @PathVariable Long id,
            @RequestBody ActualPerfomance actualPerfomance) {
        try {
            PerfomanceIndicatorModel updatedIndicator = perfomanceIndicatorService.addActualPerformance(id,
                    actualPerfomance);
            return new ResponseEntity<>(updatedIndicator, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //update Actual performance
    @PatchMapping("/{indicatorId}/actual-perfomances/{actualPerfomanceId}")
    public ResponseEntity<ActualPerfomance> patchActualPerfomance(
            @PathVariable Long indicatorId,
            @PathVariable Long actualPerfomanceId,
            @RequestBody ActualPerfomance patch) {
        try {
            ActualPerfomance updatedPerfomance = perfomanceIndicatorService.patchActualPerfomance(indicatorId,
                    actualPerfomanceId, patch);
            return new ResponseEntity<>(updatedPerfomance, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //search for the most regarding the sector
   @PostMapping("/most-frequent-progress-sector")
    public ResponseEntity<Map<String, String>> getMostFrequentProgressRating(
            @RequestBody SearchRequestSector searchRequest) {
        String progressRating = perfomanceIndicatorService
                .getMostFrequentProgressRating(searchRequest);

        Map<String, String> response = new HashMap<>();
        response.put("progressRatingEnum", progressRating);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/search-by-sector-and-province")
public ResponseEntity<Page<PerfomanceIndicatorModel>> getPerformanceIndicatorBySectorAndProvince(
        @RequestBody SearchRequestSectorProvinceOnly searchRequest) {
    Page<PerfomanceIndicatorModel> indicators = perfomanceIndicatorService.getPerformanceIndicatorsBySectorAndProvinceAll(searchRequest);
    if (!indicators.isEmpty()) {
        return new ResponseEntity<>(indicators, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


    //search for most regarding sector and province 
    @PostMapping("/most-frequent-progress-province")
    public ResponseEntity<Map<String, String>> getMostFrequentProgressRatingForProvince(
            @RequestBody SearchRequestSectorProvince searchRequest) {
        String progressRating = perfomanceIndicatorService
                .getMostFrequentProgressRatingForProvince(searchRequest);

        Map<String, String> response = new HashMap<>();
        response.put("progressRatingEnum", progressRating);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


   
    @PostMapping("/actual-performance")
    public Page<ActualPerfomance> getRecentActualPerformance(
            @RequestBody PerformanceRequest request) {
        return perfomanceIndicatorService.getRecentActualPerformanceBySector(request.getSector(), request.getPage(), 10);
    }
   

}
