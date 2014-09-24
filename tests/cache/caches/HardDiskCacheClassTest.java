package cache.caches;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class HardDiskCacheClassTest {
    CacheInterface cache;

    @Before
    public void setUp() {
        cache = new HardDiskCacheClass();
        cache.addObject("key1", "value1");
        cache.addObject(2, "value2");
        cache.addObject("key3", 3);
    }

    @After
    public void tearDown() {
        cache.clearCache();
    }

    @Test
    public void testContainsKey() {
        assertTrue(cache.containsKey("key1"));
        assertTrue(cache.containsKey(2));
        assertFalse(cache.containsKey("key4"));
    }

    @Test
    public void testGetObject() {
        assertEquals("value1", cache.getObject("key1"));
        assertEquals("value2", cache.getObject(2));
        assertEquals(3, cache.getObject("key3"));
        assertNull(cache.getObject("key4"));
    }

    @Test
    public void testRemoveObject() {
        assertEquals("value1", cache.removeObject("key1"));
        assertNull(cache.removeObject("key1"));
        assertNull(cache.removeObject(123));

    }

}