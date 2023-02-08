package portunus.core.util.finder;

import java.util.List;

import portunus.core.IPasswordLibrary;
import portunus.core.IPasswordRecord;

/**
 * An interace for password record finders in a password library.
 */
public interface IPasswordRecordFinder {
    /**
     * Searches the password library for matching records.
     *
     * @param partialString String for which matching passwords shall be found
     * @param passwordLibrary The passwordLibrary to search
     * @return A list of all mathing password records
     */
    List<IPasswordRecord> findMatchingPasswordRecords(String partialString, IPasswordLibrary passwordLibrary);
}
