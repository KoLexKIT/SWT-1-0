package portunus.core;

import portunus.core.util.finder.IPasswordRecordFinder;
import portunus.core.util.io.IPasswordLibraryLoader;
import portunus.core.util.io.IPasswordLibrarySaver;
import portunus.core.util.passwordgenerator.IPasswordGenerator;

/**
 * This interfaces defines a PortunusFactory, a factory class to create Portunus model elements.
 */
public interface IPortunusFactory {
    /**
     * Creates a new password library.
     *
     * @return The generated library
     */
    IPasswordLibrary createPasswordLibrary();

    /**
     * Creates a password group.
     *
     * @return The genrated password group
     */
    IPasswordGroup createPasswordGroup();

    /**
     * Creates a new password group with a given title.
     *
     * @param title The groups title
     * @return The generated group
     */
    IPasswordGroup createPasswordGroup(String title);

    /**
     * Creates a new password record.
     *
     * @return The generated password record
     */
    IPasswordRecord createPasswordRecord();

    /**
     * Creates a new password record with a given title.
     *
     * @param title The records title
     * @return The generated record
     */
    IPasswordRecord createPasswordRecord(String title);

    /**
     * Creates a new password library loader.
     *
     * @return The generated loader
     */
    IPasswordLibraryLoader createPasswordLibraryLoader();

    /**
     * Creates a new passowrd library saver.
     *
     * @return The generated saver
     */
    IPasswordLibrarySaver createPasswordLibrarySaver();

    /**
     * Creates a new password generator.
     *
     * @return The generated password generator
     */
    IPasswordGenerator createPasswordGenerator();

    /**
     * Creates a new password record finder.
     *
     * @return The generated record finder
     */
    IPasswordRecordFinder createPasswordRecordFinder();
}
