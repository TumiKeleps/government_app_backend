package za.gov.PerfomanceIndicator.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import za.gov.PerfomanceIndicator.AppConfig.BadRequest;
import za.gov.PerfomanceIndicator.DTO.SearchRequestSector;
import za.gov.PerfomanceIndicator.DTO.SearchRequestSectorProvince;
import za.gov.PerfomanceIndicator.DTO.SearchRequestSectorProvinceOnly;
import za.gov.PerfomanceIndicator.model.ActualPerfomance;
import za.gov.PerfomanceIndicator.model.PerfomanceIndicatorModel;
import za.gov.PerfomanceIndicator.model.ProvinceEnum;
import za.gov.PerfomanceIndicator.model.SectorEnum;
import za.gov.PerfomanceIndicator.repo.PerfomanceIndicatorRepo;
@Service
public class PerfomanceIndicatorService {

    
    @Autowired
    private PerfomanceIndicatorRepo perfomanceIndicatorRepository;

    public List<PerfomanceIndicatorModel> getAllPerfomanceIndicators() {
        return perfomanceIndicatorRepository.findAll();
    }

    public Optional<PerfomanceIndicatorModel> getPerfomanceIndicatorById(Long id) {
        return perfomanceIndicatorRepository.findById(id);
    }

    @Transactional
    public List<PerfomanceIndicatorModel> createPerfomanceIndicator(PerfomanceIndicatorModel indicatorData) {
        for (ProvinceEnum province : ProvinceEnum.values()) {
        PerfomanceIndicatorModel indicator = new PerfomanceIndicatorModel();
        indicator.setSector(indicatorData.getSector());
        indicator.setIndicator(indicatorData.getIndicator());
        indicator.setBaseline(indicatorData.getBaseline());
        indicator.setTarget(indicatorData.getTarget());
        indicator.setProvince(province);
        // Save each indicator instance to the database
        perfomanceIndicatorRepository.save(indicator);
    }
        return getAllPerfomanceIndicators();
    }

    @Transactional
    public PerfomanceIndicatorModel updatePerfomanceIndicator(Long id,
            PerfomanceIndicatorModel updatedPerfomanceIndicator) {
        Optional<PerfomanceIndicatorModel> existingPerfomanceIndicator = perfomanceIndicatorRepository.findById(id);
        if (existingPerfomanceIndicator.isPresent()) {
            PerfomanceIndicatorModel perfomanceIndicator = existingPerfomanceIndicator.get();
            if (updatedPerfomanceIndicator.getSector() != null) {
                perfomanceIndicator.setSector(updatedPerfomanceIndicator.getSector());
            }
            if (updatedPerfomanceIndicator.getIndicator() != null) {
                perfomanceIndicator.setIndicator(updatedPerfomanceIndicator.getIndicator());
            }
            if (updatedPerfomanceIndicator.getBaseline() != null) {
                perfomanceIndicator.setBaseline(updatedPerfomanceIndicator.getBaseline());
            }
            if (updatedPerfomanceIndicator.getTarget() != null) {
                perfomanceIndicator.setTarget(updatedPerfomanceIndicator.getTarget());
            }
            return perfomanceIndicatorRepository.save(perfomanceIndicator);
        } else {
            throw new BadRequest("Perfomance Indicator not found with id " + id);
        }
    }

