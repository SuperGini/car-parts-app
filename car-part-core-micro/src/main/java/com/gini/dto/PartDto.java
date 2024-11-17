package com.gini.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class PartDto {

    private String id;
    private String name;
    private List<AfDto> afDtos = new ArrayList<>();

    @Override
    public String toString() {
        return "PartDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartDto partDto)) return false;
        return Objects.equals(id, partDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
