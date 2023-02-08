package portunus.core.util.crypter;

/**
 * Parent class for several types of de/encryption exceptions.
 */
public abstract class CrypterException extends Exception {
    /**
     * The constructor of this exception.
     *
     * @param message The exceptions message
     * @param throwable The cause for ths exception
     */
    public CrypterException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
