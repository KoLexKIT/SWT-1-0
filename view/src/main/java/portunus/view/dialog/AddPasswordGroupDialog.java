package portunus.view.dialog;

import portunus.core.IPasswordGroup;
import portunus.view.image.ImageLibrary;
import javafx.scene.image.Image;

/**
 * Dialog for adding a new password group to Portunus.
 */
public class AddPasswordGroupDialog extends AddEditPasswordGroupDialog {
    /**
     * Constructor for the dialog.
     *
     * @param passwordGroup The password group to add
     */
    public AddPasswordGroupDialog(IPasswordGroup passwordGroup) {
        super(passwordGroup);
    }

    @Override
    protected String createTitle() {
        return "Add Group...";
    }

    @Override
    protected Image createImage() {
        return ImageLibrary.getInstance().getAddPasswordGroupImage();
    }
}
