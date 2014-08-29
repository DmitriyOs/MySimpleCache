import java.io.IOException;
import java.util.*;

//Least-Frequently Used (Наименее часто используемый)
public class TwoLevelCacheLFURealisation<K, V> implements CacheInterface<K, V> {
    final int MAX_SIZE_LEVEL_ONE;
    final int MAX_SIZE_LEVEL_TWO;
    private RamCacheClass<K, V> ramCache;
    private HardDiskCacheClass<K, V> hardDiskCache;
    private HashMap<K, Integer> frequencyRam;
    private HashMap<K, Integer> frequencyHardDisk;

    private K getMinKey(HashMap<K, Integer> map) {
        K minKey = null;
        int minValue = Integer.MAX_VALUE;
        for (K key : map.keySet()) {
            if (map.get(key) < minValue) {
                minKey = key;
                minValue = map.get(key);
            }
        }
        return minKey;
    }

    TwoLevelCacheLFURealisation(int maxSizeLevelOne, int maxSizeLevelTwo) {
        MAX_SIZE_LEVEL_ONE = maxSizeLevelOne;
        MAX_SIZE_LEVEL_TWO = maxSizeLevelTwo;
        ramCache = new RamCacheClass<K, V>();
        hardDiskCache = new HardDiskCacheClass<K, V>();
        frequencyRam = new HashMap<K, Integer>();
        frequencyHardDisk = new HashMap<K, Integer>();
    }

    public void addObject(K key, V value) throws IOException {
        if (ramCache.sizeOfCache() >= MAX_SIZE_LEVEL_ONE) {
            if (hardDiskCache.sizeOfCache() >= MAX_SIZE_LEVEL_TWO) {
                K tKey = getMinKey(frequencyHardDisk);
                frequencyHardDisk.remove(tKey);
                hardDiskCache.removeObject(tKey);
            }
            K tKey = getMinKey(frequencyRam);
            frequencyHardDisk.put(tKey, frequencyRam.remove(tKey));
            hardDiskCache.addObject(tKey, ramCache.removeObject(tKey));
        }
        ramCache.addObject(key, value);
        frequencyRam.put(key, 1);
    }

    public V getObject(K key) throws IOException {
        if (ramCache.containsKey(key)) {
            frequencyRam.put(key, frequencyRam.get(key) + 1);
            return ramCache.getObject(key);
        }
        if (hardDiskCache.containsKey(key)) {
            K tMinKey = getMinKey(frequencyRam);
            if (frequencyHardDisk.get(key) + 1 > frequencyRam.get(tMinKey)) {
                frequencyHardDisk.put(tMinKey, frequencyRam.remove(tMinKey));
                hardDiskCache.addObject(tMinKey, ramCache.removeObject(tMinKey));

                frequencyRam.put(key, frequencyHardDisk.remove(key) + 1);
                ramCache.addObject(key, hardDiskCache.removeObject(key));
                return ramCache.getObject(key);
            } else {
                frequencyHardDisk.put(key, frequencyHardDisk.get(key) + 1);
                return hardDiskCache.getObject(key);
            }
        }
        return null;
    }

    public V removeObject(K key) throws IOException {
        if (ramCache.containsKey(key)) {
            frequencyRam.remove(key);
            return ramCache.removeObject(key);
        }
        if (hardDiskCache.containsKey(key)) {
            frequencyHardDisk.remove(key);
            return hardDiskCache.removeObject(key);
        }
        return null;
    }

    public int sizeOfCache() {
        return ramCache.sizeOfCache() + hardDiskCache.sizeOfCache();
    }

    public void clearCache() {
        frequencyRam.clear();
        frequencyHardDisk.clear();
        ramCache.clearCache();
        hardDiskCache.clearCache();
    }

    public boolean containsKey(K key) {
        if (ramCache.containsKey(key)) {
            return true;
        }
        if (hardDiskCache.containsKey(key)) {
            return true;
        }
        return false;
    }

    public void printAllObjects() throws IOException {
        System.out.println("RAM cache (" + ramCache.sizeOfCache() + " items):");
        //System.out.println(frequencyRam);
        ramCache.printAllObjects();
        System.out.println("HardDisk cache (" + hardDiskCache.sizeOfCache() + " items):");
        //System.out.println(frequencyHardDisk);
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
