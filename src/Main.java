import cache.caches.TwoLevelCacheInterface;
import cache.realisations.TwoLevelCacheLFURealisation;
import cache.realisations.TwoLevelCacheLRURealisation;

public class Main {

    private static TwoLevelCacheInterface cache;

    static void print(String s) {
        System.out.println(s);
    }

    static void outputCache() {
        print("level 1 size==" + cache.sizeOfLevel1() + ";  level 2 size==" + cache.sizeOfLevel2());
        print(cache.toString());
        print("");
    }

    static int sizeLevelOne = 2;
    static int sizeLevelTwo = 3;

    public static void main(String[] args) {
        print("level 1 max size==" + sizeLevelOne);
        print("level 2 max size==" + sizeLevelTwo);
        print("LRU cache presentation:");
        cache = new TwoLevelCacheLRURealisation(sizeLevelOne, sizeLevelTwo);
        outputCache();
        print("input values:");
        cache.addObject(1, "value1");
        cache.addObject(2, "value2");
        cache.addObject(3, "value3");
        cache.addObject(4, "value4");
        cache.addObject(5, "value5");
        outputCache();
        print("get value3 from cache:");
        cache.getObject(3);
        outputCache();
        print("get value5 from cache:");
        cache.getObject(5);
        outputCache();
        print("add value6 to cache, remove least recently used object");
        cache.addObject(6, "value6");
        outputCache();
        print("clear cache");
        cache.clearCache();
        outputCache();

        print("LFU cache presentation:");
        cache = new TwoLevelCacheLFURealisation(sizeLevelOne, sizeLevelTwo);
        outputCache();
        print("input values:");
        cache.addObject(1, "value1");
        cache.addObject(2, "value2");
        cache.addObject(3, "value3");
        cache.addObject(4, "value4");
        cache.addObject(5, "value5");
        outputCache();
        print("get value1, value2, value3, value5 from cache:");
        cache.getObject(1);
        cache.getObject(2);
        cache.getObject(3);
        cache.getObject(5);
        outputCache();
        print("add value6 to cache, remove least frequently used object");
        cache.addObject(6, "value6");
        outputCache();
        print("clear cache");
        cache.clearCache();
        outputCache();
    }

}