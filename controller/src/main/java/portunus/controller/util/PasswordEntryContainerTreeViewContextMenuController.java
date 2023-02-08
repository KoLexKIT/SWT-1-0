package portunus.controller.util;

import portunus.core.IPasswordGroup;
import portunus.view.contextmenu.PasswordEntryContainerTreeViewContextMenu;
import portunus.view.control.PasswordEntryContainerTreeView;

/**
 * This class connects the PasswordEntryContainerTreeView to the underlying model through an OverviewController.
 */
public class PasswordEntryContainerTreeViewContextMenuController {
    private final OverviewController parentController;
    private final PasswordEntryContainerTreeViewContextMenu contextMenu;

    /**
     * Constructor for a PasswordEntryContainerTreeViewContextMenuController.
     *
     * @param contextMenu The context menu this controller relates to
     * @param parentController The parent controller of this class
     */
    public PasswordEntryContainerTreeViewContextMenuController(PasswordEntryContainerTreeViewContextMenu contextMenu,
                                                               OverviewController parentController) {
        this.contextMenu = contextMenu;
        this.parentController = parentController;
    }

    /**
     * Initialize the controller, its listeners and the contents of the view.
     */
    public void control() {
        initializeControls();
        registerListeners();
        initializeContent();
    }

    /**
     * Connects the tree view with its context menu.
     */
    protected void initializeControls() {
        PasswordEntryContainerTreeView passwordEntryContainerTreeView =
                parentController.getView().getPasswordEntryContainerTreeView();
        passwordEntryContainerTreeView.setContextMenu(contextMenu);
    }

    /**
     * Registers listeners for the PasswordEntryContainerTreeView.
     */
    protected void registerListeners() {
        contextMenu.setOnShowing(event -> updateContextMenu());

        contextMenu.getAddPasswordGroupMenuItem().setOnAction(event -> {
            IPasswordGroup selectedPasswordGroup = parentController.getSelectedPasswordGroup();
            parentController.addPasswordGroup(selectedPasswordGroup);
        });

        contextMenu.getEditPasswordGroupMenuItem().setOnAction(event -> {
            IPasswordGroup selectedPasswordGroup = parentController.getSelectedPasswordGroup();
            parentController.editPasswordGroup(selectedPasswordGroup);
        });

        contextMenu.getDeletePasswordGroupMenuItem().setOnAction(event -> {
            IPasswordGroup selectedPasswordGroup = parentController.getSelectedPasswordGroup();
            parentController.deletePasswordGroup(selectedPasswordGroup);
        });
    }

    /**
     * Initializes the displayed content of the context menu.
     */
    protected void initializeContent() {
        updateContextMenu();
    }

    /**
     * Updates the state of the context menu.
     */
    protected void updateContextMenu() {
        IPasswordGroup selectedPasswordGroup = parentController.getSelectedPasswordGroup();

        boolean addDisabled = false; // Can always add groups.
        boolean editDisabled = selectedPasswordGroup == null;
        boolean deleteDisabled = selectedPasswordGroup == null;

        contextMenu.getAddPasswordGroupMenuItem().setDisable(addDisabled);
        contextMenu.getEditPasswordGroupMenuItem().setDisable(editDisabled);
        contextMenu.getDeletePasswordGroupMenuItem().setDisable(deleteDisabled);
    }
}
