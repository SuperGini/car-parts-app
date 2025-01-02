package com.gini.service;

import com.gini.dto.CarFilterResponse;
import com.gini.dto.CarManufacturerRequest;
import com.gini.dto.CarManufacturerResponse;
import com.gini.dto.CarRequest;
import com.gini.dto.CarResponse;
import com.gini.dto.CarResponse2;
import com.gini.dto.PartFilterRequest;
import com.gini.mapper.CarMapper;
import com.gini.mapper.PaginationFilterMapper;
import com.gini.model.Car_;
import com.gini.model.views.PartView;
import com.gini.repository.CarManufacturerRepository;
import com.gini.repository.CarRepository;
import com.gini.repository.filters.BlazeFilder;
import com.gini.repository.view.CarViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarManufacturerRepository carManufacturerRepository;
    private final CarMapper carMapper;
    private final CarViewRepository carViewRepository;

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

    @Transactional(readOnly = true)
    public List<CarFilterResponse> getAllPartsWithSpecificationPaginationAndSorting2(PartFilterRequest carFilter) {

        BlazeFilder filter = new BlazeFilder(carFilter);

        Pageable page = PageRequest.of(0, 2, Sort.by(Car_.MODEL).ascending().and(Sort.by(Car_.ID).ascending()));

        log.info("searching in the database for parts paginated");
        var x = carViewRepository.findAll(filter, page);

        var response = new ArrayList<CarFilterResponse>();

        log.info("mapping database response to response");
        x.forEach(car -> {
            var t = PaginationFilterMapper.toCarFilterResponse(car);
            response.add(t);
        });

        return response;
    }


    // testsing --------------------------------------------------------START-------------------------------------------

    @Transactional(readOnly = true)
    public PartView getAllParts() {

        var x = carViewRepository.findAll();


        x.forEach(c -> {

            System.out.println("start---------------");

            c.getParts().forEach(p -> {


                System.out.println(p.getId() + " " + p.getName());
                p.getAftermarketPart().forEach(

                        af -> {
                            System.out.println("Car: " + c.getModel() + " Part: " + p.getName() + " " + p.getId() + " Af: " + af.getAfPartNumber() + " " + af.getAfPartNumber());

                        }
                );

            });

            System.out.println("end++++++++++++++++++++++++++++++++++++");


        });

        return null;
    }

    @Transactional(readOnly = true)
    public PartView getAllPartsWithSpecification(PartFilterRequest carFilter) {

        BlazeFilder filter = new BlazeFilder(carFilter);

        var x = carViewRepository.findAll(filter);


        x.forEach(c -> {


            System.out.println("start---------------");

            c.getParts().forEach(p -> {


                System.out.println(p.getId() + " " + p.getName());
                p.getAftermarketPart().forEach(

                        af -> {
                            System.out.println("Car: " + c.getModel() + " Part: " + p.getName() + " " + p.getId() + " Af: " + af.getAfPartNumber() + " " + af.getAfPartNumber());

                        }
                );

            });

            System.out.println("end++++++++++++++++++++++++++++++++++++");


        });

        return null;
    }

    @Transactional(readOnly = true)
    public List<CarFilterResponse> getAllPartsWithSpecificationPaginationAndSorting(PartFilterRequest carFilter) {

        BlazeFilder filter = new BlazeFilder(carFilter);

        Pageable page = PageRequest.of(0, 2, Sort.by(Car_.MODEL).ascending().and(Sort.by(Car_.ID).ascending()));

        var x = carViewRepository.findAll(filter, page);


        x.forEach(c -> {


            System.out.println("start---------------");

            c.getParts().forEach(p -> {


                System.out.println(p.getId() + " " + p.getName() + " " + p.getPrice().getPartPrice() + " " + p.getPrice().getCurrency());
                p.getAftermarketPart().forEach(

                        af -> {
                            System.out.println("Car: " + c.getModel() + " Part: " + p.getName() + " " + p.getId() + " Af: " + af.getAfPartNumber() + " " + af.getAfPartNumber());

                        }
                );

            });

            System.out.println("end++++++++++++++++++++++++++++++++++++");


        });

        return null;
    }

    // testsing --------------------------------------------------------END-------------------------------------------

    @Transactional(readOnly = true)
    public List<CarManufacturerResponse> getAllManufacturers() {
        return carManufacturerRepository.findAll()
                .stream()
                .map(carMapper::toCarManufacturerResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CarResponse2> getAllCars() {
        return carRepository.findCars().stream()
                .map(carMapper::toCarResponse2)
                .toList();


    }
}
