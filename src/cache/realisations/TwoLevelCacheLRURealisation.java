package cache.realisations;

import cache.caches.CacheInterface;

/**
 * Realise two level Cache with LRU strategy.
 *
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class TwoLevelCacheLRURealisation<K, V> implements CacheInterface<K, V> {
    final int MAX_SIZE_LEVEL_ONE;
    final int MAX_SIZE_LEVEL_TWO;
    private RamCacheLRURealisation<K, V> ramCache;
    private HardDiskCacheLRURealisation<K, V> hardDiskCache;

    public TwoLevelCacheLRURealisation(int maxSizeLevelOne, int maxSizeLevelTwo) {
        MAX_SIZE_LEVEL_ONE = maxSizeLevelOne;
        MAX_SIZE_LEVEL_TWO = maxSizeLevelTwo;
        ramCache = new RamCacheLRURealisation<K, V>(MAX_SIZE_LEVEL_ONE);
        hardDiskCache = new HardDiskCacheLRURealisation<K, V>(MAX_SIZE_LEVEL_TWO);
    }

    /**
     * Put Object into cache. Recache if level 1 is full.
     *
     * @param key key of object
     * @param value value of object
     */
    @Override
    public void addObject(K key, V value) {
        if (ramCache.sizeOfCache() == MAX_SIZE_LEVEL_ONE) {
            K eldestKey = ramCache.getEldestKey();
            hardDiskCache.addObject(eldestKey, ramCache.removeObject(eldestKey));
        }
        ramCache.addObject(key, value);
    }

    /**
     * Get Object from cache. Recache if object on level 2 (LRU strategy).
     *
     * @paramkey key of object
     * @return value of object
     */
    @Override
    public V getObject(K key) {
        if (ramCache.containsKey(key)) {
            return ramCache.getObject(key);
        }
        if (hardDiskCache.containsKey(key)) {
            this.addObject(key, hardDiskCache.removeObject(key));
            return ramCache.getObject(key);
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

    public int sizeOfLevel2() {
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
