package portunus.launcher.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import portunus.launcher.PortunusFactory;
import portunus.controller.util.OverviewController;
import portunus.core.IPasswordLibrary;
import portunus.core.IPortunusFactory;
import portunus.core.util.crypter.DecryptionFailedException;
import portunus.core.util.crypter.EncryptionFailedException;
import portunus.core.util.io.IPasswordLibraryLoader;
import portunus.core.util.io.IPasswordLibrarySaver;
import portunus.view.OverviewPane;
import portunus.view.image.ImageLibrary;
import portunus.view.util.ErrorUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * An abstract class for starting portunus and loading the database.
 */
public abstract class AbstractPortunus extends Application {
    private static final File PASSWORD_LIBRARY_FILE = new File("PasswordLibrary.portunus");

    private String masterPassword;
    private IPasswordLibrary passwordLibrary;
    private final IPortunusFactory factory;

    /**
     * Constructor to instantiate class.
     */
    public AbstractPortunus() {
        factory = new PortunusFactory();
    }

    /**
     * Starts portunus and the view.
     *
     * @param stage The javafx stage to use.
     */
    @Override
    public void start(Stage stage) {
        //Add application icons
        List<Image> icons = stage.getIcons();
        icons.addAll(ImageLibrary.getInstance().getApplicationImages());

        boolean passwordLibraryLoaded = loadPasswordLibrary();

        if (!passwordLibraryLoaded) {
            return;
        }

        OverviewPane overviewPane = new OverviewPane();
        OverviewController overviewController = new OverviewController(passwordLibrary, overviewPane, factory);

        overviewController.control();

        Scene scene = new Scene(overviewPane, 900, 600);

        stage.setTitle("Portunus");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> savePasswordLibrary());
    }

    /**
     * A method to load the password library.
     *
     * @return True if a correct master password is entered while loading the library
     */
    protected abstract boolean loadPasswordLibrary();

    /**
     * Loads a password library or creates a new one when the file does not exist.
     *
     * @return The loaded password library
     * @throws FileNotFoundException Thrown when the password library is not found on load. This should never be thrown
     * because we create a new library in that case
     * @throws DecryptionFailedException Thrown when decryption files
     */
    protected IPasswordLibrary doLoadPasswordLibrary() throws FileNotFoundException, DecryptionFailedException {
        IPasswordLibrary passwordLibrary = factory.createPasswordLibrary();

        if (passwordLibrary == null) {
            ErrorUtil.showFactoryMethodError(IPasswordLibrary.class, "Application will crash!");
            return null;
        }

        if (!PASSWORD_LIBRARY_FILE.exists()) {
            return passwordLibrary;
        }

        // ExamplePasswordLibraryCreator examplePasswordLibraryCreator = new ExamplePasswordLibraryCreator();
        // return examplePasswordLibraryCreator.createExampleLibrary(factory);

        IPasswordLibraryLoader loader = factory.createPasswordLibraryLoader();

        if (loader == null) {
            ErrorUtil.showFactoryMethodError(IPasswordLibraryLoader.class, "Password library will be empty.");
            return passwordLibrary;
        }

        return loader.load(PASSWORD_LIBRARY_FILE, masterPassword, passwordLibrary);
    }

    private void savePasswordLibrary() {
        IPasswordLibrarySaver saver = factory.createPasswordLibrarySaver();

        if (saver == null) {
            ErrorUtil.showFactoryMethodError(IPasswordLibrarySaver.class, "Password library will not be saved.");
            return;
        }

        try {
            saver.save(passwordLibrary, PASSWORD_LIBRARY_FILE, masterPassword);
        } catch (EncryptionFailedException e) {
            //Not going to happen in any realistic case.
            ErrorUtil.showError("Encryption of password library failed on save.");
        }
    }

    protected String getMasterPassword() {
        return masterPassword;
    }

    protected void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    protected IPasswordLibrary getPasswordLibrary() {
        return passwordLibrary;
    }

    protected void setPasswordLibrary(IPasswordLibrary passwordLibrary) {
        this.passwordLibrary = passwordLibrary;
    }
}
