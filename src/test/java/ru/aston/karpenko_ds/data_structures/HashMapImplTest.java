package ru.aston.karpenko_ds.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashMapImplTest {

    HashMapImpl<String, Integer> hashMap;
    String demi = "Demi";
    Integer demiAge = 18;
    String michael = "Michael";
    Integer sizeBeforePut;

    @BeforeEach
    void setUp() {
        hashMap = new HashMapImpl<>();
        hashMap.put("John", 32);
        hashMap.put(demi, demiAge);
    }

    @Test
    void put() {
        Integer age = 26;
        sizeBeforePut = hashMap.size();
        hashMap.put(michael, age);
        assertEquals(age, hashMap.get(michael));
        assertEquals(++sizeBeforePut, hashMap.size());
    }

    @Test
    void putNullKeyValue() {
        sizeBeforePut = hashMap.size();
        hashMap.put(null, null);
        assertNull(hashMap.get(null));
        assertEquals(++sizeBeforePut, hashMap.size());
    }

    @Test
    void updateValue() {
        put();
        Integer age = 46;
        hashMap.put(michael, age);
        assertEquals(age, hashMap.get(michael));
    }

    @Test
    void get() {
        assertEquals(demiAge, hashMap.get(demi));
    }

    @Test
    void remove() {
        Integer sizeBeforeRemove = hashMap.size();
        demiAge = hashMap.remove(demi);
        assertNotNull(demiAge);
        assertEquals(--sizeBeforeRemove, hashMap.size());
    }
}