package cache.realisations;

import cache.caches.CacheInterface;

import java.io.IOException;

/**
 * Realisation of two level Cache with LRU strategy.
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

    @Override
    public void addObject(K key, V value) {
        if (ramCache.sizeOfCache() == MAX_SIZE_LEVEL_ONE) {
            K eldestKey = ramCache.getEldestKey();
            hardDiskCache.addObject(eldestKey, ramCache.removeObject(eldestKey));
        }
        ramCache.addObject(key, value);
    }

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
