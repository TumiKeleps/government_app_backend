package za.gov.PerfomanceIndicator.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "actual_perfomance")
@SequenceGenerator(sequenceName = "actual_perfomance_seq", name = "actual_perfomance_seq", allocationSize = 1, initialValue = 100000000)
@Data
public class ActualPerfomance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actual_perfomance_seq")
    private Long id;

    @NotBlank(message = "Capture id cannot be null")
    private String captureId;

    @NotNull(message = "Quarter cannot be null")
    @Enumerated(EnumType.STRING)
    private QuarterEnum quarterEnum;


    @Nullable
    private String dataSource;

    @Nullable
    private String commentOnQuality;

    @Nullable
    private String perfomance;

    @NotNull(message = "Progress rating cannot be null")
    @Enumerated(EnumType.STRING)
    private ProgressRatingEnum progressRatingEnum;

    @Nullable
    private String briefExplanation;

    @Nullable
    private String progressReport;

    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "perfomance_indicator_id", nullable = false)
    @JsonBackReference
    private PerfomanceIndicatorModel perfomanceIndicator;

    @PrePersist
    private void createDate() {
        this.creationDate = LocalDateTime.now();
    }
}