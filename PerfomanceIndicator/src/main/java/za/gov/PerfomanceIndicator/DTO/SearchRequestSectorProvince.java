package za.gov.PerfomanceIndicator.DTO;


import lombok.Data;
import za.gov.PerfomanceIndicator.model.*;


@Data
public class SearchRequestSectorProvince {
     private SectorEnum sectorEnum;
    private QuarterEnum quarterEnum;
    private ProvinceEnum provinceEnum;
}
