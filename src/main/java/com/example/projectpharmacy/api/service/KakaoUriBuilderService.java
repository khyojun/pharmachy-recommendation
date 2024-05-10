package com.example.projectpharmacy.api.service;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class KakaoUriBuilderService {
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL= "https://dapi.kakao.com/v2/local/search/address.json";

    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";
    public URI buildUriByAddressSearch(String address){ // URI 같은 경우 다음 URICOMPONENTBUILDER를 통해 생성

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri(); // encoding 작업을 통해 한글 공백등이 잘 되게 해줘야함.

        log.info("[KakaoUriBuilderService buildUriByAddressSearch] address : {}, uri : {}", address, uri);

        return uri;
    }


    public URI buildUriByCategorySearch(double latitude, double longitude, double radius, String category)
    {
        double meterRadius = radius * 1000;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(
            KAKAO_LOCAL_CATEGORY_SEARCH_URL);
        uriBuilder.queryParam("category_group_code", category);
        uriBuilder.queryParam("x", longitude);
        uriBuilder.queryParam("y", latitude);
        uriBuilder.queryParam("radius", meterRadius);
        uriBuilder.queryParam("sort", "distance");

        URI uri = uriBuilder.build().encode().toUri();

        log.info("[KakaoAddressSearchService buildUriByCategorySearch] uri: {}", uri);

        return uri;
    }








}
