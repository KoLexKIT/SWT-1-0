package portunus.core;

/**
 * Interface for a portunus password library. The root is an IEntryContainer, when iterated it returns IEntries.
 */
public interface IPasswordLibrary extends IEntryContainer, Iterable<IEntry> {

}
