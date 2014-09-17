package cache.realisations;

import cache.caches.HardDiskCacheClass;

public class HardDiskCacheLFURealisation<K, V> extends GeneralCacheLFURealisation<K, V> {
    public HardDiskCacheLFURealisation(int sizeOfCache) {
        super(sizeOfCache);
        cache = new HardDiskCacheClass<K, V>(sizeOfCache);
    }

    public static void main(String[] args) {
        GeneralCacheLFURealisation<String, String> cache = new HardDiskCacheLFURealisation<String, String>(3);
        cache.addObject("key1", "value1");
        cache.addObject("key2", "value2");
        cache.addObject("key3", "value3");
        cache.getObject("key2");
        cache.getObject("key3");
        cache.printAllObjects();
        cache.addObject("key4", "value4");
        cache.addObject("key5", "value5");
        cache.printAllObjects();
        cache.clearCache();
    }
}