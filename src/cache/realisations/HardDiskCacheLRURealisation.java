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
        map = new LinkedHashMap<K, String>(MAX_SIZE, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                //File file = new File(map.get(eldest));
                //file.delete();
                return size() > MAX_SIZE;
            }
        };
    }

    @Override
    public void addObject(K key, V value) {
        //TODO: Add deleting from disk for eldest entry


        //если сделать map.get(eldest), то eldest поменяется!
        //если реализовать через итератор, то сразу и удалять, тогда removeEldestEntry не нужен
        if (sizeOfCache() >= MAX_SIZE) {
            File file = new File(map.get(getEldestKey()));
            file.delete();
        }
        super.addObject(key, value);
    }

    public K getEldestKey() {
        return map.keySet().iterator().next();
    }
}