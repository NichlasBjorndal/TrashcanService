package core.barcode;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

/**
 * Generates Base64 encoded UUIDs to be used when generating barcodes. Base64 encoding is used to reduce the length
 * of the Barcode UUID from 36 to 22 chars
 */
public class UniqueIDGenerator {

    /**
     * @return Returns a UUID encoded using Base64 which will be used when generating barcodes.
     */
    String generateUUIDToBase64() {
        // Generates a 128 bit UUID
        UUID uuid = UUID.randomUUID();

        Base64.Encoder encoder = Base64.getUrlEncoder();

        //Put UUID significant bits in byte array.
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        //Removes tailing '==' characters and encodes UUID array to string.
        return encoder.withoutPadding().encodeToString(bb.array());
    }
}
