package cache.caches;

import java.util.*;

public class RamCacheClass<K, V> implements CacheInterface<K, V> {
    protected Map<K, V> map;

    public RamCacheClass() {
        map = new HashMap<K, V>();
    }

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

    @Override
    public void printAllObjects() {
        System.out.println(map.values());
    }


    public static void main(String[] args) {
        RamCacheClass<String, String> cache = new RamCacheClass<String, String>();
        cache.addObject("key1", "value1");
        cache.addObject("key2", "value2");
        cache.addObject("key3", "value3");
        cache.addObject("key4", "value4");
        cache.addObject("key5", "value5");
        cache.printAllObjects();
        cache.addObject("key3", "value000");
        cache.printAllObjects();
        cache.removeObject("key4");
        cache.printAllObjects();
        cache.removeObject("key4");
        cache.printAllObjects();
        System.out.println(cache.sizeOfCache());
        cache.clearCache();
        System.out.println(cache.sizeOfCache());
        cache.printAllObjects();
    }
}