    @Transactional
    public PerfomanceIndicatorModel patchPerfomanceIndicator(Long id, PerfomanceIndicatorModel patch) {
        Optional<PerfomanceIndicatorModel> existingPerfomanceIndicator = perfomanceIndicatorRepository.findById(id);
        if (existingPerfomanceIndicator.isPresent()) {
            PerfomanceIndicatorModel perfomanceIndicator = existingPerfomanceIndicator.get();

            if (patch.getSector() != null) {
                perfomanceIndicator.setSector(patch.getSector());
            }
            if (patch.getIndicator() != null) {
                perfomanceIndicator.setIndicator(patch.getIndicator());
            }
            if (patch.getBaseline() != null) {
                perfomanceIndicator.setBaseline(patch.getBaseline());
            }
            if (patch.getTarget() != null) {
                perfomanceIndicator.setTarget(patch.getTarget());
            }
            if (patch.getActualPerfomances() != null) {
                // Clear and add items instead of replacing the list
                perfomanceIndicator.getActualPerfomances().clear();
                for (ActualPerfomance actualPerfomance : patch.getActualPerfomances()) {
                    actualPerfomance.setPerfomanceIndicator(perfomanceIndicator);
                    perfomanceIndicator.getActualPerfomances().add(actualPerfomance);
                }
            }
            if(patch.getProvince() != null)
            {
                perfomanceIndicator.setProvince(patch.getProvince());
            }
            return perfomanceIndicatorRepository.save(perfomanceIndicator);
        } else {
            throw new BadRequest("Perfomance Indicator not found with ID " + id);
        }
    }

    @Transactional
    public void deletePerfomanceIndicator(Long id) {
        if (perfomanceIndicatorRepository.existsById(id)) {
            perfomanceIndicatorRepository.deleteById(id);
        } else {
            throw new BadRequest("Perfomance Indicator not found with id " + id);
        }
    }

    

    //create Actual perfomance
    @Transactional
    public PerfomanceIndicatorModel addActualPerformance(Long performanceIndicatorId,
            ActualPerfomance newActualPerformance) {
        Optional<PerfomanceIndicatorModel> optionalPerformanceIndicator = perfomanceIndicatorRepository
                .findById(performanceIndicatorId);

        if (optionalPerformanceIndicator.isPresent()) {
            PerfomanceIndicatorModel performanceIndicator = optionalPerformanceIndicator.get();

            // Ensure that quarterEnum and captureId combination is unique
            boolean alreadyExists = performanceIndicator.getActualPerfomances().stream()
                    .anyMatch(actualPerformance ->
                            actualPerformance.getQuarterEnum().equals(newActualPerformance.getQuarterEnum()) &&
                            actualPerformance.getCaptureId().equals(newActualPerformance.getCaptureId())
                    );

            if (alreadyExists) {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                        "The combination of quarter and capture ID must be unique");
            }

            // Set the relationship between ActualPerformance and PerformanceIndicator
            newActualPerformance.setPerfomanceIndicator(performanceIndicator);
            performanceIndicator.getActualPerfomances().add(newActualPerformance);

            // Save the updated PerformanceIndicatorModel
            return perfomanceIndicatorRepository.save(performanceIndicator);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Performance Indicator not found with ID " + performanceIndicatorId);
        }
    }

    //update actual perfomance 
    @Transactional
    public ActualPerfomance patchActualPerfomance(Long indicatorId, Long actualPerfomanceId, ActualPerfomance patch) {
        // Retrieve the parent PerfomanceIndicator
        Optional<PerfomanceIndicatorModel> optionalPerfomanceIndicator = perfomanceIndicatorRepository.findById(indicatorId);
    
        if (optionalPerfomanceIndicator.isPresent()) {
            PerfomanceIndicatorModel perfomanceIndicator = optionalPerfomanceIndicator.get();
    
            // Find the ActualPerfomance within the list of actual performances
            ActualPerfomance actualPerfomance = perfomanceIndicator.getActualPerfomances().stream()
                    .filter(ap -> ap.getId().equals(actualPerfomanceId))
                    .findFirst()
                    .orElseThrow(() -> new BadRequest("Actual Perfomance not found with ID: " + actualPerfomanceId));
    
            // Apply the patch (only non-null fields)
            if (patch.getCaptureId() != null) {
                actualPerfomance.setCaptureId(patch.getCaptureId());
            }
            if (patch.getQuarterEnum() != null) {
                actualPerfomance.setQuarterEnum(patch.getQuarterEnum());
            }
            
            if (patch.getDataSource() != null) {
                actualPerfomance.setDataSource(patch.getDataSource());
            }
            if (patch.getCommentOnQuality() != null) {
                actualPerfomance.setCommentOnQuality(patch.getCommentOnQuality());
            }
            if (patch.getPerfomance() != null) {
                actualPerfomance.setPerfomance(patch.getPerfomance());
            }
            if (patch.getProgressRatingEnum() != null) {
                actualPerfomance.setProgressRatingEnum(patch.getProgressRatingEnum());
            }
            if (patch.getBriefExplanation() != null) {
                actualPerfomance.setBriefExplanation(patch.getBriefExplanation());
            }
            if (patch.getProgressReport() != null) {
                actualPerfomance.setProgressReport(patch.getProgressReport());
            }
    
            // Save the updated PerfomanceIndicator
            return perfomanceIndicatorRepository.save(perfomanceIndicator).getActualPerfomances().stream()
                    .filter(ap -> ap.getId().equals(actualPerfomanceId))
                    .findFirst()
                    .orElseThrow(() -> new BadRequest("Actual Perfomance not found after patching"));
        } else {
            throw new BadRequest("Perfomance Indicator not found with ID: " + indicatorId);
        }
    }

    // //most for sector
    
    public String getMostFrequentProgressRating(SearchRequestSector searchRequest) {
        List<Object[]> results = perfomanceIndicatorRepository
                .findMostFrequentProgressRatingBySectorAndQuarter(
                        searchRequest.getSectorEnum(), 
                        searchRequest.getQuarterEnum());

        if (!results.isEmpty()) {
            return results.get(0)[0].toString();  // The most frequent ProgressRatingEnum
        } else {
            return "NONE";
        }
    }

    @Transactional(readOnly = true)
