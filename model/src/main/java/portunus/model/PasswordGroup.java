package portunus.model;

import java.util.List;

import portunus.core.IEntry;
import portunus.core.IPasswordGroup;

/**
 * A password group within a portunus library.
 */
public class PasswordGroup extends Entry implements IPasswordGroup {

    @Override
    public List<IEntry> getEntries() {
        return List.of();
    }

    @Override
    public boolean addEntry(IEntry entry) {
        return false;
    }

    @Override
    public boolean removeEntry(IEntry entry) {
        return false;
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
