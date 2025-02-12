package ru.aston.karpenko_ds.task_data_structures;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.HashMap;

public class ArrayUtils {
    public static <T> boolean isSortedAscending(T[] array, Comparator<T> comparator) {
        for (int i=1; i < array.length; i++) {
            if (comparator.compare(array[i], array[i-1]) < 0) {
                return false; // Если текущий элемент меньше предыдущего, массив не отсортирован
            }
        }
        return true; // Если все элементы в порядке, массив отсортирован
    }

    public static <T> T[] swapFirstAndLast(T[] array) {
        // Проверяем, что массив не пустой и содержит более одного элемента
        if (array == null || array.length < 2) {
            throw new IllegalArgumentException("Массив должен содержать хотя бы два элемента для обмена.");
        }

        // Меняем местами первый и последний элементы
        T temp = array[0];
        array[0] = array[array.length-1];
        array[array.length-1] = temp;

        return array;
    }

    public static <T extends Number> T findFirstUniqueNumber(T[] array) {
        HashMap<T, Integer> countMap = new HashMap<>();

        // Подсчет вхождений каждого числа
        for (T num : array) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // Поиск первого уникального числа
        for (T num : array) {
            if (countMap.get(num) == 1) {
                return num; // Возвращаем первое уникальное число
            }
        }

        return null; // Если уникальных чисел нет
    }

    public static <T extends Number & Comparable<? super T>> void mergeSort(Class<T> clazz, T[] array) {
        if (array.length < 2) {
            return; // Массив уже отсортирован
        }
        int mid = array.length / 2;

        // Разделение массива на две половины
        T[] left = (T[]) Array.newInstance(clazz, mid);
        T[] right = (T[]) Array.newInstance(clazz, array.length - mid);

        // Заполнение левой половины
        for (int i=0; i < mid; i++) {
            left[i] = array[i];
        }

        // Заполнение правой половины
        for (int i=mid; i < array.length; i++) {
            right[i-mid] = array[i];
        }

        // Рекурсивная сортировка обеих половин
        mergeSort(clazz, left);
        mergeSort(clazz, right);

        // Слияние отсортированных половин
        merge(array, left, right);
    }

    // Метод для слияния двух отсортированных массивов
    private static <T extends Number & Comparable<? super T>> void merge(T[] array, T[] left, T[] right) {
        int i = 0, j = 0, k = 0;

        // Слияние элементов из левой и правой половин
        while ((i < left.length) && (j < right.length)) {
            if ((left[i].compareTo(right[j]) < 0) || (left[i].compareTo(right[j]) == 0)) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        // Копирование оставшихся элементов из левой половины
        while (i < left.length) {
            array[k++] = left[i++];
        }

        // Копирование оставшихся элементов из правой половины
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
}
