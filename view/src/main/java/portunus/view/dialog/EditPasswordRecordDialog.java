package portunus.view.dialog;

import portunus.core.IPasswordRecord;
import portunus.core.IPortunusFactory;
import portunus.view.image.ImageLibrary;
import javafx.scene.image.Image;

/**
 * Dialog to edit a password record.
 */
public class EditPasswordRecordDialog extends AddEditPasswordRecordDialog {
    /**
     * The dialogs constuctor.
     *
     * @param passwordRecord The password record to edit
     * @param factory The factory to use for the edit
     */
    public EditPasswordRecordDialog(IPasswordRecord passwordRecord, IPortunusFactory factory) {
        super(passwordRecord, factory);
    }

    @Override
    protected String createTitle() {
        return "Edit/View Record...";
    }


    @Override
    protected Image createImage() {
        return ImageLibrary.getInstance().getEditPasswordRecordImage();
    }
}
