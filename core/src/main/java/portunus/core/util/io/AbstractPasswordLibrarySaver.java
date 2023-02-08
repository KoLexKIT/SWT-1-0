package portunus.core.util.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import portunus.core.IPasswordLibrary;
import portunus.core.util.crypter.EncryptionFailedException;

/**
 * An abstract class for saving a password library to a file.
 */
public abstract class AbstractPasswordLibrarySaver implements IPasswordLibrarySaver {
    /**
     * Saves the given library.
     *
     * @param passwordLibrary The library to save
     * @param file The file to save into
     * @param masterPassword The masterPassword to encrypt with
     * @throws EncryptionFailedException Thrown when encryption fails
     */
    @Override
    public void save(IPasswordLibrary passwordLibrary, File file, String masterPassword)
            throws EncryptionFailedException {
        String xmlContent = encodeAsXML(passwordLibrary);
        String encryptedXMLContent = encryptXMLContent(xmlContent, masterPassword);
        saveToFile(encryptedXMLContent, file);
    }

    /**
     * Encode a given library to xml.
     *
     * @param passwordLibrary The library to encode
     * @return The resulting xml string
     */
    public abstract String encodeAsXML(IPasswordLibrary passwordLibrary);

    /**
     * Encrypt a given xml string with a master password.
     *
     * @param xmlContent The xml string to encode
     * @param masterPassword The master password to use for encryption
     * @return The encrypted string
     * @throws EncryptionFailedException Thrown when encryption fails
     */
    public abstract String encryptXMLContent(String xmlContent, String masterPassword) throws EncryptionFailedException;

    /**
     * Save a content string to a file.
     *
     * @param content The content string to save
     * @param file The file to save into
     */
    public void saveToFile(String content, File file) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(content);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
