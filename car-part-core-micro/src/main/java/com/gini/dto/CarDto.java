package com.gini.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class CarDto {

    private String id;
    private String model;
    private List<PartDto> parts = new ArrayList<>();

    @Override
    public String toString() {
        return "CarDto{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarDto carDto)) return false;
        return Objects.equals(id, carDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
