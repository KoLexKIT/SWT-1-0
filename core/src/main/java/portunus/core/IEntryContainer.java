package portunus.core;

import java.util.List;

/**
 * Interface for an entry container.
 */
public interface IEntryContainer {
    /**
     * Retuns the entries of the container.
     *
     * @return All entries
     */
    List<IEntry> getEntries();

    /**
     * Adds an entry to the container.
     *
     * @param entry The entry to be added
     * @return Wheter the addition was successful
     */
    boolean addEntry(IEntry entry);

    /**
     * Remove an entry from the container.
     *
     * @param entry The entry to be removed
     * @return Whether the removal was successful
     */
    boolean removeEntry(IEntry entry);
}
