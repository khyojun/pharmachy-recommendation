package com.example.projectpharmacy.api.service

import com.example.projectpharmacy.AbstractIntegrationBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationBaseTest {


    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "address 파라미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다."(){
        given:
        def address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)


        then:
        result == null
    }


    def "주소값이 valid하다면, requestAddressSearch 메소드는 정상적으로 document를 반환한다."(){
        given:
        def address = "서울 성북구 종암로 10길"
        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)
        then:
        result.documentDtoList.size()>0
        result.metaDto.totalCount>0
        result.documentDtoList.get(0).addressName != null
    }

}
