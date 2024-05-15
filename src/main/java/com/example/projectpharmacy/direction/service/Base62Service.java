package com.example.projectpharmacy.direction.service;


import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Base62Service {

    private static final Base62 bas62Instance = Base62.createInstance();


    //db sequence 값 이용해서 진행
    public String encodeDirectionId(Long directionId){
        return new String(bas62Instance.encode(String.valueOf(directionId).getBytes()));
    }

    public Long decodeDirectionId(String encodedDirectionId){
        String resultDirectionId = new String(bas62Instance.decode(encodedDirectionId.getBytes()));
        return Long.valueOf(resultDirectionId);
    }


}
