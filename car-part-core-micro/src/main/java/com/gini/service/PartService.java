package com.gini.service;

import com.gini.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;


}
