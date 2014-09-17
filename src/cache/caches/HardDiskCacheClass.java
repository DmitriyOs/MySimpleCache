package cache.caches;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

public class HardDiskCacheClass<K, V> implements CacheInterface<K, V> {
    protected Map<K, String> map;

    public HardDiskCacheClass(int size) {
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


    public static void main(String[] args) {
        HardDiskCacheClass<String, String> cache = new HardDiskCacheClass<String, String>();
        try {
            cache.addObject("key1", "value1");
            cache.printAllObjects();
            cache.addObject("key2", "value2");
            cache.printAllObjects();
            cache.clearCache();
        } catch (Exception e) {
        }

    }
}
