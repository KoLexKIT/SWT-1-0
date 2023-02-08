package portunus.core.util.crypter;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class provides AES encryption and decryption through the javax.crypto API.
 */
public class AESCrypter implements ICrypter {

    /**
     * Creates a new SecretKeySpec for a given key.
     *
     * @param plainKey The key the SecretKeySpec shall be constructed for
     * @return The contructed SecretKeySpec
     */
    protected SecretKeySpec createSecretKeySpec(String plainKey) {
        try {
            byte[] plainKeyBytes = plainKey.getBytes(StandardCharsets.UTF_8);

            //Hash the plain password
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] shaHashedKeyBytes = sha.digest(plainKeyBytes);

            //Bring hashed bytes to length expected by AES (16 bytes).
            byte[] truncatedKeyBytes = Arrays.copyOf(shaHashedKeyBytes, 16);

            //Create the AES secret key specification.
            return new SecretKeySpec(truncatedKeyBytes, "AES");
        } catch (NoSuchAlgorithmException e) {
            //Should not happen unless this is statically misconfigured.
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creates a new ciper of type AES/ECB/PKCS5PADDING.
     *
     * @return The created cipher
     */
    protected Cipher createCipher() {
        try {
            return Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            //Should not happen unless this is statically misconfigured.
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Encrypts a given plaintext with a given key.
     *
     * @param plainTextToEncrypt The plaintext to encrypt
     * @param plainKey The key the plaintext shall be encrypted with
     * @return The encrypted plaintext
     * @throws EncryptionFailedException When the key, block size or padding is invalid. The latter two should never
     * occur when using AES with strings and PKCS5
     */
    @Override
    public String encrypt(String plainTextToEncrypt, String plainKey) throws EncryptionFailedException {
        try {
            SecretKeySpec secretKeySpec = createSecretKeySpec(plainKey);

            //Create cipher and set it to encryption mode.
            Cipher cipher = createCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] decryptedDecodedBytes = plainTextToEncrypt.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedDecodedBytes = cipher.doFinal(decryptedDecodedBytes);

            return Base64.getEncoder().encodeToString(encryptedDecodedBytes);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new EncryptionFailedException("Encryption failed.", e);
        }
    }

    /**
     * Decrypts a given ciphertext using a given key.
     *
     * @param encryptedTextToDecrypt The cyphertext to decrypt
     * @param plainKey The keys for the text
     * @return The decrypted plaintext
     * @throws DecryptionFailedException When the key, block size or padding is invalid. The latter two should never
     * occur when using AES with strings and PKCS5
     */
    @Override
    public String decrypt(String encryptedTextToDecrypt, String plainKey) throws DecryptionFailedException {
        try {
            SecretKeySpec secretKeySpec = createSecretKeySpec(plainKey);

            //Create cipher and set it to decryption mode.
            Cipher cipher = createCipher();
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] encryptedDecodedBytes = Base64.getDecoder().decode(encryptedTextToDecrypt);
            byte[] decryptedDecodedBytes = cipher.doFinal(encryptedDecodedBytes);

            return new String(decryptedDecodedBytes);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new DecryptionFailedException("Decryption failed.", e);
        }
    }
}
