package Barcode;

import java.util.UUID;

public class UniqueIDGenerator {

    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
