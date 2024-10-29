package com.gini.service;

import com.gini.dto.CarManufacturerRequest;
import com.gini.dto.CarManufacturerResponse;
import com.gini.dto.CarRequest;
import com.gini.dto.CarResponse;
import com.gini.mapper.CarMapper;
import com.gini.repository.CarManufacturerRepository;
import com.gini.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarManufacturerRepository carManufacturerRepository;
    private final CarMapper carMapper;

    @Transactional
    public CarManufacturerResponse saveCarManufacturer(CarManufacturerRequest carManufacturerRequest) {
        var carManufacturer = carMapper.toCarManufacturer(carManufacturerRequest);
        var carManufacturerDB = carManufacturerRepository.save(carManufacturer);
        log.info("car manufacturer: {} was saved in database", carManufacturer.getName());
        return carMapper.toCarManufacturerResponse(carManufacturerDB);
    }

    @Transactional
    public CarResponse saveCar(CarRequest carRequest) {

        var car = carMapper.toCar(carRequest);

        var carManufacturer = carManufacturerRepository.getReferenceById(carRequest.getCarManufacturerId().toString());

        car.setCarManufacturer(carManufacturer);
        carManufacturer.setCars(List.of(car));

        var carDB = carRepository.save(car);
        log.info("car model: {} saved in the database", carRequest.getModel());
        return carMapper.toCarResponse(carDB);

    }
}
