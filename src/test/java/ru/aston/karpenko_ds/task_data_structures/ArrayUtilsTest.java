package ru.aston.karpenko_ds.task_data_structures;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilsTest {

    @Test
    void isSortedAscendingOk() {
        boolean result =
                ArrayUtils.isSortedAscending(
                        new Integer[] {1, 2, 3, 4, 5},
                        Comparator.comparingInt(i -> i)
                );
        assertEquals(TestResult.OK.getText(), getTestResultText(result));
    }

    @Test
    void isSortedAscendingTryAgain() {
        boolean result =
                ArrayUtils.isSortedAscending(
                        new Integer[] {5, 3, 4, 1, 2},
                        Comparator.comparingInt(i -> i)
                );
        assertEquals(TestResult.TRY_AGAIN.getText(), getTestResultText(result));
    }

    private String getTestResultText(boolean result) {
        if (result) {
            return TestResult.OK.getText();
        } else {
            return TestResult.TRY_AGAIN.getText();
        }
    }

    @Test
    void swapFirstAndLast() {
        Integer[] array = new Integer[] {5, 6, 7, 2};
        array = ArrayUtils.swapFirstAndLast(array);
        assertEquals(2, array[0]);
        assertEquals(5, array[3]);
    }

    @Test
    void findFirstUniqueNumber() {
        Integer firstUniqueInteger =
                ArrayUtils.findFirstUniqueNumber(new Integer[] {1, 2, 3, 1, 2, 4});
        assertEquals(3, firstUniqueInteger);

        Double firstUniqueDouble =
                ArrayUtils.findFirstUniqueNumber(new Double[] {10.0, 20.0, 40.0, 10.0, 20.0, 30.0});
        assertEquals(40.0, firstUniqueDouble);
    }

    @Test
    void mergeSort() {
        int size = 10; // Размер массива
        int bound = 100; // Максимальное значение для случайных чисел
        Random random = new Random();
        Integer[] randomArray = new Integer[size];
        for (int i=0; i < size; i++) {
            randomArray[i] = random.nextInt(bound); // Генерация случайного числа до bound
        }

        ArrayUtils.mergeSort(Integer.class, randomArray);

        boolean sortingResult =
                ArrayUtils.isSortedAscending(
                        randomArray,
                        Comparator.comparingInt(i -> i)
                );
        assertTrue(sortingResult);
    }
}