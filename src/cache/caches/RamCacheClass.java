package cache.caches;

import java.util.*;

/**
 * General realisation of one level Ram Cache.
 *
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class RamCacheClass<K, V> implements CacheInterface<K, V> {
    protected Map<K, V> map;

    public RamCacheClass() {
        map = new HashMap<K, V>();
    }

    /**
     * Put Object into Ram Cache.
     *
     * @param key   of Object in the Cache
     * @param value Value of Object in the Cache
     */
    @Override
    public void addObject(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V getObject(K key) {
        return map.get(key);
    }

    @Override
    public V removeObject(K key) {
        return map.remove(key);
    }

    @Override
    public int sizeOfCache() {
        return map.size();
    }

    @Override
    public void clearCache() {
        map.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public String toString() {
        return map.values().toString();
    }

}
