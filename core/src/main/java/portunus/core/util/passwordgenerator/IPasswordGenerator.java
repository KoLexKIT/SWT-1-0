package portunus.core.util.passwordgenerator;

import java.util.Collection;

/**
 * An interface specifying a password generator.
 */
public interface IPasswordGenerator {
    /**
     * Method to generate a password.
     *
     * @param length The length of the password to be generated
     * @param characterSets The character sets to be used
     * @return Thr generated password
     */
    String generatePassword(int length, Collection<CharacterSet> characterSets);
}
