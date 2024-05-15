package com.example.projectpharmacy.pharmacy.controller;


import com.example.projectpharmacy.pharmacy.PharmacyDto;
import com.example.projectpharmacy.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.projectpharmacy.pharmacy.entity.Pharmacy;
import com.example.projectpharmacy.pharmacy.service.PharmacyRepositoryService;
import com.example.projectpharmacy.pharmacy.service.PharmacySearchService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;


    //데이터 초기 셋팅을 위한 임시 메서드
    @GetMapping("/redis/save")
    public String save(){

        List<PharmacyDto> pharmacyDtoList = pharmacyRepositoryService.findAll()
            .stream().map(pharmacy -> PharmacyDto.builder()
                .id(pharmacy.getId())
                .pharmacyName(pharmacy.getPharmacyName())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .latitude(pharmacy.getLatitude())
                .longitude(pharmacy.getLongitude())
                .build()
            )
            .collect(Collectors.toList());

        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);


        return "success";
    }


}
