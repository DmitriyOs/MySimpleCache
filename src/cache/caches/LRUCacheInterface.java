package cache.caches;

public interface LRUCacheInterface<K, V> extends CacheInterface<K, V> {
    K getEldestKey();
}
