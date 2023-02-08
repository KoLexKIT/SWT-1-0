package portunus.model.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import portunus.core.IEntry;
import portunus.core.IEntryContainer;
import portunus.core.IPasswordLibrary;

/**
 * This class implements an iterator for Portunus {@link portunus.core.IEntry} elements.
 */
public class PasswordLibraryIterator implements Iterator<IEntry> {

    private final Queue<IEntry> elementQueue;

    /**
     * Constructor for a new PasswordLibraryIterator on a specific library.
     *
     * @param library The library to iterate over
     */
    public PasswordLibraryIterator(IPasswordLibrary library) {
        elementQueue = new LinkedList<>();
        elementQueue.addAll(library.getEntries());
    }

    @Override
    public boolean hasNext() {
        return !elementQueue.isEmpty();
    }

    @Override
    public IEntry next() {
        IEntry next = elementQueue.poll();
        if (next instanceof IEntryContainer) {
            elementQueue.addAll(((IEntryContainer) next).getEntries());
        }
        return next;
    }

}
