package cache.realisations;

import cache.caches.CacheInterface;

/**
 * Realise two level Cache with LFU strategy.
 *
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class TwoLevelCacheLFURealisation<K, V> implements CacheInterface<K, V> {
    final int MAX_SIZE_LEVEL_ONE;
    final int MAX_SIZE_LEVEL_TWO;
    private RamCacheLFURealisation<K, V> ramCache;
    private HardDiskCacheLFURealisation<K, V> hardDiskCache;

    public TwoLevelCacheLFURealisation(int maxSizeLevelOne, int maxSizeLevelTwo) {
        MAX_SIZE_LEVEL_ONE = maxSizeLevelOne;
        MAX_SIZE_LEVEL_TWO = maxSizeLevelTwo;
        ramCache = new RamCacheLFURealisation<K, V>(MAX_SIZE_LEVEL_ONE);
        hardDiskCache = new HardDiskCacheLFURealisation<K, V>(MAX_SIZE_LEVEL_TWO);
    }

    /**
     * Put Object into cache. Recache if level 1 is full.
     *
     * @param key   key of object
     * @param value value of object
     */
    @Override
    public void addObject(K key, V value) {
        if (ramCache.sizeOfCache() >= MAX_SIZE_LEVEL_ONE) {
            K tKey = ramCache.getMinFrequencyKey();
            int tFrequency = ramCache.getFrequency(tKey);
            hardDiskCache.addObject(tKey, ramCache.removeObject(tKey), tFrequency);
        }
        ramCache.addObject(key, value);
    }

    /**
     * Get Value of Object from Cache.
     * Recache if object in level 2 have frequency more
     * than min frequency in level 1.
     *
     * @param key Key of Object
     * @return Value of Object
     */
    @Override
    public V getObject(K key) {
        if (ramCache.containsKey(key)) {
            return ramCache.getObject(key);
        }
        if (hardDiskCache.containsKey(key)) {
            K tMinKey = ramCache.getMinFrequencyKey();
            int tMinFrequency = ramCache.getFrequency(tMinKey);
            int frequencyOfKey = hardDiskCache.getFrequency(key);
            if (frequencyOfKey + 1 > tMinFrequency) {
                V value = hardDiskCache.removeObject(key);
                hardDiskCache.addObject(tMinKey, ramCache.removeObject(tMinKey), tMinFrequency);
                ramCache.addObject(key, value, frequencyOfKey + 1);
                return value;
            } else {
                return hardDiskCache.getObject(key);
            }

        }
        return null;
    }

    @Override
    public V removeObject(K key) {
        if (ramCache.containsKey(key)) {
            return ramCache.removeObject(key);
        }
        if (hardDiskCache.containsKey(key)) {
            return hardDiskCache.removeObject(key);
        }
        return null;
    }

    @Override
    public int sizeOfCache() {
        return ramCache.sizeOfCache() + hardDiskCache.sizeOfCache();
    }

    public int sizeOfLevel1() {
        return ramCache.sizeOfCache();
    }

    public int sizeOfCache2() {
        return hardDiskCache.sizeOfCache();
    }

    @Override
    public void clearCache() {
        ramCache.clearCache();
        hardDiskCache.clearCache();
    }

    @Override
    public boolean containsKey(K key) {
        if (ramCache.containsKey(key)) {
            return true;
        }
        if (hardDiskCache.containsKey(key)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "RAM cache:\n" + ramCache.toString() + "\nHardDisk cache:\n" + hardDiskCache.toString();
    }
}
