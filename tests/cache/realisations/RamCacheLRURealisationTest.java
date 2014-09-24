package cache.realisations;

import cache.caches.LRUCacheInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RamCacheLRURealisationTest {
    LRUCacheInterface cache;

    @Before
    public void setUp() {
        cache = new RamCacheLRURealisation(3);
    }

    @After
    public void tearDown() {
        cache.clearCache();
    }

    @Test
    public void testGlobal() {
        cache.addObject("key1","value1");
        cache.addObject(2,2);
        assertEquals("key1",cache.getEldestKey());
        cache.getObject("key1");
        assertEquals(2,cache.getEldestKey());
        cache.addObject(3,"value3");
        cache.addObject(4,4);
        assertNull(cache.getObject(2));
    }

}