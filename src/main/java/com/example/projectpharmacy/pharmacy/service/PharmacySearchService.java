package com.example.projectpharmacy.pharmacy.service;

import com.example.projectpharmacy.pharmacy.PharmacyDto;
import com.example.projectpharmacy.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.projectpharmacy.pharmacy.entity.Pharmacy;
import com.example.projectpharmacy.pharmacy.repository.PharmacyRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;


    public List<PharmacyDto> searchPharmacyDtoList(){

        //redis
        List<PharmacyDto> pharmacyDtoList = pharmacyRedisTemplateService.findAll();
        if(!pharmacyDtoList.isEmpty()){
            log.info("redis findAll success");
            return pharmacyDtoList;
        }

        //db
        return pharmacyRepositoryService.findAll()
            .stream().map(this::convertToPharmacyDto)
            .collect(Collectors.toList());
    }


    private PharmacyDto convertToPharmacyDto(Pharmacy pharmacy){
        return PharmacyDto.builder()
            .id(pharmacy.getId())
            .pharmacyAddress(pharmacy.getPharmacyAddress())
            .pharmacyName(pharmacy.getPharmacyName())
            .latitude(pharmacy.getLatitude())
            .longitude(pharmacy.getLongitude())
            .build();
    }


}
