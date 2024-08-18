package util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncrypt {
    public static String encrypt(String data, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        int blockSize = cipher.getBlockSize();
        byte[] dataBytes = data.getBytes("UTF-8");
        int plainTextLength = dataBytes.length;
        if (plainTextLength % blockSize != 0) {
            plainTextLength = plainTextLength + (blockSize - plainTextLength % blockSize);
        }
        byte[] plaintext = new byte[plainTextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        byte[] encrypted = cipher.doFinal(plaintext);
        return Base64.encodeBase64URLSafeString(encrypted);
    }

    // Sample
    public static void main(String[] args) throws Exception {
        String data = "{\n" +
                "  \"action\": 29,\n" +
                "  \"ts\": 1447452951820,\n" +
                "  \"parent\": \"testag\",\n" +
                "  \"starttime\": \"08-12-2016 17:45:00\",\n" +
                "  \"endtime\": \"08-12-2016 17:46:00\",\n" +
                "  \"gTypes\": [0,7]\n" +
                "}";
        AESEncrypt.doMI(data);
    }

    public static void doMI(String data) throws Exception {
        String key = "9b09db42837213df";
        String iv = "b603b3b6edba668d";
        System.out.println(encrypt(data, key, iv));
    }
}
