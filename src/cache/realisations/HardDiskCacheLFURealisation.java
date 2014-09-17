package cache.realisations;

import cache.caches.HardDiskCacheClass;

/**
 * Realisation of one level Hard Disk Cache with LFU strategy.
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class HardDiskCacheLFURealisation<K, V> extends GeneralCacheLFURealisation<K, V> {
    public HardDiskCacheLFURealisation(int sizeOfCache) {
        super(sizeOfCache);
        cache = new HardDiskCacheClass<K, V>(sizeOfCache);
    }
}