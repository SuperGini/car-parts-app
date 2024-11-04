package com.gini.service;

import com.gini.dto.PartManufacturerRequest;
import com.gini.dto.PartManufacturerResponse;
import com.gini.dto.PartRequest;
import com.gini.dto.PartResponse;
import com.gini.mapper.PartMapper;
import com.gini.model.Part;
import com.gini.repository.CarRepository;
import com.gini.repository.PartRepositoryWithFilter;
import com.gini.repository.ParManufacturerRepository;
import com.gini.repository.PartRepository;
import com.gini.repository.filters.PartFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;
    private final PartMapper partMapper;
    private final ParManufacturerRepository parManufacturerRepository;
    private final CarRepository carRepository;
    private final PartRepositoryWithFilter partRepositoryWithFilter;

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

      var x =  partRepositoryWithFilter.findPartWithFilter(partFilter);

        return x;
    }
}
