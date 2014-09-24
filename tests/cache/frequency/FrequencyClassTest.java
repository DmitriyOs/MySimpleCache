package cache.frequency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FrequencyClassTest {
    FrequencyClass frequency;

    @Before
    public void setUp() {
        frequency = new FrequencyClass();
    }

    @After
    public void tearDown() {
        frequency.clear();
    }

    @Test
    public void testAdd() {
        frequency.add("key1");
        frequency.add(2);
        frequency.add(3, 5);
        assertEquals(1, frequency.get("key1"));
        assertEquals(1, frequency.get(2));
        assertEquals(5, frequency.get(3));
        assertEquals(0, frequency.get("key"));
    }

    @Test
    public void testGet() {
        assertEquals(0, frequency.get("key"));
    }

    @Test
    public void testRemove() {
        assertEquals(0, frequency.remove("key"));
    }

    @Test
    public void testIncrement() throws Exception {
        frequency.add(1, 2);
        frequency.increment(1);
        assertEquals(3, frequency.get(1));
        frequency.increment("key");
    }

    @Test
    public void testGetMinFrequencyKey() throws Exception {
        assertNull(frequency.getMinFrequencyKey());
        frequency.add(1, 3);
        frequency.add("key1", 3);
        frequency.add(0, 2);
        assertEquals(0, frequency.getMinFrequencyKey());
    }
}