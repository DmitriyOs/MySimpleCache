package cache.realisations;

import cache.caches.HardDiskCacheClass;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Realisation of one level Hard Disk Cache with LRU strategy.
 *
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class HardDiskCacheLRURealisation<K, V> extends HardDiskCacheClass<K, V> {
    final int MAX_SIZE;

    public HardDiskCacheLRURealisation(int sizeOfCache) {
        MAX_SIZE = sizeOfCache;
        map = new LinkedHashMap<K, String>(MAX_SIZE);
    }

    @Override
    public void addObject(K key, V value) {
        if (sizeOfCache() >= MAX_SIZE) {
            K tKey = getEldestKey();
            File file = new File(map.get(tKey));
            file.delete();
            removeObject(tKey);
        }
        super.addObject(key, value);
    }

    public K getEldestKey() {
        return map.keySet().iterator().next();
    }
}