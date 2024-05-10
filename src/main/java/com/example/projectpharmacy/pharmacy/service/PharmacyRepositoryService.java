package com.example.projectpharmacy.pharmacy.service;

import com.example.projectpharmacy.pharmacy.entity.Pharmacy;
import com.example.projectpharmacy.pharmacy.repository.PharmacyRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRepositoryService {

    private final PharmacyRepository pharmacyRepository;



    @Transactional
    public void updateAddress(Long id, String address){
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);

        if(Objects.isNull(pharmacy)){
            log.error("[PharmacyRepositoryService updateAddress] not found id : {}", id);
            return;
        }

        pharmacy.changePharmacyAddress(address);
    }

    //for test
    public void updateAddressWithoutTransaction(Long id, String address){
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);

        if(Objects.isNull(pharmacy)){
            log.error("[PharmacyRepositoryService updateAddress] not found id : {}", id);
            return;
        }

        pharmacy.changePharmacyAddress(address);
    }

}
