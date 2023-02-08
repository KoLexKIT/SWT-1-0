package portunus.view.control;

import portunus.core.IEntryContainer;
import portunus.view.image.ImageLibrary;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

/**
 * A Item in the {@link portunus.view.control.PasswordEntryContainerTreeView}.
 */
public class PasswordEntryContainerTreeItem extends TreeItem<IEntryContainer> {
    /**
     * Constructor for an item in a {@link portunus.view.control.PasswordEntryContainerTreeView}.
     *
     * @param passwordEntryContainer The container to construct the item for
     */
    public PasswordEntryContainerTreeItem(IEntryContainer passwordEntryContainer) {
        super(passwordEntryContainer, new ImageView(ImageLibrary.getInstance().getPasswordGroupImage()));
    }
}
