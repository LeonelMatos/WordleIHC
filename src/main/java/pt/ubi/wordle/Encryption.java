package pt.ubi.wordle;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    private static final String algorithm = "DES";

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(algorithm).generateKey();
    }

    public static byte[] desAlgorithm(byte[] text, int method, SecretKey key) {
        try {
            Cipher desCipher = Cipher.getInstance(algorithm);
            desCipher.init(method, key);
            return desCipher.doFinal(text);
        } catch (Exception ignored) {
            return null;
        }
    }

    /*
    public static void main(String[] args) {
        try {
            // Create secret key
            SecretKey key = generateSecretKey();

            // Creating byte array to store string
            byte[] text = "Encrypted string!".getBytes(StandardCharsets.UTF_8);

            // Encrypting text
            byte[] textEncrypted = desAlgorithm(text, Cipher.ENCRYPT_MODE, key);

            // Decrypting text
            byte[] textDecrypted = desAlgorithm(textEncrypted, Cipher.DECRYPT_MODE, key);

            // Converting encrypted byte array to string
            if (textEncrypted != null)
                System.out.println(new String(textEncrypted));

            // Converting decrypted byte array to string
            if (textDecrypted != null)
                System.out.println(new String(textDecrypted));
        } catch (Exception ignored) {
        }
    }
    */
}
