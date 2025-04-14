package ru.aston.karpenko_ds.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashSetImplTest {

    private HashSetImpl<Integer> hashSet;
    private Integer sameElement = 5;
    private Integer addedElement = 8;
    private Integer missingElement = 256;

    @BeforeEach
    void setUp() {
        hashSet = new HashSetImpl<>();
        hashSet.add(sameElement);
        hashSet.add(6);
        hashSet.add(7);
    }

    @Test
    void add() {
        boolean add = hashSet.add(sameElement);
        assertFalse(add);

        add = hashSet.add(addedElement);
        assertTrue(add);
        assertTrue(hashSet.contains(addedElement));
    }

    @Test
    void remove() {
        boolean remove = hashSet.remove(missingElement);
        assertFalse(remove);

        remove = hashSet.remove(sameElement);
        assertTrue(remove);
    }

    @Test
    void contains() {
        boolean contains = hashSet.contains(missingElement);
        assertFalse(contains);

        contains = hashSet.contains(sameElement);
        assertTrue(contains);
    }
}