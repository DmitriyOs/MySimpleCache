package cache.caches;

import java.io.*;
import java.util.*;

/**
 * General realisation of one level Hard Disk Cache.
 *
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class HardDiskCacheClass<K, V> implements CacheInterface<K, V> {
    protected Map<K, String> map;

    public HardDiskCacheClass() {
        map = new HashMap<K, String>();

        File folder = new File("temp\\");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * Put Object into Hard Disk Cache.
     *
     * @param key   Key of Object in the Cache
     * @param value Value of Object in the Cache
     */
    @Override
    public void addObject(K key, V value) {
        String file = "temp\\" + key + ".cache";
        map.put(key, file);

        FileOutputStream fileStream;
        ObjectOutputStream objectStream;

        try {
            fileStream = new FileOutputStream(file);
            objectStream = new ObjectOutputStream(fileStream);

            objectStream.writeObject(value);

            objectStream.flush();
            objectStream.close();
            fileStream.flush();
            fileStream.close();
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private V getFromFile(String file) {

        try {
            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            V value = (V) objectStream.readObject();

            fileStream.close();
            objectStream.close();

            return value;
        } catch (IOException ex) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    @Override
    public V getObject(K key) {
        return getFromFile(map.get(key));
    }

    @Override
    public V removeObject(K key) {
        V result = getFromFile(map.get(key));
        File file = new File(map.remove(key));
        file.delete();
        return result;
    }

    @Override
    public int sizeOfCache() {
        return map.size();
    }

    @Override
    public void clearCache() {
        Collection<String> values = map.values();
        for (String path : values) {
            File file = new File(path);
            file.delete();
        }
        map.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public String toString() {
        Collection<String> files = map.values();
        LinkedHashSet<V> values = new LinkedHashSet<V>();

        for (String file : files) {
            values.add(getFromFile(file));

        }
        return values.toString();
    }

}
