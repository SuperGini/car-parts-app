package com.gini.service;

import com.gini.dto.PartManufacturerRequest;
import com.gini.dto.PartManufacturerResponse;
import com.gini.dto.PartRequest;
import com.gini.dto.PartResponse;
import com.gini.dto.PartResponse2;
import com.gini.error.exceptions.NotFoundException;
import com.gini.mapper.PartMapper;
import com.gini.model.Part;
import com.gini.repository.CarRepository;
import com.gini.repository.ParManufacturerRepository;
import com.gini.repository.PartRepository;
import com.gini.repository.PartRepositoryWithFilter;
import com.gini.repository.filters.PartFilter;
import com.gini.repository.view.PartViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;
    private final PartMapper partMapper;
    private final ParManufacturerRepository parManufacturerRepository;
    private final CarRepository carRepository;
    private final PartRepositoryWithFilter partRepositoryWithFilter;
    private final PartViewRepository partViewRepository;

    @Transactional
    public PartManufacturerResponse save(PartManufacturerRequest partManufacturerRequest) {
        var manufacturer = partMapper.mapPartManufacturer(partManufacturerRequest);
        var manufacturerDB = parManufacturerRepository.save(manufacturer);

        var response = partMapper.mapPartManufacturerResponse(manufacturerDB);

        return response;

    }

    @Transactional
    public PartResponse save(PartRequest partRequest) {

        var manufacturer = parManufacturerRepository.getReferenceById(partRequest.getManufacturerId().toString());
        var car = carRepository.getReferenceById(partRequest.getCarId().toString());
        var part = partMapper.mapPart(partRequest);

        part.setCar(car);
        part.setPartManufacturer(manufacturer);
        manufacturer.setParts(Set.of(part));
        car.setParts(List.of(part));

        var partDb = partRepository.save(part);

        var response = partMapper.mapPartResponse(partDb);

        return response;

    }

    @Transactional(readOnly = true)
    public List<Part> getAllPartsFiltered(PartFilter partFilter) {

        var x = partRepositoryWithFilter.findPartWithFilter(partFilter);

        return x;
    }

    @Transactional(readOnly = true)
    public List<Part> getAllPartsFiltered2(PartFilter partFilter) {


        var x = partRepository.findAll(partRepositoryWithFilter.findPartWithFilter2(partFilter));

        x.forEach(c -> {
            System.out.println(c.getPartManufacturer().getName());
            System.out.println(c.getCar().getModel());
            System.out.println(c.getName());
            System.out.println(c.getCar().getCarManufacturer().getName());
            System.out.println(c.getPrice().getPartPrice());


        });

        return null;


    }

    @Transactional(readOnly = true)
    public void criteriabuilder(PartFilter partFilter) {
        partRepositoryWithFilter.withCriteriaBuilder(partFilter);

    }

    @Transactional(readOnly = true)
    public void criteriaBuidler2(PartFilter partFilter) {

        var x = partRepositoryWithFilter.findPartWithFilter2(partFilter);


        partRepositoryWithFilter.criteria2WithSpecification(x);
    }

    @Transactional(readOnly = true)
    public List<PartManufacturerResponse> getAllPartManufacturers() {
        return parManufacturerRepository.getAllPartManufacturers()
                .stream()
                .map(partMapper::mapPartManufacturerResponse)
                .toList();

    }

    @Transactional(readOnly = true)
    public PartResponse2 findPartByPartNumber2(String partNumber) {
        return partViewRepository.findByPartNumber(partNumber)
                .map(partMapper::mapPartResponse2)
                .orElseThrow(() -> new NotFoundException("Part not found"));
    }
}
