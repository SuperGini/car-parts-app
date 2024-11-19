package com.gini.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class AftermarketPart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String afPartNumber;


    @ManyToOne(fetch = FetchType.LAZY)
    private Part part;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Price price;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AftermarketPart that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(afPartNumber, that.afPartNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, afPartNumber);
    }

    @Override
    public String toString() {
        return "AftermarketPart{" +
                "id='" + id + '\'' +
                ", afPartNumber='" + afPartNumber + '\'' +
                '}';
    }
}
