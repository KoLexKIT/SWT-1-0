package portunus.view.dialog;

import portunus.core.IPasswordGroup;
import portunus.view.image.ImageLibrary;
import javafx.scene.image.Image;

/**
 * A dialog for editing password groups.
 */
public class EditPasswordGroupDialog extends AddEditPasswordGroupDialog {
    /**
     * The constructor for the dialog.
     *
     * @param passwordGroup The password group to edit
     */
    public EditPasswordGroupDialog(IPasswordGroup passwordGroup) {
        super(passwordGroup);
    }

    @Override
    protected String createTitle() {
        return "Edit Group...";
    }

    @Override
    protected Image createImage() {
        return ImageLibrary.getInstance().getEditPasswordGroupImage();
    }
}
