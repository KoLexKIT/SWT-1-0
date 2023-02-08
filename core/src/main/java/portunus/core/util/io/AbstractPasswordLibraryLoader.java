package portunus.core.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import portunus.core.IPasswordLibrary;
import portunus.core.util.crypter.DecryptionFailedException;

/**
 * An abstract class for loading a password library from a file.
 */
public abstract class AbstractPasswordLibraryLoader implements IPasswordLibraryLoader {
    /**
     * Load a library.
     *
     * @param file The file of the encrypted library
     * @param masterPassword The master password for the library
     * @param passwordLibrary The password library to encrypt into
     * @return The password library containing the data
     * @throws FileNotFoundException Thrown when the file does not exist
     * @throws DecryptionFailedException Thrown when decryption fails
     */
    @Override
    public IPasswordLibrary load(File file, String masterPassword, IPasswordLibrary passwordLibrary)
            throws FileNotFoundException, DecryptionFailedException {
        String encryptedXMLContent = loadFromFile(file);
        String decryptedXMLContent = decryptXMLContent(encryptedXMLContent, masterPassword);

        decodeFromXML(decryptedXMLContent, passwordLibrary);

        return passwordLibrary;
    }

    /**
     * Loads an encrypted string from a given file.
     *
     * @param file The file to load
     * @return The encrypted string
     * @throws FileNotFoundException Thrown when the given file does not exist
     */
    public String loadFromFile(File file) throws FileNotFoundException {
        if (file == null) {
            throw new FileNotFoundException();
        }

        if (!file.exists()) {
            throw new FileNotFoundException("File " + file.getPath() + " could not be found.");
        }

        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return new String(fileContent, StandardCharsets.UTF_8);
        } catch(IOException e) {
            e.printStackTrace();
            throw new FileNotFoundException("File " + file.getPath() + " could not be read.");
        }
    }

    /**
     * Decrypt the given xml content using the master password.
     *
     * @param encryptedXMLContent The encrypted xml
     * @param masterPassword The master password
     * @return The String containing the decrypted content
     * @throws DecryptionFailedException Will be thrown if the decryption failed.
     */
    public abstract String decryptXMLContent(String encryptedXMLContent, String masterPassword)
            throws DecryptionFailedException;

    /**
     * Decodes a xml-String into a passwordLibrary.
     *
     * @param xmlContent The xml string to decode
     * @param passwordLibrary The password library to decode into
     */
    public abstract void decodeFromXML(String xmlContent, IPasswordLibrary passwordLibrary);
}
