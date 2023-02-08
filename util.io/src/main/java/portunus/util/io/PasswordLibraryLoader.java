package portunus.util.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;
import portunus.core.IEntryContainer;
import portunus.core.IPasswordGroup;
import portunus.core.IPasswordLibrary;
import portunus.core.IPasswordRecord;
import portunus.model.PasswordGroup;
import portunus.model.PasswordRecord;
import portunus.core.util.crypter.AESCrypter;
import portunus.core.util.crypter.DecryptionFailedException;
import portunus.core.util.crypter.ICrypter;
import portunus.core.util.io.AbstractPasswordLibraryLoader;

/**
 * Provides methods to load a portunus library from an encrypted XML file.
 */
public class PasswordLibraryLoader extends AbstractPasswordLibraryLoader {

    @Override
    public String decryptXMLContent(String encryptedXMLContent, String masterPassword)
            throws DecryptionFailedException {
        // Decrypt XML content.
        ICrypter crypter = new AESCrypter();
        return crypter.decrypt(encryptedXMLContent, masterPassword);
    }

    @Override
    public void decodeFromXML(String xmlContent, IPasswordLibrary passwordLibrary) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new ByteArrayInputStream(xmlContent.getBytes()));
            readPasswordLibraryElement(document, passwordLibrary);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.err.println("Internal error when loading.");
        }
    }

    /**
     * Reads a password library element from a XML document.
     *
     * @param document The document to read from
     * @param passwordLibrary The password library currently being built
     */
    protected void readPasswordLibraryElement(Document document, IPasswordLibrary passwordLibrary) {
        List<Element> childElements = getChildElements(document);
        int numberOfChildElements = childElements.size();

        if (numberOfChildElements != 1) {
            escalate("Encountered an unexpected number of root elements (" + numberOfChildElements + ").");
        }

        Element element = childElements.get(0);
        checkNodeType(element, "PasswordLibrary");

        readPasswordEntryContainerEntries(element, passwordLibrary);
    }

    /**
     * Reads a password entry from a XML documents element.
     *
     * @param element The element to read from
     * @param parentPasswordEntryContainer The parent password entry container
     */
    protected void readPasswordEntryElement(Element element, IEntryContainer parentPasswordEntryContainer) {
        String nodeName = element.getNodeName();

        if (nodeName.equals("PasswordRecord")) {
            readPasswordRecordElement(element, parentPasswordEntryContainer);
            return;
        }

        if (nodeName.equals("PasswordGroup")) {
            readPasswordGroupElement(element, parentPasswordEntryContainer);
            return;
        }

        escalate("Encountered unknown password entry type \"" + nodeName + "\".");
    }

    /**
     * Reads a password record from a XML documents element.
     *
     * @param element The element to read from
     * @param parentPasswordEntryContainer The parent password entry container
     */
    protected void readPasswordRecordElement(Element element, IEntryContainer parentPasswordEntryContainer) {
        checkNodeType(element, "PasswordRecord");

        String title = element.getAttribute("title");
        String user = element.getAttribute("user");
        String password = element.getAttribute("password");
        String url = element.getAttribute("url");
        String notes = element.getAttribute("notes");

        IPasswordRecord passwordRecord = new PasswordRecord();
        passwordRecord.setTitle(title);
        passwordRecord.setUser(user);
        passwordRecord.setPassword(password);
        passwordRecord.setUrl(url);
        passwordRecord.setNotes(notes);
        parentPasswordEntryContainer.addEntry(passwordRecord);
    }

    /**
     * Reads a password group from a XML documents element.
     *
     * @param element The element to read from
     * @param parentPasswordEntryContainer The parent password entry container
     */
    protected void readPasswordGroupElement(Element element, IEntryContainer parentPasswordEntryContainer) {
        checkNodeType(element, "PasswordGroup");

        String title = element.getAttribute("title");

        IPasswordGroup passwordGroup = new PasswordGroup();
        passwordGroup.setTitle(title);
        parentPasswordEntryContainer.addEntry(passwordGroup);


        List<Element> childElements = getChildElements(element);

        for (Element childElement : childElements) {
            readPasswordEntryElement(childElement, passwordGroup);
        }
    }

    /**
     * Reads entries from a password container given in an XML element.
     *
     * @param element The element containg the entries
     * @param parentPasswordEntryContainer The parent password entry container
     */
    protected void readPasswordEntryContainerEntries(Element element, IEntryContainer parentPasswordEntryContainer) {
        List<Element> childElements = getChildElements(element);

        for (Element childElement : childElements) {
            readPasswordEntryElement(childElement, parentPasswordEntryContainer);
        }
    }

    private void escalate(String message) {
        throw new RuntimeException(message);
    }

    private static List<Element> getChildElements(Node node) {
        NodeList childNodes = node.getChildNodes();
        List<Element> childElements = new ArrayList<>();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            if (childNode instanceof Element childElement) {
                childElements.add(childElement);
            }
        }

        return childElements;
    }

    private void checkNodeType(Node node, String intendedName) {
        String nodeName = node.getNodeName();

        if (!nodeName.equals(intendedName)) {
            escalate("Encountered unexpected node type \"" + nodeName + "\" (expected: \"" + intendedName + "\").");
        }
    }
}
