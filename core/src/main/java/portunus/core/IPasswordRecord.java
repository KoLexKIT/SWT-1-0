package portunus.core;

/**
 * Interface for a password record.
 */
public interface IPasswordRecord extends IEntry {
    /**
     * Gets the records user.
     *
     * @return The user
     */
    String getUser();

    /**
     * Sets the records user.
     *
     * @param user The user
     */
    void setUser(String user);

    /**
     * Gets the records password.
     *
     * @return The password
     */
    String getPassword();

    /**
     * Sets the records password.
     *
     * @param password The password
     */
    void setPassword(String password);

    /**
     * Gets the records url.
     *
     * @return The url
     */
    String getUrl();

    /**
     * Sets the records url.
     *
     * @param url The url
     */
    void setUrl(String url);

    /**
     * Gets the records notes.
     *
     * @return The records notes
     */
    String getNotes();

    /**
     * Sets the records notes.
     *
     * @param notes The records notes
     */
    void setNotes(String notes);
}
