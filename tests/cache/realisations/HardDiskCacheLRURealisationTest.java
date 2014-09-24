package cache.realisations;

import org.junit.Before;

import static org.junit.Assert.*;

public class HardDiskCacheLRURealisationTest extends RamCacheLRURealisationTest {
    @Override
    @Before
    public void setUp() {
        cache = new HardDiskCacheLRURealisation(3);
    }
}