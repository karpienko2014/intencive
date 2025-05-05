package ru.aston.karpenko_ds.data_structures;

import java.util.Arrays;

/**
 * Класс, представляющий реализацию множества (HashSet).
 *
 * @param <T> тип элементов, хранящихся в множестве
 */
public class HashSetImpl<T> {
    private Element<T>[] elements; // Массив для хранения элементов
    private int size; // Текущее количество элементов
    private static final int INITIAL_CAPACITY = 16; // Начальная емкость

    /**
     * Конструктор, создающий множество с начальной емкостью.
     */
    public HashSetImpl() {
        elements = new Element[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Вложенный статический класс для хранения значений.
     *
     * @param <T> тип значения
     */
    private static class Element<T> {
        T value;

        Element(T value) {
            this.value = value;
        }
    }

    /**
     * Добавляет элемент в множество.
     *
     * @param element элемент, который нужно добавить
     * @return true, если элемент был добавлен, false если элемент уже существует
     */
    public boolean add(T element) {
        if (contains(element)) {
            return false; // Элемент уже существует
        }
        ensureCapacity();
        elements[size++] = new Element<>(element);
        return true;
    }

    /**
     * Удаляет элемент из множества.
     *
     * @param element элемент, который нужно удалить
     * @return true, если элемент был удален, false если элемента не было
     */
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (contains(element)) {
                elements[i] = elements[size - 1]; // Перемещаем последний элемент на место удаляемого
                elements[size - 1] = null; // Убираем ссылку на последний элемент
                size--;
                return true;
            }
        }
        return false; // Элемент не найден
    }

    /**
     * Проверяет наличие элемента в множестве.
     *
     * @param element элемент для проверки
     * @return true, если элемент существует, иначе false
     */
    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].value.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает текущее количество элементов в множестве.
     *
     * @return количество элементов в множестве
     */
    public int size() {
        return size;
    }

    /**
     * Увеличивает емкость массива при необходимости.
     */
    private void ensureCapacity() {
        if (size >= elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }
}
