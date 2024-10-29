package com.gini.mapper;

import com.gini.dto.CarManufacturerRequest;
import com.gini.dto.CarManufacturerResponse;
import com.gini.dto.CarRequest;
import com.gini.dto.CarResponse;
import com.gini.model.Car;
import com.gini.model.CarManufacturer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CarMapper {


    public CarManufacturerResponse toCarManufacturerResponse(CarManufacturer carManufacturer) {
        log.info("mapping to carManufacturer response");
        return new CarManufacturerResponse()
                .id(UUID.fromString(carManufacturer.getId()))
                .name(carManufacturer.getName());
    }

    public CarManufacturer toCarManufacturer(CarManufacturerRequest request) {
        log.info("mapping to carManufacturer entity");
        var carManufacturer = new CarManufacturer();
        carManufacturer.setName(request.getName());
        return carManufacturer;
    }


    public Car toCar(CarRequest request) {
        log.info("mapping to car entity");
        var car = new Car();
        car.setProductionStartYear(request.getProductionStartYear());
        car.setProductionEndYear(request.getProductionEndYear());
        car.setModel(request.getModel());
        return car;
    }

    public CarResponse toCarResponse(Car car) {
        log.info("mapping to car response");
        return new CarResponse()
                .id(UUID.fromString(car.getId()))
                .model(car.getModel())
                .productionStartYear(car.getProductionStartYear())
                .productionEndYear(car.getProductionEndYear());
    }


}
