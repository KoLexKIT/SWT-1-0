import portunus.core.util.finder.IPasswordRecordFinder;
import portunus.util.finder.PasswordRecordFinder;

/**
 * This module provides a password record finder for a portunus library.
 */
module portunus.util.finder {
    requires transitive portunus.core;

    exports portunus.util.finder;

    provides IPasswordRecordFinder with PasswordRecordFinder;
}
