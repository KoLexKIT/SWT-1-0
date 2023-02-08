package portunus.model.observer;

import portunus.core.IEntry;

/**
 * An observer triggering when an entry is added to a password library.
 */
public class PasswordLibraryObserver extends AbstractObserver {

    @Override
    public void update(IEntry newEntry) {
        System.out.println("An entry was added to the PasswordLibrary: " + newEntry.getTitle());
    }

}
