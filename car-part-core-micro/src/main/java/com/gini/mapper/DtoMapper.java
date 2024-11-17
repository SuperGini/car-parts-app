package com.gini.mapper;

import com.gini.dto.AfDto;
import com.gini.dto.CarDto;
import com.gini.dto.PartDto;
import com.gini.model.AftermarketPart;
import com.gini.model.Car;
import com.gini.model.Part;

public class DtoMapper {

    public static CarDto toCarDto(Car car) {

        var carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setModel(car.getModel());
        return carDto;
    }

    public static PartDto toPartDto(Part part) {

        var partDto = new PartDto();
        partDto.setId(part.getId());
        partDto.setName(part.getName());
        return partDto;
    }


    public static AfDto afDto(AftermarketPart af) {

        var afDto = new AfDto();

        if(af != null) {
            afDto.setId(af.getId());
            afDto.setName(af.getAfPartNumber());
        }

        return afDto;
    }



}
