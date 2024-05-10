package com.example.projectpharmacy.pharmacy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "pharmacy")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이 안에 비우면 db 기본 전략에 따름
    private Long id; // db에게 위임을 하였기에 1차 캐시에 바로 저장하지 못하고 id값을 받고 db에 저장하고 와서 채워넣게 된다.

    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;

    public void changePharmacyAddress(String address){
        this.pharmacyAddress = address;
    }

}
