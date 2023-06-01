package pt.ubi.wordle;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryption {
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "ThisIsASecretKey";

    public static String aesAlgorithm(String text, int mode) throws Exception {
        SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        switch (mode) {
            case Cipher.DECRYPT_MODE -> { // Decrypt (2)
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(text));
                return new String(decryptedBytes, StandardCharsets.UTF_8);
            }
            case Cipher.ENCRYPT_MODE -> { // Encrypt (1)
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(encryptedBytes);
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String plaintext = "Hello, World!";
        String encryptedText = aesAlgorithm(plaintext, 1);
        String decryptedText = aesAlgorithm(encryptedText, 2);

        System.out.println("Original text: " + plaintext);
        System.out.println("Encrypted text: " + encryptedText);
        System.out.println("Decrypted text: " + decryptedText);
    }
}
