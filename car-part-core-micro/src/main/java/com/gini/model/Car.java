package com.gini.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "model")
    private String model;

    @Column(name = "production_start_year")
    private LocalDate productionStartYear;

    @Column(name = "production_end_year")
    private LocalDate productionEndYear;

    @OneToOne(mappedBy = "car", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private CarManufacturer carManufacturer;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<Part> parts;







    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(model, car.model) &&
                Objects.equals(productionStartYear, car.productionStartYear) &&
                Objects.equals(productionEndYear, car.productionEndYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, productionStartYear, productionEndYear);
    }
}
