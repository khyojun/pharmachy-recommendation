package com.example.projectpharmacy.direction.controller

import com.example.projectpharmacy.direction.dto.OutputDto
import com.example.projectpharmacy.pharmacy.service.PharmacyRecommendationService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class FormControllerTest extends Specification {

    private MockMvc mockMvc
    private PharmacyRecommendationService pharmacyRecommendationService = Mock()
    private List<OutputDto> outputDtoList

    def setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(new FormController(pharmacyRecommendationService))
        .build()

        outputDtoList = new ArrayList<>()
        outputDtoList.addAll(
                OutputDto.builder()
                .pharmacyName("pharmacy1")
                .build(),
                OutputDto.builder()
                .pharmacyName("pharmacy2")
                .build()
        )
    }

    def "GET /"(){
        expect:
        // FormController의 "/" URI를 get방식으로 호출
        mockMvc.perform(get("/"))
        .andExpect(handler().handlerType(FormController.class))
        .andExpect(handler().methodName("main"))
        .andExpect(status().isOk())
        .andExpect (view().name("main"))
        .andDo(log())
    }

    def "Post /search"(){

        given:
        String inputAddress= "서울 성북구 종암동"

        when:
        def resultActions = mockMvc.perform(post("/search").param("address", inputAddress))

        then:
        1 * pharmacyRecommendationService.recommendationPharmacyList(argument -> {
            assert argument==inputAddress
        }) >> outputDtoList


        resultActions
        .andExpect(status().isOk())
        .andExpect(view().name("output"))
        .andExpect(model().attributeExists("outputFormList"))
        .andExpect(model().attribute("outputFormList", outputDtoList))
        .andDo(print())
    }


}
