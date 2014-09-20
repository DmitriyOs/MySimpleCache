package cache.caches;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 * General realisation of one level Hard Disk Cache.
 *
 * @param <K> Key of Object in the Cache
 * @param <V> Value of Object in the Cache
 */
public class HardDiskCacheClass<K, V> implements CacheInterface<K, V> {
    protected Map<K, String> map;

    /**
     * Constructor of one level Hard Disk Cache.
     *
     * @param sizeOfCache size of Cache
     */
    public HardDiskCacheClass(int sizeOfCache) {
        map = new HashMap<K, String>();

        File folder = new File("temp\\");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    protected HardDiskCacheClass() {
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

    /**
     * Read Object from HDD.
     *
     * @param key Key of Object in the Cache
     * @return Value of Object
     */
    private V getFromFile(K key) {
        String file = map.get(key);

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
        return getFromFile(key);
    }

    @Override
    public V removeObject(K key) {
        V result = getFromFile(key);
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

    /**
     * Print all values from the Hard Disk Cache.
     */
    @Override
    public void printAllObjects() {
        Collection<String> values = map.values();
        System.out.print("[ ");
        for (String file : values) {
            try {
                FileInputStream fileStream = new FileInputStream(file);
                ObjectInputStream objectStream = new ObjectInputStream(fileStream);
                V value = (V) objectStream.readObject();

                fileStream.close();
                objectStream.close();

                System.out.print(value + " ");
            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
            }
        }
        System.out.println("]");
    }
}
