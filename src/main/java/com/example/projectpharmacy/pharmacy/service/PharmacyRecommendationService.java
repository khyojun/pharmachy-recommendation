package com.example.projectpharmacy.pharmacy.service;

import com.example.projectpharmacy.api.dto.DocumentDto;
import com.example.projectpharmacy.api.dto.KakaoApiResponseDto;
import com.example.projectpharmacy.api.service.KakaoAddressSearchService;
import com.example.projectpharmacy.direction.dto.OutputDto;
import com.example.projectpharmacy.direction.entity.Direction;
import com.example.projectpharmacy.direction.service.Base62Service;
import com.example.projectpharmacy.direction.service.DirectionService;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final Base62Service base62Service;

    @Value("${pharmacy.recommendation.base.url}")
    private String baseUrl;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";


    public List<OutputDto> recommendationPharmacyList(String address) {
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(
            address);

        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(
            kakaoApiResponseDto.getDocumentDtoList())) {
            log.error(
                "[PharmacyRecommendationService recommendPharmacyList fail] Input address : {}",
                address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtoList().get(0);

        List<Direction> directions = directionService.buildDirectionList(documentDto);

        //List<Direction> directions = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directions).stream()
            .map(this::convertToOutputDto)
            .collect(Collectors.toList());
    }


    private OutputDto convertToOutputDto(Direction direction) {



        //은혜약국, 위도, 경도

        log.info("direction.getTargetAddress : {}", direction.getTargetAddress());
        return OutputDto.builder()
            .pharmacyAddress(direction.getTargetAddress())
            .pharmacyName(direction.getTargetPharmacyName())
            .directionUrl(baseUrl+base62Service.encodeDirectionId(direction.getId()))
            .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + ","
                + direction.getTargetLongitude())
            .distance(String.format("%.2f km", direction.getDistance()))
            .build();
    }


}
