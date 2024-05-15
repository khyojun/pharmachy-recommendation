package com.example.projectpharmacy.pharmacy.cache

import com.example.projectpharmacy.AbstractIntegrationBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class RedisTemplateTest extends AbstractIntegrationBaseTest{

    @Autowired
    private RedisTemplate redisTemplate;


    def "RedisTemplate String operations"(){
        given:
        def valueOperations = redisTemplate.opsForValue()
        def key = "stringKey"
        def value = "hello"

        when:
        valueOperations.set(key, value)


        then:
        def getValue = valueOperations.get(key)
        getValue == value
    }


    def "RedisTemplate set operations"(){
        given:
        def setOperations = redisTemplate.opsForSet()
        def key = "setKey"

        when:
        setOperations.add(key, "h", "e", "l", "l", "o")


        then:
        def size = setOperations.size(key)
        size == 4
    }


    def "RedistTemplate hash operations"(){
        given:
        def hashOperations = redisTemplate.opsForHash()
        def key = "hashKey"

        when:
        hashOperations.put(key, "subkey", "value")

        then:
        def result = hashOperations.get(key, "subkey")
        result == "value"


        def entrites = hashOperations.entries(key)
        entrites.keySet().contains("subkey")
        entrites.values().contains("value")

        def size = hashOperations.size(key)

        size == entrites.size()
    }

}
