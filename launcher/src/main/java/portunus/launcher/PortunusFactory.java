package portunus.launcher;

import portunus.launcher.base.AbstractPortunusFactory;
import portunus.core.IPasswordGroup;
import portunus.core.IPasswordLibrary;
import portunus.core.IPasswordRecord;
import portunus.model.PasswordGroup;
import portunus.model.PasswordLibrary;
import portunus.model.PasswordRecord;
import portunus.core.util.finder.IPasswordRecordFinder;
import portunus.util.finder.PasswordRecordFinder;
import portunus.core.util.io.IPasswordLibraryLoader;
import portunus.core.util.io.IPasswordLibrarySaver;
import portunus.util.io.PasswordLibraryLoader;
import portunus.util.io.PasswordLibrarySaver;
import portunus.core.util.passwordgenerator.IPasswordGenerator;
import portunus.util.passwordgenerator.PasswordGenerator;

/**
 * A factory that provides instances of implementations of the portunus interfaces.
 */
public class PortunusFactory extends AbstractPortunusFactory {

    @Override
    public IPasswordLibrary createPasswordLibrary() {
        return new PasswordLibrary();
    }

    @Override
    public IPasswordGroup createPasswordGroup() {
        return new PasswordGroup();
    }

    @Override
    public IPasswordRecord createPasswordRecord() {
        return new PasswordRecord();
    }


    @Override
    public IPasswordLibraryLoader createPasswordLibraryLoader() {
        return new PasswordLibraryLoader();
    }

    @Override
    public IPasswordLibrarySaver createPasswordLibrarySaver() {
        return new PasswordLibrarySaver();
    }


    @Override
    public IPasswordGenerator createPasswordGenerator() {
        return new PasswordGenerator();
    }


    @Override
    public IPasswordRecordFinder createPasswordRecordFinder() {
        return new PasswordRecordFinder();
    }

}
