package portunus.launcher.base;

import java.io.FileNotFoundException;

import portunus.core.IPasswordLibrary;
import portunus.core.util.crypter.DecryptionFailedException;

/**
 * A class abstractly representing a portunus instance without master password.
 */
public class AbstractPortunusWithoutMasterPassword extends AbstractPortunus {

    @Override
    protected boolean loadPasswordLibrary() {
        try {
            setMasterPassword("MyPassword");
            IPasswordLibrary loadedPasswordLibrary = doLoadPasswordLibrary();
            setPasswordLibrary(loadedPasswordLibrary);
        } catch(FileNotFoundException e) {
            // Not a problem. Creating a new file when saving on exit.
        } catch(DecryptionFailedException e) {
            e.printStackTrace();
        }

        return true;
    }
}
