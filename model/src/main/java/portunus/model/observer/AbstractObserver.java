package portunus.model.observer;

import portunus.core.IEntry;

/**
 * An abstract class for all observers to implement.
 */
public abstract class AbstractObserver {
    /**
     * Update method. Is called when the observed classes state changes.
     *
     * @param newEntry The changed entry
     */
    public abstract void update(IEntry newEntry);

}
