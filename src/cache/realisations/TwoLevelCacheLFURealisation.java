package cache.realisations;

import cache.caches.CacheInterface;

import java.io.IOException;

//Least-Frequently Used (Наименее часто используемый)
public class TwoLevelCacheLFURealisation<K, V> implements CacheInterface<K, V> {
    final int MAX_SIZE_LEVEL_ONE;
    final int MAX_SIZE_LEVEL_TWO;
    private RamCacheLFURealisation<K, V> ramCache;
    private HardDiskCacheLFURealisation<K, V> hardDiskCache;

    public TwoLevelCacheLFURealisation(int maxSizeLevelOne, int maxSizeLevelTwo) {
        MAX_SIZE_LEVEL_ONE = maxSizeLevelOne;
        MAX_SIZE_LEVEL_TWO = maxSizeLevelTwo;
        ramCache = new RamCacheLFURealisation<K, V>(MAX_SIZE_LEVEL_ONE);
        hardDiskCache = new HardDiskCacheLFURealisation<K, V>(MAX_SIZE_LEVEL_TWO);
    }

    @Override
    public void addObject(K key, V value) throws IOException {
        if (ramCache.sizeOfCache() >= MAX_SIZE_LEVEL_ONE) {
            K tKey = ramCache.getMinFrequencyKey();
            int tFrequency = ramCache.getFrequency(tKey);
            hardDiskCache.addObject(tKey, ramCache.removeObject(tKey), tFrequency);
        }
        ramCache.addObject(key, value);
    }

    @Override
    public V getObject(K key) throws IOException {
        if (ramCache.containsKey(key)) {
            return ramCache.getObject(key);
        }
        if (hardDiskCache.containsKey(key)) {
            K tMinKey = ramCache.getMinFrequencyKey();
            int tMinFrequency = ramCache.getFrequency(tMinKey);
            int frequencyOfKey = hardDiskCache.getFrequency(key);
            if (frequencyOfKey + 1 > tMinFrequency) {
                V value = hardDiskCache.removeObject(key);
                hardDiskCache.addObject(tMinKey, ramCache.removeObject(tMinKey), tMinFrequency);
                ramCache.addObject(key, value, frequencyOfKey + 1);
                return value;
            } else {
                return hardDiskCache.getObject(key);
            }

        }
        return null;
    }

    @Override
    public V removeObject(K key) throws IOException {
        if (ramCache.containsKey(key)) {
            return ramCache.removeObject(key);
        }
        if (hardDiskCache.containsKey(key)) {
            return hardDiskCache.removeObject(key);
        }
        return null;
    }

    @Override
    public int sizeOfCache() {
        return ramCache.sizeOfCache() + hardDiskCache.sizeOfCache();
    }

    public int sizeOfLevel1() {
        return ramCache.sizeOfCache();
    }

    public int sizeOfCache2() {
        return hardDiskCache.sizeOfCache();
    }

    @Override
    public void clearCache() {
        ramCache.clearCache();
        hardDiskCache.clearCache();
    }

    @Override
    public boolean containsKey(K key) {
        if (ramCache.containsKey(key)) {
            return true;
        }
        if (hardDiskCache.containsKey(key)) {
            return true;
        }
        return false;
    }

    @Override
    public void printAllObjects() throws IOException {
        System.out.println("RAM cache (" + ramCache.sizeOfCache() + " items):");
        ramCache.printAllObjects();
        System.out.println("HardDisk cache (" + hardDiskCache.sizeOfCache() + " items):");
        hardDiskCache.printAllObjects();
        System.out.println();
    }

    public static void main(String[] args) {
        TwoLevelCacheLFURealisation<String, String> cache = new TwoLevelCacheLFURealisation<String, String>(2, 3);
        try {
            cache.addObject("key1", "value1");
            cache.addObject("key2", "value2");
            cache.addObject("key3", "value3");
            cache.addObject("key4", "value4");
            cache.addObject("key5", "value5");
            cache.printAllObjects();
            cache.getObject("key3");
            cache.getObject("key4");
            cache.getObject("key5");
            cache.printAllObjects();
            cache.clearCache();

        } catch (Exception e) {
        }
    }
}
