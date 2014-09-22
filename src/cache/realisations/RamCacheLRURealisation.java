package cache.realisations;

import cache.caches.RamCacheClass;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Realisation of one level Ram Cache with LRU strategy.
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class RamCacheLRURealisation<K, V> extends RamCacheClass<K, V> {
    public RamCacheLRURealisation(final int MAX_ENTRIES) {
        map = new LinkedHashMap<K, V>(MAX_ENTRIES, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > MAX_ENTRIES;
            }
        };
    }

    public K getEldestKey() {
        return map.keySet().iterator().next();
    }
}