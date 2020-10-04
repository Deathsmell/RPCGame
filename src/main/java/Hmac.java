import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class Hmac {
    public static String generateKey(SecureRandom random){
        byte[] key = new byte[32];
        random.nextBytes(key);
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(key);
    }

    public static String generateHmac(String choice,String key){
        Mac initializedMac = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_MD5, key.getBytes());
        byte[] bytes = initializedMac.doFinal(choice.getBytes());
        return bytesToHex(bytes);
    }

    private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);
    private static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}
