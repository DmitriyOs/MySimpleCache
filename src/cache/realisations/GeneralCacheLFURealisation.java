package cache.realisations;

import cache.caches.CacheInterface;
import cache.frequency.FrequencyClass;

/**
 * Realise LFU strategy in one level Cache.
 *
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
abstract class GeneralCacheLFURealisation<K, V> implements CacheInterface<K, V> {
    final int MAX_SIZE;
    CacheInterface<K, V> cache;
    private FrequencyClass<K> frequencyMap = new FrequencyClass<K>();

    protected GeneralCacheLFURealisation(int sizeOfCache) {
        MAX_SIZE = sizeOfCache;
    }

    /**
     * Put Object into Cache with frequency equals 1. Remove least frequency used object when cache is full.
     *
     * @param key   Key of Object in the Cache
     * @param value Value of Object in the Cache
     */
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

    /**
     * Put Object into Cache with frequency equals valueOfFrequency. Remove least frequency used object when cache is full.
     *
     * @param key              Key of Object in the Cache
     * @param value            Value of Object in the Cache
     * @param valueOfFrequency Frequency of Object
     */
    public void addObject(K key, V value, int valueOfFrequency) {
        if (cache.sizeOfCache() >= MAX_SIZE) {
            K minKey = getMinFrequencyKey();
            cache.removeObject(minKey);
            frequencyMap.remove(minKey);
        }
        cache.addObject(key, value);
        frequencyMap.add(key, valueOfFrequency);
    }

    /**
     * Get Object from cache. Increase frequency.
     *
     * @param key Key of Object
     * @return value
     */
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

    @Override
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    @Override
    public String toString() {
        return cache.toString();
    }

    public int getFrequency(K key) {
        return frequencyMap.get(key);
    }

    public K getMinFrequencyKey() {
        return frequencyMap.getMinFrequencyKey();
    }
}
