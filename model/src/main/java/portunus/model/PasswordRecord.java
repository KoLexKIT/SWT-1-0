package portunus.model;

import portunus.core.IPasswordRecord;

/**
 * A class representing a password record in Portunus.
 */
public class PasswordRecord extends Entry implements IPasswordRecord {

    /**
     * Default constructor for password records.
     */
    public PasswordRecord() {
        this("");
    }

    /**
     * Constructor for password records setting the title.
     *
     * @param title The title for the constructed record
     */
    public PasswordRecord(String title) {
        this(title, "", "");
    }

    /**
     * Constructor for password records setting the title, user and password.
     *
     * @param title The title for the constructed record
     * @param user The username for the constructed record
     * @param password The password for the constructed record
     */
    public PasswordRecord(String title, String user, String password) {
        setTitle(title);
    }

    @Override
    public String getUser() {
        return "";
    }

    @Override
    public void setUser(String user) {

    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public String getUrl() {
        return "";
    }

    @Override
    public void setUrl(String url) {

    }

    @Override
    public String getNotes() {
        return "";
    }

    @Override
    public void setNotes(String notes) {

    }
}
