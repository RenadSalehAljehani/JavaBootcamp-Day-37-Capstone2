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
public class MaterialOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer materialOrderItemId;

    @NotNull(message = "Material order id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer materialOrderId;

    @NotNull(message = "Material id can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer materialId;

    @NotNull(message = "Quantity can't be empty.")
    @Column(columnDefinition = "int not null")
    private Integer quantity;

    @NotNull(message = "Price can't be empty.")
    @Column(columnDefinition = "double not null")
    private Double price;
}