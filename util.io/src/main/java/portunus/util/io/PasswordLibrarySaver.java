package portunus.util.io;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import portunus.core.IEntry;
import portunus.core.IEntryContainer;
import portunus.core.IPasswordGroup;
import portunus.core.IPasswordLibrary;
import portunus.core.IPasswordRecord;
import portunus.core.util.crypter.AESCrypter;
import portunus.core.util.crypter.EncryptionFailedException;
import portunus.core.util.crypter.ICrypter;
import portunus.core.util.io.AbstractPasswordLibrarySaver;

/**
 * Provides methods to save a portunus library to an encrypted XML file.
 */
public class PasswordLibrarySaver extends AbstractPasswordLibrarySaver {

    private Document document;

    @Override
    public String encodeAsXML(IPasswordLibrary passwordLibrary) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();

            appendRootElement(passwordLibrary);
        } catch (ParserConfigurationException e) {
            System.err.println("Internal error when saving.");
        }

        return transformDocumentToXML();
    }

    @Override
    public String encryptXMLContent(String xmlContent, String masterPassword) throws EncryptionFailedException {
        //Encrypt XML content.
        ICrypter crypter = new AESCrypter();
        return crypter.encrypt(xmlContent, masterPassword);
    }

    /**
     * Starts a new document using a root element.
     *
     * @param passwordLibrary The password library to save
     */
    protected void appendRootElement(IPasswordLibrary passwordLibrary) {
        Element passwordLibraryElement = document.createElement("PasswordLibrary");
        document.appendChild(passwordLibraryElement);

        doAppendPasswordEntryContainerEntries(passwordLibrary, passwordLibraryElement);
    }

    /**
     * Appends a password entry to a parent XML element.
     *
     * @param entry The entry to append
     * @param parentElement The parent element
     * @throws UnsupportedOperationException Thrown if we can not match the element type
     */
    protected void appendPasswordEntryElement(IEntry entry, Element parentElement) {
        if (entry instanceof IPasswordGroup group) {
            appendPasswordGroupElement(group, parentElement);
            return;
        }

        if (entry instanceof IPasswordRecord passwordRecord) {
            appendPasswordRecordElement(passwordRecord, parentElement);
            return;
        }

        throw new UnsupportedOperationException();
    }

    /**
     * Appends an element for a password group.
     *
     * @param group The group to construct the element for
     * @param parentElement The parent element
     */
    protected void appendPasswordGroupElement(IPasswordGroup group, Element parentElement) {
        String title = group.getTitle();

        Element element = document.createElement("PasswordGroup");
        element.setAttribute("title", title);
        parentElement.appendChild(element);

        doAppendPasswordEntryContainerEntries(group, element);
    }

    /**
     * Append elements for the children of a password entry container.
     *
     * @param passwordEntryContainer The password entry container to parse
     * @param parentElement The parent element of the containers children
     */
    protected void doAppendPasswordEntryContainerEntries(IEntryContainer passwordEntryContainer,
                                                         Element parentElement) {
        List<IEntry> entries = passwordEntryContainer.getEntries();

        for (IEntry entry : entries) {
            appendPasswordEntryElement(entry, parentElement);
        }
    }

    /**
     * Append an element for a password record to a parent element.
     *
     * @param passwordRecord The record to append
     * @param parentElement The parent element to append to
     */
    protected void appendPasswordRecordElement(IPasswordRecord passwordRecord, Element parentElement) {
        String title = passwordRecord.getTitle();
        String user = passwordRecord.getUser();
        String password = passwordRecord.getPassword();
        String url = passwordRecord.getUrl();
        String notes = passwordRecord.getNotes();

        Element element = document.createElement("PasswordRecord");
        element.setAttribute("title", title);
        element.setAttribute("user", user);
        element.setAttribute("password", password);
        element.setAttribute("url", url);
        element.setAttribute("notes", notes);

        parentElement.appendChild(element);
    }

    /**
     * Transforms a document to XML.
     *
     * @return The XML-string to encrypt and save
     */
    protected String transformDocumentToXML() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            transformer.transform(new DOMSource(document), new StreamResult(outputStream));
            return outputStream.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    }
}
