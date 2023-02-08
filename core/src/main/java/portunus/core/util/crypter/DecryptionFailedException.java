package portunus.core.util.crypter;

/**
 * This class represents an exception to be thrown when decryption fails.
 */
public class DecryptionFailedException extends CrypterException {
    /**
     * The constructor of this exception.
     *
     * @param message The message for this exception
     * @param throwable The exceptions cause
     */
    public DecryptionFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
