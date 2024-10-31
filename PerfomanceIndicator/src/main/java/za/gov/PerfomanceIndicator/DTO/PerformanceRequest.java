package za.gov.PerfomanceIndicator.DTO;



import lombok.Data;
import za.gov.PerfomanceIndicator.model.*;

@Data
public class PerformanceRequest {
    private SectorEnum sector;
    private Integer page;
}
