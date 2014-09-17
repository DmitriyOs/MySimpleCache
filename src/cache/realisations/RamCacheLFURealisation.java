package cache.realisations;

import cache.caches.RamCacheClass;
import cache.frequency.FrequencyClass;

public class RamCacheLFURealisation<K, V> extends RamCacheClass<K, V> {
    final int MAX_SIZE;
    private FrequencyClass<K> frequencyMap;

    public RamCacheLFURealisation(int sizeOfCache) {
        MAX_SIZE = sizeOfCache;
        frequencyMap = new FrequencyClass<K>();
    }

    @Override
    public void addObject(K key, V value) {
        if (super.sizeOfCache() >= MAX_SIZE) {
            K minKey = getMinFrequencyKey();
            super.removeObject(minKey);
            frequencyMap.remove(minKey);
        }
        super.addObject(key, value);
        frequencyMap.add(key);
    }

    public void addObject(K key, V value, int valueOfFrequency) {
        super.addObject(key, value);
        frequencyMap.add(key, valueOfFrequency);
    }

    @Override
    public V getObject(K key) {
        frequencyMap.increment(key);
        return super.getObject(key);
    }

    @Override
    public V removeObject(K key) {
        frequencyMap.remove(key);
        return super.removeObject(key);

    }

    @Override
    public void clearCache() {
        super.clearCache();
        frequencyMap.clear();
    }

    public int getFrequency(K key) {
        return frequencyMap.get(key);
    }

    public K getMinFrequencyKey() {
        return frequencyMap.getMinFrequencyKey();
    }

    public static void main(String[] args) {
        RamCacheLFURealisation<String, String> cache = new RamCacheLFURealisation<String, String>(3);
        cache.addObject("key1", "value1");
        cache.addObject("key2", "value2");
        cache.addObject("key3", "value3");
        cache.getObject("key2");
        cache.getObject("key3");
        cache.printAllObjects();
        cache.addObject("key4", "value4");
        cache.addObject("key5", "value5");
        cache.printAllObjects();
        cache.clearCache();
    }

}
