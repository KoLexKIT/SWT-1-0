package portunus.model;

import java.util.Iterator;
import java.util.List;

import portunus.core.IEntry;
import portunus.core.IPasswordLibrary;
import portunus.model.iterator.PasswordLibraryIterator;
import portunus.model.observer.AbstractSubject;
import portunus.model.observer.PasswordLibraryObserver;

/**
 * A password library in Portunus. There is always only one password library per program instance.
 */
public class PasswordLibrary extends AbstractSubject implements IPasswordLibrary
{

    /**
     * Create a new, empty password library with a new observer.
     */
    public PasswordLibrary() {
        addObserver(new PasswordLibraryObserver());
    }

    @Override
    public List<IEntry> getEntries() {
        return List.of();
    }

    @Override
    public boolean addEntry(IEntry entry) {
        notifyObserver(entry);
        return false;
    }

    @Override
    public boolean removeEntry(IEntry entry) {
        return false;
    }

    @Override
    public Iterator<IEntry> iterator() {
        return new PasswordLibraryIterator(this);
    }

}