public Page<PerfomanceIndicatorModel> getPerformanceIndicatorsBySectorAndProvinceAll(SearchRequestSectorProvinceOnly searchRequest) {
    Page<PerfomanceIndicatorModel> indicatorsPage = perfomanceIndicatorRepository
            .findPerformanceIndicatorBySectorAndProvinceAll(
                    searchRequest.getSectorEnum(),
                    searchRequest.getProvinceEnum(),
                    PageRequest.of(searchRequest.getPage(), 10));

    // Since the province is a field in PerformanceIndicatorModel, there's no need to filter ActualPerformances by province
    // You can return the indicatorsPage directly or process it further if needed

    return indicatorsPage;
}

    //most for sector including province 
    public String getMostFrequentProgressRatingForProvince(SearchRequestSectorProvince searchRequest) {
        List<Object[]> results = perfomanceIndicatorRepository
                .findMostFrequentProgressRatingBySectorQuarterAndProvince(
                        searchRequest.getSectorEnum(), 
                        searchRequest.getQuarterEnum(),
                        searchRequest.getProvinceEnum());

        if (!results.isEmpty()) {
            return results.get(0)[0].toString();  // The most frequent ProgressRatingEnum
        } else {
            return "NONE";
        }
    }

    public Page<ActualPerfomance> getRecentActualPerformanceBySector(SectorEnum sector, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Get all performance indicators by sector
        List<PerfomanceIndicatorModel> indicators = perfomanceIndicatorRepository.findBySector(sector);

        // Flatten all actual performances from all indicators and sort by creation date (descending)
        List<ActualPerfomance> allActualPerformances = indicators.stream()
            .flatMap(indicator -> indicator.getActualPerfomances().stream())
            .sorted((a, b) -> b.getCreationDate().compareTo(a.getCreationDate())) // Sort by creation date DESC
            .collect(Collectors.toList());

        // Return the paginated list
        int start = Math.min((int) pageable.getOffset(), allActualPerformances.size());
        int end = Math.min((start + pageable.getPageSize()), allActualPerformances.size());
        return new PageImpl<>(allActualPerformances.subList(start, end), pageable, allActualPerformances.size());
    }

     
}
