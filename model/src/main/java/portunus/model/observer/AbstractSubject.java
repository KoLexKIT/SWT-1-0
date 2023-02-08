package portunus.model.observer;

import java.util.ArrayList;
import java.util.List;

import portunus.core.IEntry;

/**
 * Abstract class to specify the methods a subject being observed by an {@link portunus.model.observer.AbstractObserver}
 * should implement.
 */
public abstract class AbstractSubject {

    private final List<AbstractObserver> observerList = new ArrayList<>();

    /**
     * Add an observer to own observer list.
     *
     * @param observer The observer to add
     */
    public void addObserver(AbstractObserver observer) {
        observerList.add(observer);
    }

    /**
     * Delete an observer from own observer list.
     *
     * @param observer The observer to delete
     */
    public void deleteObserver(AbstractObserver observer) {
        observerList.remove(observer);
    }

    /**
     * Notify all observer regarding a change in a specific entry.
     *
     * @param newEntry The changed entry
     */
    public void notifyObserver(IEntry newEntry) {
        for (AbstractObserver observer : observerList) {
            observer.update(newEntry);
        }
    }

}
