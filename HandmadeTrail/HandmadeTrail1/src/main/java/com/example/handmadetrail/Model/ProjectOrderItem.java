package com.example.handmadetrail.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class ProjectOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectOrderItemId;

    @NotNull(message = "Project order id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer projectOrderId;

    @NotNull(message = "Project id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer projectId;

    @NotNull(message = "Quantity can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer quantity;

    @NotNull(message = "Price can't be empty.")
    @Column(columnDefinition = "double not null")
    private Double price;
}