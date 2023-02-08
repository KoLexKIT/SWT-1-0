import portunus.core.util.passwordgenerator.IPasswordGenerator;
import portunus.util.passwordgenerator.PasswordGenerator;

/**
 * This module provides a basic password generator for Portunus.
 */
module portunus.util.passwordgenerator {
    requires transitive portunus.core;

    exports portunus.util.passwordgenerator;

    provides IPasswordGenerator with PasswordGenerator;
}
