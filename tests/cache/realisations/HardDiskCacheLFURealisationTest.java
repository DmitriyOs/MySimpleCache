package cache.realisations;

import org.junit.Before;

import static org.junit.Assert.*;

public class HardDiskCacheLFURealisationTest extends RamCacheLFURealisationTest {

    @Override
    @Before
    public void setUp() {
        cache = new HardDiskCacheLFURealisation(3);
    }
}