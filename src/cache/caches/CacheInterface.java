package cache.caches;

import java.io.IOException;

public interface CacheInterface<K, V> {
    void addObject(K key, V value) throws IOException;
    V getObject(K key) throws IOException;
    V removeObject(K key) throws IOException;
    int sizeOfCache();
    void clearCache();
    boolean containsKey(K key);
    void printAllObjects() throws IOException;
}
