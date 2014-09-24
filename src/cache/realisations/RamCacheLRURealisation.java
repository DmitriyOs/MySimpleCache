package cache.realisations;

import cache.caches.LRUCacheInterface;
import cache.caches.RamCacheClass;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Realise one level Ram Cache with LRU strategy.
 *
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class RamCacheLRURealisation<K, V> extends RamCacheClass<K, V> implements LRUCacheInterface<K, V> {
    public RamCacheLRURealisation(final int MAX_ENTRIES) {
        //when sizeOfCache > MAX_ENTRIES, the eldest Object will be removed
        map = new LinkedHashMap<K, V>(MAX_ENTRIES, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > MAX_ENTRIES;
            }
        };
    }

    @Override
    public K getEldestKey() {
        return map.keySet().iterator().next();
    }
}