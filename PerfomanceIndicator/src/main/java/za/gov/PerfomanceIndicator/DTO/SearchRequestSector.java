package za.gov.PerfomanceIndicator.DTO;


import lombok.Data;
import za.gov.PerfomanceIndicator.model.*;
@Data
public class SearchRequestSector {
    private SectorEnum sectorEnum;
    //private ProgressRatingEnum progressRatingEnum;
    private QuarterEnum quarterEnum;
}
