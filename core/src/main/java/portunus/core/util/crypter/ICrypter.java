package portunus.core.util.crypter;

/**
 * An interface for different de/encryption methods.
 */
public interface ICrypter {
    /**
     * This method encrypts a given plaintext with a given key.
     *
     * @param plainTextToEncrypt The plaintext to encrypt
     * @param plainKey The key to encrypt with
     * @return The encrypted cyphertext
     * @throws EncryptionFailedException Thrown when encryption fails
     */
    String encrypt(String plainTextToEncrypt, String plainKey) throws EncryptionFailedException;

    /**
     * This method decrypts a given cyphertext with a given key.
     *
     * @param encryptedTextToDecrypt The cyphertext to decypt
     * @param plainKey The key to decrypt with
     * @return The decrypted plaintext
     * @throws DecryptionFailedException Thrown when decryption fails
     */
    String decrypt(String encryptedTextToDecrypt, String plainKey) throws DecryptionFailedException;
}
