package ru.aston.karpenko_ds.data_structures;

import java.util.Objects;

/**
 * Класс, представляющий реализацию HashMap.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashMapImpl<K, V> {
    private Entry<K, V>[] table; // Массив для хранения пар "ключ-значение"
    private int size; // Текущее количество элементов
    private static final int INITIAL_CAPACITY = 16; // Начальная емкость

    /**
     * Конструктор, создающий HashMap с начальной емкостью.
     */
    public HashMapImpl() {
        table = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Вложенный статический класс для хранения пар "ключ-значение".
     *
     * @param <K> тип ключа
     * @param <V> тип значения
     */
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next; // Ссылка на следующую запись (для обработки коллизий)

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    /**
     * Добавляет пару "ключ-значение" в HashMap.
     *
     * @param key   ключ, который нужно добавить
     * @param value значение, связанное с ключом
     */
    public void put(K key, V value) {
        int index = getIndex(key, table.length);
        Entry<K, V> newEntry = new Entry<>(key, value);

        if (table[index] == null) {
            table[index] = newEntry;
            size++;
        } else {
            Entry<K, V> current = table[index];
            while (true) {
                if (Objects.equals(current.key, key)) {
                    current.value = value; // Обновляем значение если ключ уже существует
                    return;
                }
                if (current.next == null) {
                    current.next = newEntry; // Добавляем новую запись в конец списка
                    size++;
                    break;
                }
                current = current.next;
            }
        }

        ensureCapacity();
    }

    /**
     * Возвращает значение по указанному ключу.
     *
     * @param key ключ для поиска значения
     * @return значение по указанному ключу или null если ключ не найден
     */
    public V get(K key) {
        int index = getIndex(key, table.length);
        Entry<K, V> current = table[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value; // Возвращаем значение если ключ найден
            }
            current = current.next;
        }

        return null; // Ключ не найден
    }

    /**
     * Удаляет пару "ключ-значение" из HashMap по указанному ключу.
     *
     * @param key ключ для удаления
     * @return значение удаленного элемента или null если элемент не найден
     */
    public V remove(K key) {
        int index = getIndex(key, table.length);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) { // Удаляем первый элемент в списке
                    table[index] = current.next;
                } else { // Удаляем элемент из середины или конца списка
                    previous.next = current.next;
                }
                size--;
                return current.value; // Возвращаем удаленное значение
            }
            previous = current;
            current = current.next;
        }

        return null; // Ключ не найден
    }

    /**
     * Возвращает текущее количество элементов в HashMap.
     *
     * @return количество элементов в HashMap
     */
    public int size() {
        return size;
    }

    /**
     * Вычисляет индекс для хранения элемента на основе его хэш-кода.
     *
     * @param key ключ для вычисления индекса
     * @param length длина массива
     * @return индекс в массиве для хранения элемента
     */
    private int getIndex(K key, int length) {
        return Math.abs(Objects.hashCode(key)) % length; // Используем модуль хэш-кода для получения индекса
    }

    /**
     * Увеличивает емкость массива при необходимости.
     */
    private void ensureCapacity() {
        if (size >= table.length * 0.75) { // Увеличиваем размер при достижении 75% заполненности
            int newCapacity = table.length * 2;
            Entry<K, V>[] newTable = new Entry[newCapacity];

            for (Entry<K, V> entry : table) {
                while (entry != null) {
                    int index = getIndex(entry.key, newCapacity);

                    Entry<K, V> nextEntry = entry.next; // Сохраняем ссылку на следующий элемент

                    entry.next = newTable[index]; // Перемещаем элемент в новый массив
                    newTable[index] = entry;

                    entry = nextEntry; // Переходим к следующему элементу в старом массиве
                }
            }

            table = newTable; // Обновляем ссылку на новый массив
        }
    }
}
