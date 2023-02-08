package portunus.launcher.base;

import java.io.FileNotFoundException;
import java.util.Optional;

import portunus.core.IPasswordLibrary;
import portunus.core.util.crypter.DecryptionFailedException;
import portunus.view.dialog.EnterMasterPasswordDialog;

/**
 * An abstract class representing a portunus instance with a master password.
 */
public class AbstractPortunusWithMasterPassword extends AbstractPortunus {

    @Override
    protected boolean loadPasswordLibrary() {
        boolean masterPasswordCorrect = false;

        // Ask for the master password until the correct one is entered.
        do {
            EnterMasterPasswordDialog enterMasterPasswordDialog = new EnterMasterPasswordDialog();

            Optional<String> result = enterMasterPasswordDialog.showAndWait();

            if (result.isEmpty()) {
                // Cancel application if no master password was entered.
                return false;
            }

            String enteredMasterPassword = result.get();
            setMasterPassword(enteredMasterPassword);

            try {
                IPasswordLibrary loadedPasswordLibrary = doLoadPasswordLibrary();
                setPasswordLibrary(loadedPasswordLibrary);
                masterPasswordCorrect = true;
            } catch(FileNotFoundException e) {
                // Not a problem. Creating a new file when saving on exit.
            } catch(DecryptionFailedException e) {
                // Occurs when password is wrong. Continue.
            }
        } while(!masterPasswordCorrect);

        return true;
    }
}
