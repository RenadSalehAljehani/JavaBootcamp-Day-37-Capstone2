package com.example.handmadetrail.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    @NotEmpty(message = "Title can't be empty.")
    @Size(min = 5, max = 20, message = "Title length must be between 5-20 characters.")
    @Column(columnDefinition = "varchar(20) not null unique")
    @Check(constraints = "length(title) >= 5")
    private String title;

    @NotEmpty(message = "Description can't be empty.")
    @Size(min = 4, max = 800, message = "Description length must be between 4-800 characters.")
    @Column(columnDefinition = "varchar(800) not null")
    @Check(constraints = "length(description) >= 4")
    private String description;

    @NotEmpty(message = "Difficulty level can't be empty.")
    @Pattern(regexp = "^(?i)(easy|intermediate|hard)$", message = "Difficulty level must be easy, intermediate, or hard.")
    @Column(columnDefinition = "varchar(12) not null")
    @Check(constraints = "LOWER(difficultyLevel) = 'easy' or LOWER(difficultyLevel) ='intermediate' or LOWER(difficultyLevel) ='hard'")
    private String difficultyLevel;

    @NotNull(message = "Time required in minutes can't be empty.")
    @Positive(message = "Time required in minutes must be a positive number larger than zero.")
    @Column(columnDefinition = "int not null")
    @Check(constraints = "timeRequiredInMinutes > 0")
    private Integer timeRequiredInMinutes;

    @NotNull(message = "Number of materials can't be empty.")
    @Positive(message = "Number of materials must be a positive number larger than zero.")
    @Column(columnDefinition = "int not null")
    @Check(constraints = "numberOfMaterials > 0")
    private Integer numberOfMaterials;

    @NotEmpty(message = "Steps can't be empty.")
    @Column(nullable = false)
    private String steps;

    @NotNull(message = "Category id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer categoryId;

    @NotNull(message = "Expert id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer diyExpertId;

    @Column(columnDefinition = "double default 0.0")
    private Double budget = 0.0;

    @Column(columnDefinition = "double default 0.0")
    private Double price = 0.0;

    @NotNull(message = "Stock can't be empty.")
    @PositiveOrZero(message = "Stock must be a positive number or zero.")
    @Column(columnDefinition = "int not null")
    private Integer stock;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime publishedDate;
}