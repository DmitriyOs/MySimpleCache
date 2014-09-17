package cache.realisations;

import cache.caches.HardDiskCacheClass;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Realisation of one level Hard Disk Cache with LRU strategy.
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class HardDiskCacheLRURealisation<K, V> extends HardDiskCacheClass<K, V> {
    public HardDiskCacheLRURealisation(final int MAX_ENTRIES) {
        map = new LinkedHashMap<K, String>(MAX_ENTRIES, 0.75F, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > MAX_ENTRIES;
            }
        };
    }

    public K getEldestKey() {
        return map.keySet().iterator().next();
    }
}