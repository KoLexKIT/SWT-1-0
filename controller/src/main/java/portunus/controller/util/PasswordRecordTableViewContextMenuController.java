package portunus.controller.util;

import java.util.List;

import portunus.core.IPasswordGroup;
import portunus.core.IPasswordRecord;
import portunus.view.contextmenu.PasswordRecordTableViewContextMenu;
import portunus.view.control.PasswordRecordTableView;

/**
 * This class connects a passwordRecordTableView to its context menu via its OverviewController.
 */
public class PasswordRecordTableViewContextMenuController {
    private final OverviewController parentController;
    private final PasswordRecordTableViewContextMenu contextMenu;

    /**
     * Constructor for this controller.
     *
     * @param contextMenu The context menu this controller belongs to
     * @param parentController The parent controller of this controller
     */
    public PasswordRecordTableViewContextMenuController(PasswordRecordTableViewContextMenu contextMenu,
                                                        OverviewController parentController) {
        this.contextMenu = contextMenu;
        this.parentController = parentController;
    }

    /**
     * Initializes the controller, its listeners and initializes the displayed view.
     */
    public void control() {
        initializeControls();
        registerListeners();
        initializeContent();
    }

    /**
     * Connects the table view with its controller.
     */
    protected void initializeControls() {
        PasswordRecordTableView passwordRecordTableView = parentController.getView().getPasswordRecordTableView();
        passwordRecordTableView.setContextMenu(contextMenu);
    }

    /**
     * Registers listeners on the view.
     */
    protected void registerListeners() {
        contextMenu.setOnShowing(event -> updateContextMenu());

        contextMenu.getCopyUserMenuItem().setOnAction(event -> parentController.copyUserToClipboard());

        contextMenu.getCopyPasswordMenuItem().setOnAction(event -> parentController.copyPasswordToClipboard());

        contextMenu.getAddPasswordRecordMenuItem().setOnAction(event -> parentController.addPasswordRecord());

        contextMenu.getEditPasswordRecordMenuItem().setOnAction(event -> {
            IPasswordRecord selectedPasswordRecord = parentController.getSelectedPasswordRecord();
            parentController.editPasswordRecord(selectedPasswordRecord);
        });

        contextMenu.getDeletePasswordRecordMenuItem().setOnAction(event -> {
            IPasswordRecord selectedPasswordRecord = parentController.getSelectedPasswordRecord();
            parentController.deletePasswordRecord(selectedPasswordRecord);
        });
    }

    /**
     * Initializes the view for display.
     */
    protected void initializeContent() {
        updateContextMenu();
    }

    /**
     * Updates the state of the context menu.
     */
    protected void updateContextMenu() {
        IPasswordGroup selectedPasswordGroup = parentController.getSelectedPasswordGroup();
        List<IPasswordRecord> selectedPasswordRecords = parentController.getSelectedPasswordRecords();
        IPasswordRecord selectedPasswordRecord = parentController.getSelectedPasswordRecord();

        boolean copyUserDisabled = selectedPasswordRecords.size() != 1 ||
                selectedPasswordRecord.getUser() == null ||
                selectedPasswordRecord.getUser().isEmpty();
        boolean copyPasswordDisabled = selectedPasswordRecords.size() != 1 ||
                selectedPasswordRecord.getPassword() == null ||
                selectedPasswordRecord.getPassword().isEmpty();

        boolean addDisabled = selectedPasswordGroup == null;
        boolean editDisabled = selectedPasswordRecords.size() != 1;
        boolean deleteDisabled = selectedPasswordRecords.size() != 1;

        contextMenu.getCopyUserMenuItem().setDisable(copyUserDisabled);
        contextMenu.getCopyPasswordMenuItem().setDisable(copyPasswordDisabled);

        contextMenu.getAddPasswordRecordMenuItem().setDisable(addDisabled);
        contextMenu.getEditPasswordRecordMenuItem().setDisable(editDisabled);
        contextMenu.getDeletePasswordRecordMenuItem().setDisable(deleteDisabled);
    }
}
