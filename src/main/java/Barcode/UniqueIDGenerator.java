package Barcode;


import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;


public class UniqueIDGenerator {

    public String generateUUID() {
        UUID uuid = UUID.randomUUID();

        Base64.Encoder encoder = Base64.getUrlEncoder();

        //Put UUID significant bits in byte array.
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return encoder.withoutPadding().encodeToString(bb.array());
    }
}
