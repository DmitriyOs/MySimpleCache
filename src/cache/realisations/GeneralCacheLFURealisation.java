package cache.realisations;

import cache.caches.CacheInterface;
import cache.frequency.FrequencyClass;

abstract class GeneralCacheLFURealisation<K, V> implements CacheInterface<K, V> {
    final int MAX_SIZE;
    CacheInterface<K, V> cache;
    private FrequencyClass<K> frequencyMap = new FrequencyClass<K>();

    public GeneralCacheLFURealisation(int sizeOfCache) {
        MAX_SIZE = sizeOfCache;
    }

    @Override
    public void addObject(K key, V value) {
        if (cache.sizeOfCache() >= MAX_SIZE) {
            K minKey = getMinFrequencyKey();
            cache.removeObject(minKey);
            frequencyMap.remove(minKey);
        }
        cache.addObject(key, value);
        frequencyMap.add(key);
    }

    public void addObject(K key, V value, int valueOfFrequency) {
        if (cache.sizeOfCache() >= MAX_SIZE) {
            K minKey = getMinFrequencyKey();
            cache.removeObject(minKey);
            frequencyMap.remove(minKey);
        }
        cache.addObject(key, value);
        frequencyMap.add(key, valueOfFrequency);
    }

    @Override
    public V getObject(K key) {
        frequencyMap.increment(key);
        return cache.getObject(key);
    }

    @Override
    public V removeObject(K key) {
        frequencyMap.remove(key);
        return cache.removeObject(key);

    }

    @Override
    public int sizeOfCache() {
        return cache.sizeOfCache();
    }

    @Override
    public void clearCache() {
        cache.clearCache();
        frequencyMap.clear();
    }

    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    @Override
    public void printAllObjects() {
        cache.printAllObjects();
    }

    public int getFrequency(K key) {
        return frequencyMap.get(key);
    }

    public K getMinFrequencyKey() {
        return frequencyMap.getMinFrequencyKey();
    }
}
