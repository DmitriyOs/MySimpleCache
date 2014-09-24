package cache.caches;

public interface TwoLevelCacheInterface<K, V> extends CacheInterface<K, V> {
    int sizeOfLevel1();
    int sizeOfLevel2();
}
