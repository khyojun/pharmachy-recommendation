package com.example.projectpharmacy.pharmacy.service;

import com.example.projectpharmacy.api.dto.DocumentDto;
import com.example.projectpharmacy.api.dto.KakaoApiResponseDto;
import com.example.projectpharmacy.api.service.KakaoAddressSearchService;
import com.example.projectpharmacy.direction.dto.OutputDto;
import com.example.projectpharmacy.direction.entity.Direction;
import com.example.projectpharmacy.direction.service.DirectionService;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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


    public List<OutputDto> recommendationPharmacyList(String address){
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(
            address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentDtoList())){
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address : {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtoList().get(0);

        List<Direction> directions = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directions).stream()
            .map(this::convertToOutputDto)
            .collect(Collectors.toList());
    }


    private OutputDto convertToOutputDto(Direction direction){

        log.info("direction.getTargetAddress : {}" , direction.getTargetAddress());
        return OutputDto.builder()
            .pharmacyAddress(direction.getTargetAddress())
            .pharmacyName(direction.getTargetPharmacyName())
            .directionUrl("todo")
            .roadViewUrl("todo")
            .distance(String.format("%.2f km", direction.getDistance()))
            .build();
    }


}
