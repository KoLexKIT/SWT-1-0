package portunus.view.control;

import portunus.core.IEntryContainer;
import javafx.scene.control.TreeView;

/**
 * A password entry container tree view.
 */
public class PasswordEntryContainerTreeView extends TreeView<IEntryContainer> {
    /**
     * Initialize the class and its control.
     */
    public PasswordEntryContainerTreeView() {
        initializeControl();
    }

    private void initializeControl() {
        setShowRoot(false);
    }
}
