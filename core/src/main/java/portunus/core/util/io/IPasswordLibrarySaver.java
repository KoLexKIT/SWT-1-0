package portunus.core.util.io;

import java.io.File;

import portunus.core.IPasswordLibrary;
import portunus.core.util.crypter.EncryptionFailedException;

/**
 * An interface specifying a password library saver.
 */
public interface IPasswordLibrarySaver {
    /**
     * Saves a given password library encrypted with a given master password.
     *
     * @param passwordLibrary The password library to save
     * @param file The file to save into
     * @param masterPassword The master password to use for encryption
     * @throws EncryptionFailedException Thrown when encryption fails
     */
    void save(IPasswordLibrary passwordLibrary, File file, String masterPassword) throws EncryptionFailedException;
}
