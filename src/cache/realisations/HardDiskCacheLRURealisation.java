package cache.realisations;

import cache.caches.HardDiskCacheClass;

import java.io.*;
import java.util.LinkedHashMap;

public class HardDiskCacheLRURealisation<K, V> extends HardDiskCacheClass<K, V> {
    public HardDiskCacheLRURealisation(final int MAX_ENTRIES) {
        map = new LinkedHashMap<K, String>(MAX_ENTRIES, 0.75F, true);
        File folder = new File("temp\\");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public K getEldestKey() {
        return map.keySet().iterator().next();
    }

    public static void main(String[] args) {
        HardDiskCacheLRURealisation<String, String> cache = new HardDiskCacheLRURealisation<String, String>(3);
        try {
            cache.addObject("key1", "value1");
            cache.printAllObjects();
            cache.addObject("key2", "value2");
            //cache.getAllObjects();
            //cache.addObject("key2","value000");
            cache.printAllObjects();
            cache.addObject("key3", "value3");
            cache.printAllObjects();
            //cache.addObject("key1","value000");
            String s = cache.getObject("key1");
            cache.printAllObjects();
            String eldest = cache.getEldestKey();
            System.out.println(eldest);
            System.out.println(cache.removeObject(eldest));
            cache.printAllObjects();
            cache.addObject("key4", "value4");
            //cache.addObject("key5","value5");
            cache.printAllObjects();
            cache.clearCache();
        } catch (Exception e) {
        }
    }
}