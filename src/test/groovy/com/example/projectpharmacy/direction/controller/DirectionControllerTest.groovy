package com.example.projectpharmacy.direction.controller

import com.example.projectpharmacy.direction.service.DirectionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DirectionControllerTest extends Specification {

    private DirectionService directionService = Mock()
    private MockMvc mockMvc


    def setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectionController(directionService)).build()
    }



    def "GET /dir/{encodedId}"(){
        given:
        String encodedId = "r"

        String redirectedURL = "https://map.kakao.com/link/map/pharmacy,38.11,128.11"

        when:
        directionService.findDirectionUrlById(encodedId) >> redirectedURL // stubbing
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/dir/{encodedId}", encodedId))

        then:
        result.andExpect(status().is3xxRedirection()) // redirection
        .andExpect(redirectedUrl(redirectedURL)) // redirect 경로 검증
        .andDo(MockMvcResultHandlers.print())

    }


}
