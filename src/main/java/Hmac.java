import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class Hmac {

    private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);

    public static String generateKey(SecureRandom random) {
        byte[] key = new byte[32];
        random.nextBytes(key);
        return bytesToHex(key);
    }

    public static String generateHmac(String choice, String key) {
        Mac initializedMac = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, key.getBytes());
        byte[] bytes = initializedMac.doFinal(choice.getBytes());
        return bytesToHex(bytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }
}
