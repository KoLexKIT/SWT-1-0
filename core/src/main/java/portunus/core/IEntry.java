package portunus.core;

/**
 * An interface specifying a password library entry.
 */
public interface IEntry {
    /**
     * Get the title of the entry.
     *
     * @return The title
     */
    String getTitle();

    /**
     * Set the title of the entry.
     *
     * @param title The title
     */
    void setTitle(String title);

    /**
     * Checks if an entry matches a given search request.
     *
     * @param searchRequest The string to match against
     * @return True if this entry matches
     */
    boolean matchesSearch(String searchRequest);
}
