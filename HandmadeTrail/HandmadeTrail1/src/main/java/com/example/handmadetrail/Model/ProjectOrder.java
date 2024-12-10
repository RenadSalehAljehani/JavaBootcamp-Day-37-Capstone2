package com.example.handmadetrail.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class ProjectOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectOrderId;

    @NotNull(message = "Diy beginner id can't be null.")
    @Column(columnDefinition = "int not null")
    private Integer diyBeginnerId;

    @Column(columnDefinition = "double")
    private Double totalPrice = 0.0;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime orderDate;
}