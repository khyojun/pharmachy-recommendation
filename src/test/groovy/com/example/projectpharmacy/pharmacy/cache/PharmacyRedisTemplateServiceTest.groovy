package com.example.projectpharmacy.pharmacy.cache

import com.example.projectpharmacy.AbstractIntegrationBaseTest
import com.example.projectpharmacy.pharmacy.PharmacyDto
import org.springframework.beans.factory.annotation.Autowired


class PharmacyRedisTemplateServiceTest extends AbstractIntegrationBaseTest {


    @Autowired
    private PharmacyRedisTemplateService pharmacyRedisTemplateService

    def setup(){
        pharmacyRedisTemplateService.findAll()
        .forEach(dto -> {
            pharmacyRedisTemplateService.delete(dto.getId())
        })

    }
    
    
    def "save Success"(){
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        PharmacyDto dto = PharmacyDto.builder()
                .id(1L)
                .pharmacyName(pharmacyName)
                .pharmacyAddress(pharmacyAddress)
                .build()

        when:
        pharmacyRedisTemplateService.save(dto)
        def result = pharmacyRedisTemplateService.findAll()


        then:
        result.size() ==1
        result.get(0).id == 1L
        result.get(0).getPharmacyAddress() == pharmacyAddress
        result.get(0).getPharmacyName() == pharmacyName
        
    }


    def "save fail"(){
        given:
        PharmacyDto dto = PharmacyDto.builder().build()

        when:
        pharmacyRedisTemplateService.save(dto)
        def result = pharmacyRedisTemplateService.findAll()


        then:
        result.size() ==0
    }


    def "delete"(){
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        PharmacyDto dto = PharmacyDto.builder()
                .id(1L)
                .pharmacyName(pharmacyName)
                .pharmacyAddress(pharmacyAddress)
                .build()


        when:
        pharmacyRedisTemplateService.save(dto)
        pharmacyRedisTemplateService.delete(dto.getId())
        def result = pharmacyRedisTemplateService.findAll()

        then:
        result.size()==0

    }


    

}
