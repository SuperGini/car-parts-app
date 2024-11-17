package com.gini.service;

import com.gini.controller.AfRequest;
import com.gini.model.AftermarketPart;
import com.gini.repository.AfPartRepository;
import com.gini.repository.CustomAfPartRepository;
import com.gini.repository.PartRepository;
import com.gini.repository.filters.CarFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AfPartService {

    private final PartRepository partRepository;
    private final AfPartRepository afPartRepository;
    private final CustomAfPartRepository afPartCustomRepository;


    @Transactional
    public void saveAfPart(AfRequest afRequest) {

        var part = partRepository.getReferenceById(afRequest.partId());
        var af = new AftermarketPart();
        af.setAfPartNumber(afRequest.afName());

        part.setAftermarketPart(List.of(af));

        af.setPart(part);

        afPartRepository.save(af);

        System.out.println(af.getId() + "----------------------");

    }

    @Transactional
    public void getAfPart(CarFilter carFilter) {

        afPartCustomRepository.getAllPartsForCar(carFilter);

    }

    public void getAllPartsForCarWithEntityGRaph(CarFilter carFilter){
        afPartCustomRepository.getAllPartsForCarWithEntityGRaph(carFilter);
    }


}
