package util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESDecrypt {
    public static String decrypt(String data, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"),
                new IvParameterSpec(iv.getBytes()));
        String decryptData = new String(cipher.doFinal(Base64.decodeBase64(data)));
        return decryptData;
    }

    // Sample
    public static void main(String[] args) throws Exception {
//        String encryptData = "c6qeNKlMkeObazOE+bYfrJ8w/l1P6H9oXGtcHoAIBFOsTuPJcA7+uQgyzO5o47BvMMvyxY+QTu3Q7217gE0CRgcmZnKjm02JYxIjZ+G13GD+qSBIq7Suo/fmnTH9xvaR3jlMsgM4kVoOISeBEza528amzMJzEhY0T5pM57z0yafhRAjL5t2Tnx9sNebxek3t";

        String x = "e9tkQRED2CBJ_HLjMosUZZa4I6ivGzaPCDDMDzqfd4sWVgMR29KhOYtjJ4BYkLCVBcjwHTGBmbllv4zFWp2o2iUHVkd8XHFp3sQH4wl-HyCjSdE0jubQS_xtACtZ34wGnPBq6BOT-v2-g8i7NguuWwRtpvk7NxHS5hzwLE2a9BZik-Ipxe_2-0NYGPVwvKqPBBOHvjgEmNB0jToZ3KnomdQi3jyWgU5w0BW4iKh4vmCEhyK7WIt0bA";
        doMI(x);
    }

    public static void doMI(String data) throws Exception {
        String key = "9b09db42837213df";
        String iv = "b603b3b6edba668d";
        System.out.println(decrypt(data));
    }

    public static String decrypt(String encrypted) throws Exception {
        byte[] encryptedData = Base64.decodeBase64(encrypted);
        SecretKeySpec key = new SecretKeySpec("e^B%4L3w4a^dire1".getBytes("UTF8"), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(2, key);
        byte[] decrypted = cipher.doFinal(encryptedData);
        return new String(decrypted);
    }
}
