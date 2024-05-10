package com.example.projectpharmacy.api.service

import com.example.projectpharmacy.AbstractIntegrationBaseTest
import com.example.projectpharmacy.api.dto.DocumentDto
import com.example.projectpharmacy.api.dto.KakaoApiResponseDto
import com.example.projectpharmacy.api.dto.MetaDto
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper


class KakaoAddressSearchServiceRetryTest extends AbstractIntegrationBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    @SpringBean
    private KakaoUriBuilderService kakaoUriBuilderService = Mock()

    private MockWebServer mockWebServer

    private ObjectMapper mapper = new ObjectMapper()
    private String inputAddress = "서울 성북구 종암로 10길"


    def setup(){
        mockWebServer = new MockWebServer()
        mockWebServer.start()
        println(mockWebServer.port)
        println(mockWebServer.url("/"))
    }

    def clenaup(){
        mockWebServer.shutdown()
    }


    def "requestAddressSearch retry success"(){
        given:
        def metaDto = new MetaDto(1)
        def documentDto = DocumentDto.builder()
                .addressName(inputAddress)
                .build()
        def expectedResponse = new KakaoApiResponseDto(metaDto, Arrays.asList(documentDto))
        def uri = mockWebServer.url("/").uri()


        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
        .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .setBody(mapper.writeValueAsString(expectedResponse)))

        def kakaoApiResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        2 * kakaoUriBuilderService.buildUriByAddressSearch(inputAddress) >> uri
        kakaoApiResult.getDocumentDtoList().size()==1
        kakaoApiResult.getMetaDto().totalCount==1
        kakaoApiResult.getDocumentDtoList().get(0).getAddressName()==inputAddress
    }



    def "requestAddressSearch retry fail"(){
        given:
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))

        def result = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        2 * kakaoUriBuilderService.buildUriByAddressSearch(inputAddress) >> uri
        result == null
    }


    def "정상적인 주소를 입력했을 경우, 정상적으로 위도 경도로 변환 된다."(){
        given:
        boolean actualresult = false

        when:
        def searchResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        if(searchResult == null) actualresult = false
        else actualresult=searchResult.getDocumentDtoList().size()> 0



        where:
        inputAddress   | expectedResult
        "서울 특별시 성북구 종암동" | true
        "서울 성북구 종암동 91" | true
        "서울 대학로" | true
        "서울 성북구 종암동 잘못된 주소" | false
        "광진구 구의동 251-45" | true
        "광진구 구의동 251-455555" | false
        "" | false


    }


}
