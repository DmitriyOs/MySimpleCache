package cache.realisations;

import cache.caches.CacheInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoLevelCacheLRURealisationTest {
    TwoLevelCacheLRURealisation cache;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        cache.clearCache();
    }

    @Test
    public void recacheTesting() {
        cache = new TwoLevelCacheLRURealisation(2, 2);
        cache.addObject(1, 1);
        assertEquals(1, cache.sizeOfLevel1());
        assertEquals(0, cache.sizeOfLevel2());
        cache.addObject(2, 2);
        assertEquals(2, cache.sizeOfLevel1());
        assertEquals(0, cache.sizeOfLevel2());
        cache.addObject(3, 3);
        assertEquals(2, cache.sizeOfLevel1());
        assertEquals(1, cache.sizeOfLevel2());
        cache.addObject(4, 4);
        assertEquals(2, cache.sizeOfLevel1());
        assertEquals(2, cache.sizeOfLevel2());
        cache.getObject(1);
        cache.removeObject(1);
        assertEquals(1, cache.sizeOfLevel1());
        assertEquals(2, cache.sizeOfLevel2());
        cache.getObject(3);
        assertEquals(2, cache.sizeOfLevel1());
        assertEquals(1, cache.sizeOfLevel2());
        cache.addObject(4, 4);
        cache.addObject(5, 5);
        assertEquals(2, cache.sizeOfLevel1());
        assertEquals(2, cache.sizeOfLevel2());
    }

    @Test
    public void testGetObject() {
        cache = new TwoLevelCacheLRURealisation(1, 1);
        cache.addObject(1, 1);
        cache.addObject("key2", "value2");
        assertEquals(1, cache.getObject(1));
        assertEquals("value2", cache.getObject("key2"));
        assertNull(cache.getObject(0));
    }

    @Test
    public void testRemoveObject() {
        cache = new TwoLevelCacheLRURealisation(1, 1);
        cache.addObject(1, 1);
        cache.addObject("key2", "value2");
        assertEquals(1, cache.removeObject(1));
        assertEquals("value2", cache.removeObject("key2"));
        assertNull(cache.removeObject(0));
    }

    @Test
    public void testContainsKey() {
        cache = new TwoLevelCacheLRURealisation(1, 1);
        cache.addObject(1, 1);
        cache.addObject("key2", "value2");
        assertTrue(cache.containsKey(1));
        assertTrue(cache.containsKey("key2"));
        assertFalse(cache.containsKey("key0"));
    }
}