import portunus.core.util.io.IPasswordLibraryLoader;
import portunus.core.util.io.IPasswordLibrarySaver;
import portunus.util.io.PasswordLibraryLoader;
import portunus.util.io.PasswordLibrarySaver;

/**
 * This module provides classes to load an save Portunus libraries to encrypted XML files.
 */
module portunus.util.io {
    requires portunus.model;
    requires transitive portunus.core;
    requires java.xml;

    exports portunus.util.io;

    provides IPasswordLibraryLoader with PasswordLibraryLoader;
    provides IPasswordLibrarySaver with PasswordLibrarySaver;
}
