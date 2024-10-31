package za.gov.PerfomanceIndicator.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import za.gov.PerfomanceIndicator.model.*;
@Repository
public interface PerfomanceIndicatorRepo extends JpaRepository<PerfomanceIndicatorModel, Long> {

    

    @Query("SELECT ap.progressRatingEnum, COUNT(ap.progressRatingEnum) as ratingCount " +
            "FROM PerfomanceIndicatorModel pi JOIN pi.actualPerfomances ap " +
            "WHERE pi.sector = :sectorEnum AND ap.quarterEnum = :quarterEnum " +
            "GROUP BY ap.progressRatingEnum " +
            "ORDER BY ratingCount DESC")
    List<Object[]> findMostFrequentProgressRatingBySectorAndQuarter(
            @Param("sectorEnum") SectorEnum sectorEnum,
            @Param("quarterEnum") QuarterEnum quarterEnum);

            @Query("SELECT pi FROM PerfomanceIndicatorModel pi " +
       "WHERE pi.sector = :sectorEnum AND pi.province = :provinceEnum")
Page<PerfomanceIndicatorModel> findPerformanceIndicatorBySectorAndProvinceAll(
        @Param("sectorEnum") SectorEnum sectorEnum,
        @Param("provinceEnum") ProvinceEnum provinceEnum,
        Pageable pageable);


        @Query("SELECT ap.progressRatingEnum, COUNT(ap.progressRatingEnum) as ratingCount " +
        "FROM PerfomanceIndicatorModel pi JOIN pi.actualPerfomances ap " +
        "WHERE pi.sector = :sectorEnum AND ap.quarterEnum = :quarterEnum AND pi.province = :provinceEnum " +
        "GROUP BY ap.progressRatingEnum " +
        "ORDER BY ratingCount DESC")
 List<Object[]> findMostFrequentProgressRatingBySectorQuarterAndProvince(
         @Param("sectorEnum") SectorEnum sectorEnum,
         @Param("quarterEnum") QuarterEnum quarterEnum,
         @Param("provinceEnum") ProvinceEnum provinceEnum);
 

//             @Query("SELECT pi FROM PerfomanceIndicatorModel pi JOIN pi.actualPerfomances ap " +
//             "WHERE pi.sector = :sectorEnum AND ap.province = :provinceEnum")
//      Page<PerfomanceIndicatorModel> findPerfomanceIndicatorBySectorAndProvinceAll(
//              @Param("sectorEnum") SectorEnum sectorEnum,
//              @Param("provinceEnum") ProvinceEnum provinceEnum,
//              Pageable pageable);




              List<PerfomanceIndicatorModel> findBySector(SectorEnum sector);
}