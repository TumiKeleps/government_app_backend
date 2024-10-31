package za.gov.PerfomanceIndicator.DTO;


import lombok.Data;

@Data
public class SearchRequest {
   private String province;
    private String quarter;
    private int page;  
}
