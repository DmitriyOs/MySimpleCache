package cache;

import cache.caches.CacheInterface;
import cache.realisations.TwoLevelCacheLFURealisation;
import cache.realisations.TwoLevelCacheLRURealisation;

public class Main {

    static CacheInterface<String, String> cache;
    static String strategy = "LRU";
    static int sizeLevelOne = 2;
    static int sizeLevelTwo = 3;

    public static void main(String args[]) {
        switch (args.length) {
            case 3:
                try {
                    int i = Integer.parseInt(args[1]);
                    if (i < 0) break;
                    sizeLevelOne = i;

                    i = Integer.parseInt(args[2]);
                    if (i < 0) break;
                    sizeLevelTwo = i;

                } catch (Exception e) {
                    sizeLevelOne = 2;
                    sizeLevelTwo = 3;
                }
            case 1:
                if (args[0].equals("LFU")) strategy = "LFU";
                break;
            default:
                sizeLevelOne = 2;
                sizeLevelTwo = 3;
                strategy = "LRU";
        }

        System.out.println(strategy);
        System.out.println(sizeLevelOne + " " + sizeLevelTwo);

        if (strategy.equals("LRU")) {
            cache = new TwoLevelCacheLRURealisation<String, String>(sizeLevelOne, sizeLevelTwo);
        } else if (strategy.equals("LFU")) {
            cache = new TwoLevelCacheLFURealisation<String, String>(sizeLevelOne, sizeLevelTwo);
        }

        try {
            cache.addObject("key1", "value1");
            cache.addObject("key2", "value2");
            cache.addObject("key3", "value3");
            cache.addObject("key4", "value4");
            cache.addObject("key5", "value5");
            System.out.println("Added 5 objects:");
            cache.printAllObjects();
            cache.getObject("key3");
            cache.getObject("key4");
            cache.getObject("key5");
            System.out.println("Objects 3,4,5 used:");
            cache.printAllObjects();
            System.out.println("Add object 6:");
            cache.addObject("key6", "value6");
            cache.printAllObjects();
            cache.clearCache();

        } catch (Exception e) {
        }


    }
}