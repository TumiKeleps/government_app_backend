package za.gov.PerfomanceIndicator.DTO;


import lombok.Data;
import za.gov.PerfomanceIndicator.model.*;

@Data
public class SearchRequestSectorProvinceOnly {
     private SectorEnum sectorEnum;
    private ProvinceEnum provinceEnum;
    private int page;
    private int size;

}
