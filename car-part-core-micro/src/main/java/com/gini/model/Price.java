package com.gini.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "price")
    private BigDecimal partPrice;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne(mappedBy = "price", fetch = FetchType.LAZY)
    private Part part;


    @Override
    public String toString() {
        return "Price{" +
                "partPrice=" + partPrice +
                '}';
    }
}
