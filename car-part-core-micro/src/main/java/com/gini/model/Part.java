package com.gini.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "part_number", unique = true, nullable = false)
    private String partNumber;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Price price;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Car car;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private PartManufacturer partManufacturer;

    @OneToMany(mappedBy = "part", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<AftermarketPart> aftermarketPart;

    @Version
    private short version;


    @Override
    public String toString() {
        return "Part{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Objects.equals(partNumber, part.partNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(partNumber);
    }
}
