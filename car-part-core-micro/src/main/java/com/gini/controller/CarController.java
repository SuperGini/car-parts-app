package com.gini.controller;

import com.gini.api.CarApi;
import com.gini.dto.CarManufacturerRequest;
import com.gini.dto.CarManufacturerResponse;
import com.gini.dto.CarRequest;
import com.gini.dto.CarResponse;
import com.gini.dto.PartFilterRequest;
import com.gini.repository.CustomCarRepository;
import com.gini.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CarController implements CarApi {

    private final CarService carService;
    private final CustomCarRepository customCarRepository; //todo: move this shit repositry to service

    @Override
    public ResponseEntity<CarResponse> createCar(CarRequest carRequest) {
        log.info("initiating creation of car : {}", carRequest.getModel());
        var carResponse = carService.saveCar(carRequest);
        return ResponseEntity.ok(carResponse);
    }

    @Override
    public ResponseEntity<CarManufacturerResponse> createCarManufacturer(CarManufacturerRequest carManufacturerRequest) {
        log.info("initiating creation of car manufacturer: {}", carManufacturerRequest.getName());
        var carManufacturerResponse = carService.saveCarManufacturer(carManufacturerRequest);
        return ResponseEntity.ok(carManufacturerResponse);
    }

    @Override
    public ResponseEntity<CarResponse> findCarById(UUID id) {
        customCarRepository.joinsPart2();


        return null;
    }

    @Override
    public ResponseEntity<List<CarManufacturerResponse>> getAllManufacturers() {
        return ResponseEntity.ok(carService.getAllManufacturers());
    }


    @GetMapping("/parts-all")
    public void getAllParts() {

        carService.getAllParts();

    }

    @GetMapping("/parts-all2")
    public void getAllParts2(@RequestBody PartFilterRequest carFilter) {
        carService.getAllPartsWithSpecificationPaginationAndSorting(carFilter);
    }
}
