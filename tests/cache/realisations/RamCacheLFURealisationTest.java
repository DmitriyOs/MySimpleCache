package cache.realisations;

import cache.caches.GeneralCacheLFURealisation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RamCacheLFURealisationTest {
    GeneralCacheLFURealisation cache;

    @Before
    public void setUp() {
        cache = new RamCacheLFURealisation(3);
    }

    @After
    public void tearDown() {
        cache.clearCache();
    }

    @Test
    public void testGlobal() {
        cache.addObject("key1", "value1");
        //increase frequency testing
        assertEquals("value1", cache.getObject("key1"));
        assertEquals(2, cache.getFrequency("key1"));
        //min frequency testing
        assertEquals("key1", cache.getMinFrequencyKey());
        //LFU strategy testing
        cache.addObject(2, "key2", 3);
        cache.addObject("key3", 2, 3);
        cache.addObject(4, 4, 3);
        assertNull(cache.getObject("key1"));
        //get and remove testing
        assertNull(cache.getObject("key0"));
        assertEquals("key2", cache.removeObject(2));
        assertNull(cache.removeObject("key0"));
    }

    @Test
    public void testContainsKey() {
        cache.addObject(1, 1);
        assertTrue(cache.containsKey(1));
        assertFalse(cache.containsKey(0));
    }

}