package cache.realisations;

import cache.caches.CacheInterface;
import cache.caches.GeneralCacheLFURealisation;
import cache.caches.RamCacheClass;

/**
 * Realise one level Ram Cache with LFU strategy.
 *
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class RamCacheLFURealisation<K, V> extends GeneralCacheLFURealisation<K, V> implements CacheInterface<K, V> {
    public RamCacheLFURealisation(int sizeOfCache) {
        super(sizeOfCache);
        cache = new RamCacheClass<K, V>();
    }
}
