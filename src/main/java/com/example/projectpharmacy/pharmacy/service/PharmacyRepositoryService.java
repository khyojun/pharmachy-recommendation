package com.example.projectpharmacy.pharmacy.service;

import com.example.projectpharmacy.pharmacy.entity.Pharmacy;
import com.example.projectpharmacy.pharmacy.repository.PharmacyRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRepositoryService {

    private final PharmacyRepository pharmacyRepository;


    //self invocation test
    public void bar(List<Pharmacy> pharmacyList){
        log.info("bar CurrentTransactionName: " + TransactionSynchronizationManager.getCurrentTransactionName());
        foo(pharmacyList);
    }

    @Transactional
    public void foo(List<Pharmacy> pharmacyList) {
        log.info("foo CurrentTransactionName: " + TransactionSynchronizationManager.getCurrentTransactionName());
        pharmacyList.forEach(pharmacy -> {
            pharmacyRepository.save(pharmacy);
            throw new RuntimeException("error"); // 예외 발생
        });
    }


    @Transactional
    public void updateAddress(Long id, String address){
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);

        if(Objects.isNull(pharmacy)){
            log.error("[PharmacyRepositoryService updateAddress] not found id : {}", id);
            return;
        }

        pharmacy.changePharmacyAddress(address);
    }


    @Transactional(readOnly = true)
    public List<Pharmacy> findAll(){
        return pharmacyRepository.findAll();
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
