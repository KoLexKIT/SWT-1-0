import portunus.core.IEntry;
import portunus.core.IPasswordGroup;
import portunus.core.IPasswordLibrary;
import portunus.core.IPasswordRecord;
import portunus.model.Entry;
import portunus.model.PasswordGroup;
import portunus.model.PasswordLibrary;
import portunus.model.PasswordRecord;

/**
 * This module provides the Portunus model and some abstractions to ease access and observation of the model.
 */
module portunus.model {
    requires transitive portunus.core;
    exports portunus.model;

    provides IEntry with Entry;
    provides IPasswordGroup with PasswordGroup;
    provides IPasswordLibrary with PasswordLibrary;
    provides IPasswordRecord with PasswordRecord;
}
