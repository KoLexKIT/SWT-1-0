package portunus.view.dialog;

import portunus.core.IPasswordRecord;
import portunus.core.IPortunusFactory;
import portunus.view.image.ImageLibrary;
import javafx.scene.image.Image;

/**
 * A dialog to add a password record.
 */
public class AddPasswordRecordDialog extends AddEditPasswordRecordDialog {
    /**
     * The constructor for the dialog.
     *
     * @param passwordRecord The password record to add
     * @param factory The factory to use for this record
     */
    public AddPasswordRecordDialog(IPasswordRecord passwordRecord, IPortunusFactory factory) {
        super(passwordRecord, factory);
    }

    @Override
    protected String createTitle() {
        return "Add Record...";
    }


    @Override
    protected Image createImage() {
        return ImageLibrary.getInstance().getAddPasswordRecordImage();
    }
}
