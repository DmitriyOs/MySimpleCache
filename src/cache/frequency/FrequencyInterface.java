package cache.frequency;

public interface FrequencyInterface<K> {
    void add(K key);
    void add(K key, int value);
    void increment(K key);
    int get(K key);
    int remove(K key);
    K getMinFrequencyKey();
    void clear();

}
