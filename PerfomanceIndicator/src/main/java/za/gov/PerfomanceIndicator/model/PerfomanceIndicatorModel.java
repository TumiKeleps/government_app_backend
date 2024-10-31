package za.gov.PerfomanceIndicator.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "perfomance_indicator")
@SequenceGenerator(sequenceName="perfomance_indicator_seq", name="perfomance_indicator_seq", allocationSize=1,initialValue = 100000000)
@Data
public class PerfomanceIndicatorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfomance_indicator_seq")
    private Long id;

    @NotNull(message = "Sector cannot be null")
    @Enumerated(EnumType.STRING)
    private SectorEnum sector;

    @NotBlank(message = "Indicator cannot be null")
    private String indicator;

    @NotBlank(message = "baseline cannot be null")
    private String baseline;

    @NotBlank(message = "Target cannot be null")
    private String target;

     @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
   
    private LocalDate creationDate;

    
    @NotNull(message = "Province cannot be null")
    @Enumerated(EnumType.STRING)
    private ProvinceEnum province;

    @OneToMany(mappedBy = "perfomanceIndicator", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    
    private List<ActualPerfomance> actualPerfomances = new ArrayList<>();


    @PrePersist
    private void createDate() {
        this.creationDate = LocalDate.now();
    }
}