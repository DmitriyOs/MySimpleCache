import java.io.IOException;

//Least Recently Used (Вытеснение давно неиспользуемых)
public class TwoLevelCacheLRURealisation<K, V> implements CacheInterface<K, V> {
    final int MAX_SIZE_LEVEL_ONE;
    final int MAX_SIZE_LEVEL_TWO;
    private RamCacheClassLRU<K, V> ramCache;
    private HardDiskCacheClassLRU<K, V> hardDiskCache;


    TwoLevelCacheLRURealisation(int maxSizeLevelOne, int maxSizeLevelTwo) {
        MAX_SIZE_LEVEL_ONE = maxSizeLevelOne;
        MAX_SIZE_LEVEL_TWO = maxSizeLevelTwo;
        ramCache = new RamCacheClassLRU<K, V>(MAX_SIZE_LEVEL_ONE);
        hardDiskCache = new HardDiskCacheClassLRU<K, V>(MAX_SIZE_LEVEL_TWO);

    }

    public void addObject(K key, V value) throws IOException {
        if (ramCache.sizeOfCache() == MAX_SIZE_LEVEL_ONE) {
            K eldestKey = ramCache.getEldestKey();
            hardDiskCache.addObject(eldestKey, ramCache.removeObject(eldestKey));
        }
        ramCache.addObject(key, value);

    }

    public V getObject(K key) throws IOException {
        if (ramCache.containsKey(key)) {
            return ramCache.getObject(key);
        }
        if (hardDiskCache.containsKey(key)) {
            this.addObject(key, hardDiskCache.removeObject(key));
            return ramCache.getObject(key);
        }
        return null;
    }

    public V removeObject(K key) throws IOException {
        if (ramCache.containsKey(key)) {
            return ramCache.removeObject(key);
        }
        if (hardDiskCache.containsKey(key)) {
            return hardDiskCache.removeObject(key);
        }
        return null;
    }

    public int sizeOfCache() {
        return ramCache.sizeOfCache() + hardDiskCache.sizeOfCache();
    }

    public void clearCache() {
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
        ramCache.printAllObjects();
        System.out.println("HardDisk cache (" + hardDiskCache.sizeOfCache() + " items):");
        hardDiskCache.printAllObjects();
        System.out.println();
    }

    public static void main(String[] args) {
        TwoLevelCacheLRURealisation<String, String> cache = new TwoLevelCacheLRURealisation<String, String>(2, 3);
        try {
            cache.addObject("key1", "value1");
            cache.addObject("key2", "value2");
            cache.addObject("key3", "value3");
            cache.addObject("key4", "value4");
            cache.addObject("key5", "value5");
            cache.printAllObjects();
            cache.addObject("key6", "value6");
            cache.printAllObjects();
            System.out.println("!!!RECACHE TESTING!!!");
            cache.getObject("key3");
            cache.printAllObjects();
            cache.clearCache();

        } catch (Exception e) {
        }
    }
}
