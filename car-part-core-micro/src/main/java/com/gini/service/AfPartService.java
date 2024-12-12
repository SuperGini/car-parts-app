package com.gini.service;

import com.gini.dto.AfPartRequest;
import com.gini.dto.AfPartResponse;
import com.gini.mapper.AfPartMapper;
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
    public AfPartResponse saveAfPart(AfPartRequest afRequest) {

        var part = partRepository.getReferenceById(afRequest.getPartId());
        var af = AfPartMapper.mapFrom(afRequest);
        af.setPart(part);

        part.setAftermarketPart(List.of(af));

        af.setPart(part);

        var afPart = afPartRepository.save(af);

        return AfPartMapper.mapFrom(afPart);
    }

    @Transactional
    public void getAfPart(CarFilter carFilter) {

        afPartCustomRepository.getAllPartsForCar(carFilter);

    }

    public void getAllPartsForCarWithEntityGRaph(CarFilter carFilter) {
        afPartCustomRepository.getAllPartsForCarWithEntityGRaph(carFilter);
    }


}
