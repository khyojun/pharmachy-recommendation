package com.example.projectpharmacy.pharmacy.service;

import com.example.projectpharmacy.api.dto.DocumentDto;
import com.example.projectpharmacy.api.dto.KakaoApiResponseDto;
import com.example.projectpharmacy.api.service.KakaoAddressSearchService;
import com.example.projectpharmacy.pharmacy.direction.entity.Direction;
import com.example.projectpharmacy.pharmacy.direction.service.DirectionService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;


    public void recommendationPharmacyList(String address){
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(
            address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentDtoList())){
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address : {}", address);
            return;
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtoList().get(0);

//        List<Direction> directions = directionService.buildDirectionList(documentDto);
        List<Direction> directions = directionService.buildDirectionListByCategoryApi(documentDto);

        directionService.saveAll(directions);
    }


}
