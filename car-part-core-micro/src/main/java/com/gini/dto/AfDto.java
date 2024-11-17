package com.gini.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AfDto {

    private String id;
    private String name;

    @Override
    public String toString() {
        return "AfDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AfDto afDto)) return false;
        return Objects.equals(id, afDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
