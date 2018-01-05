package Core.Barcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class UniqueIDGeneratorTest {
    private UniqueIDGenerator idGenerator;

    @org.junit.Before
    public void setUp() throws Exception {
        idGenerator = new UniqueIDGenerator();
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @Test
    public void generateUUID() throws Exception {
        String UUID = idGenerator.generateUUIDToBase64();

        assertEquals(22,UUID.length());
    }
}