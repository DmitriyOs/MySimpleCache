package cache.caches;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class RamCacheClassTest extends HardDiskCacheClassTest {
    @Override
    @Before
    public void setUp() {
        cache = new RamCacheClass();
        cache.addObject("key1", "value1");
        cache.addObject(2, "value2");
        cache.addObject("key3", 3);
    }
}