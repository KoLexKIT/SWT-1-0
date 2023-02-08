package portunus.core.util.io;

import java.io.File;
import java.io.FileNotFoundException;

import portunus.core.IPasswordLibrary;
import portunus.core.util.crypter.DecryptionFailedException;

/**
 * An interface specifying a password library loader.
 */
public interface IPasswordLibraryLoader {
    /**
     * Loads a password library from a given file using a master password.
     *
     * @param file The file to load from
     * @param masterPassword The master password to use for decryption
     * @param passwordLibrary The password library to load into
     * @return The password library containing the loaded data
     * @throws FileNotFoundException Thrown when the file to be loaded is not found
     * @throws DecryptionFailedException Thrown when decryption fails
     */
    IPasswordLibrary load(File file, String masterPassword, IPasswordLibrary passwordLibrary)
            throws FileNotFoundException, DecryptionFailedException;
}
