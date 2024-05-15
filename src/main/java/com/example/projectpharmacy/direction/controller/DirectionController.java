package com.example.projectpharmacy.direction.controller;

import com.example.projectpharmacy.direction.entity.Direction;
import com.example.projectpharmacy.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;



    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedId){
        String directionUrlById = directionService.findDirectionUrlById(encodedId);

        log.info("[Direction Shorten Url] direction url: {}", directionUrlById);

        return "redirect:"+directionUrlById;
    }



}
