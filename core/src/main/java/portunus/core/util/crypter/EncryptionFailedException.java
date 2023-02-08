package portunus.core.util.crypter;

/**
 * This class represents an exception to be thrown when encryption fails.
 */
public class EncryptionFailedException extends CrypterException {
    /**
     * The exceptions constructor.
     *
     * @param message The message of this exception
     * @param throwable The exceptions cause
     */
    public EncryptionFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
