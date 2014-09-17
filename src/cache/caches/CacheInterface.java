package cache.caches;

public interface CacheInterface<K, V> {
    void addObject(K key, V value);
    V getObject(K key);
    V removeObject(K key);
    int sizeOfCache();
    void clearCache();
    boolean containsKey(K key);
    void printAllObjects();
}
