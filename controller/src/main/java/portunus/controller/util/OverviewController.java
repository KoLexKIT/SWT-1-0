package portunus.controller.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import portunus.core.IEntry;
import portunus.core.IEntryContainer;
import portunus.core.IPasswordGroup;
import portunus.core.IPasswordLibrary;
import portunus.core.IPasswordRecord;
import portunus.core.IPortunusFactory;
import portunus.core.util.finder.IPasswordRecordFinder;
import portunus.view.OverviewPane;
import portunus.view.contextmenu.PasswordEntryContainerTreeViewContextMenu;
import portunus.view.contextmenu.PasswordRecordTableViewContextMenu;
import portunus.view.control.PasswordEntryContainerTreeItem;
import portunus.view.control.PasswordEntryContainerTreeView;
import portunus.view.control.PasswordRecordTableView;
import portunus.view.dialog.AddPasswordGroupDialog;
import portunus.view.dialog.AddPasswordRecordDialog;
import portunus.view.dialog.EditPasswordGroupDialog;
import portunus.view.dialog.EditPasswordRecordDialog;
import portunus.view.util.ErrorUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * This class connects the view with the underlying model. It provides several methods for the view to call.
 */
public class OverviewController {
    private final IPasswordLibrary passwordLibrary;
    private final OverviewPane overviewPane;
    private final IPortunusFactory factory;

    /**
     * Constructor for an OverviewController.
     *
     * @param passwordLibrary The password library this controller relates to
     * @param overviewPane The view this controller relates to
     * @param factory A PortunusFactory providing factory methods for creating and deleting
     *                passwords and password groups
     */
    public OverviewController(IPasswordLibrary passwordLibrary, OverviewPane overviewPane, IPortunusFactory factory) {
        this.passwordLibrary = passwordLibrary;
        this.overviewPane = overviewPane;
        this.factory = factory;
    }

    /**
     * Method setting initializing the controller and its listeners. It also initializes the view.
     */
    public void control() {
        registerListeners();
        initializeContent();
        initializeChildControllers();
    }

    /**
     * Registers listeners within the view of Portunus.
     */
    protected void registerListeners() {
        PasswordEntryContainerTreeView passwordEntryContainerTreeView =
                overviewPane.getPasswordEntryContainerTreeView();

        passwordEntryContainerTreeView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    IPasswordGroup selectedPasswordGroup = getSelectedPasswordGroup();
                    updatePasswordRecordTableView(selectedPasswordGroup);
                });

        overviewPane.getSearchButton().setOnAction(e -> searchPasswordEntries());


        TableView<IPasswordRecord> passwordRecordTableView = overviewPane.getPasswordRecordTableView();

        passwordRecordTableView.setRowFactory(tv -> {
            TableRow<IPasswordRecord> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                //Detect double clicks
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    IPasswordRecord passwordRecord = row.getItem();
                    editPasswordRecord(passwordRecord);
                }
            });

            return row;
        });
    }

    /**
     * Sets up the view for display.
     */
    protected void initializeContent() {
        updatePasswordEntryContainerTreeView();

        //Set selection to first (visible) password group.
        List<IEntry> passwordEntries = passwordLibrary.getEntries();

        if (!passwordEntries.isEmpty()) {
            IEntry firstPasswordEntry = passwordEntries.get(0);

            if (firstPasswordEntry instanceof IPasswordGroup firstPasswordGroup) {
                selectPasswordGroup(firstPasswordGroup);
            }
        }
    }

    /**
     * Sets up the child controllers for the view Elements within the overwiew pane.
     */
    protected void initializeChildControllers() {
        PasswordRecordTableViewContextMenu passwordRecordContextMenu = new PasswordRecordTableViewContextMenu();
        PasswordRecordTableViewContextMenuController passwordRecordTableViewContextMenuController = new PasswordRecordTableViewContextMenuController(passwordRecordContextMenu, this);
        passwordRecordTableViewContextMenuController.control();

        PasswordEntryContainerTreeViewContextMenu passwordEntryContainerTreeViewContextMenu =
                new PasswordEntryContainerTreeViewContextMenu();
        PasswordEntryContainerTreeViewContextMenuController passwordEntryContainerTreeViewContextMenuController =
                new PasswordEntryContainerTreeViewContextMenuController(passwordEntryContainerTreeViewContextMenu,
                        this);
        passwordEntryContainerTreeViewContextMenuController.control();
    }

    /**
     * This method is called when a password search shall be executed.
     */
    public void searchPasswordEntries() {
        TextField searchTextField = overviewPane.getSearchTextField();
        String partialString = searchTextField.getText();
        doSearchPasswordEntries(partialString);
    }

    /**
     * Copies the user of the currently selected record to clipboard.
     */
    public void copyUserToClipboard() {
        IPasswordRecord selectedPasswordRecord = getSelectedPasswordRecord();

        if (selectedPasswordRecord == null) {
            return;
        }

        String user = selectedPasswordRecord.getUser();
        copyToClipboard(user);
    }

    /**
     * Copies the password of the currently selected record to clipboard.
     */
    public void copyPasswordToClipboard() {
        IPasswordRecord selectedPasswordRecord = getSelectedPasswordRecord();

        if (selectedPasswordRecord == null) {
            return;
        }

        String password = selectedPasswordRecord.getPassword();
        copyToClipboard(password);
    }

    private void copyToClipboard(String text) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    /**
     * Adds a password group to the password library.
     *
     * @param parentContainer The parent of the password group to be added
     */
    public void addPasswordGroup(IEntryContainer parentContainer) {
        IPasswordGroup passwordGroup = factory.createPasswordGroup("New Group");

        if (passwordGroup == null) {
            ErrorUtil.showFactoryMethodError(IPasswordGroup.class);
            return;
        }

        AddPasswordGroupDialog dialog = new AddPasswordGroupDialog(passwordGroup);
        Optional<IPasswordGroup> result = dialog.showAndWait();

        if (result.isPresent()) {
            IPasswordGroup editedPasswordGroup = result.get();
            IPasswordGroup selectedPasswordGroup = getSelectedPasswordGroup();
            doAddPasswordGroup(editedPasswordGroup, selectedPasswordGroup);
        }
    }

    /**
     * Edits a password group. Spawns a new Edit-Dialog and processes the results.
     *
     * @param passwordGroup The password group to be edited.
     */
    public void editPasswordGroup(IPasswordGroup passwordGroup) {
        if (passwordGroup == null) {
            return;
        }

        EditPasswordGroupDialog dialog = new EditPasswordGroupDialog(passwordGroup);
        Optional<IPasswordGroup> result = dialog.showAndWait();

        if (result.isPresent()) {
            IPasswordGroup editedPasswordGroup = result.get();
            updatePasswordGroup(editedPasswordGroup);
        }
    }

    /**
     * Deletes a password group after asking for confirmation.
     *
     * @param passwordGroup The password group to be deleted.
     */
    public void deletePasswordGroup(IPasswordGroup passwordGroup) {
        boolean reallyDelete = showDeleteConfirmationDialog("group", passwordGroup.getTitle());

        if (reallyDelete) {
            doDeletePasswordGroup(passwordGroup);
        }
    }


    /**
     * Adds a new password record by getting a new record from PortunusFactory and
     * editing it by spawning a AddPasswordRecordDialog and processing its results.
     */
    public void addPasswordRecord() {
        IPasswordRecord passwordRecord = factory.createPasswordRecord("New Record");

        if (passwordRecord == null) {
            ErrorUtil.showFactoryMethodError(IPasswordRecord.class);
            return;
        }

        AddPasswordRecordDialog dialog = new AddPasswordRecordDialog(passwordRecord, factory);
        Optional<IPasswordRecord> result = dialog.showAndWait();

        if (result.isPresent()) {
            IPasswordRecord editedPasswordRecord = result.get();
            doAddPasswordRecord(editedPasswordRecord);
        }
    }

    /**
     * Edit a given password record. Spawns a dialog and processes the results.
     *
     * @param passwordRecord The record to be edited
     */
    public void editPasswordRecord(IPasswordRecord passwordRecord) {
        if (passwordRecord == null) {
            return;
        }

        EditPasswordRecordDialog dialog = new EditPasswordRecordDialog(passwordRecord, factory);
        Optional<IPasswordRecord> result = dialog.showAndWait();

        if (result.isPresent()) {
            IPasswordRecord editedPasswordRecord = result.get();
            doAddPasswordRecord(editedPasswordRecord);
        }
    }

    /**
     * Deletes a given password record. Asks for confirmation before deletion.
     *
     * @param passwordRecord the password record to be deleted
     */
    public void deletePasswordRecord(IPasswordRecord passwordRecord) {
        boolean reallyDelete = showDeleteConfirmationDialog("record", passwordRecord.getTitle());

        if (reallyDelete) {
            doDeletePasswordRecord(passwordRecord);
        }
    }


    /**
     * Shows a dialog conferming a pending deletion.
     *
     * @param elementTypeName The type name of the element to be deleted
     * @param elementName The name of the element to be deleted
     * @return True if the deletion was confirmed
     */
    protected boolean showDeleteConfirmationDialog(String elementTypeName, String elementName) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete " + elementTypeName + "?");
        alert.setHeaderText(null);
        alert.setContentText("Do you really want to delete the " + elementTypeName + " \"" + elementName + "\"?");

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElseThrow() == ButtonType.OK;
    }

    /**
     * Executes adding a given password group to a given parent container.
     *
     * @param passwordGroup The password group to be added
     * @param parentContainer The container the group shall be added to
     */
    protected void doAddPasswordGroup(IPasswordGroup passwordGroup, IEntryContainer parentContainer) {
        IEntryContainer container = parentContainer == null ? passwordLibrary : parentContainer;

        container.addEntry(passwordGroup);
        updatePasswordEntryContainerTreeView();
        selectPasswordGroup(passwordGroup);
    }

    /**
     * Executes deleting a given password group to a given parent container.
     *
     * @param passwordGroup The password group to be deleted
     */
    protected void doDeletePasswordGroup(IPasswordGroup passwordGroup) {
        IEntryContainer container = findPasswordEntryContainer(passwordGroup);

        if (container != null) {
            container.removeEntry(passwordGroup);
            updatePasswordEntryContainerTreeView();
        }
    }

    private IEntryContainer findPasswordEntryContainer(IEntry searchedEntry) {
        return findPasswordEntryContainer(searchedEntry, passwordLibrary);
    }

    private IEntryContainer findPasswordEntryContainer(IEntry searchedEntry, IEntryContainer currentContainer) {
        List<IEntry> childEntries = currentContainer.getEntries();

        if (childEntries.contains(searchedEntry)) {
            return currentContainer;
        }

        for (IEntry childEntry : childEntries) {
            if (childEntry instanceof IEntryContainer childContainer) {
                IEntryContainer foundContainer = findPasswordEntryContainer(searchedEntry, childContainer);

                if (foundContainer != null) {
                    return foundContainer;
                }
            }
        }

        return null;
    }

    private TreeItem<IEntryContainer> findPasswordEntryContainerTreeItem(
            IEntryContainer searchedPasswordEntryContainer) {
        PasswordEntryContainerTreeView passwordEntryContainerTreeView =
                overviewPane.getPasswordEntryContainerTreeView();

        TreeItem<IEntryContainer> rootTreeItem = passwordEntryContainerTreeView.getRoot();

        return findPasswordEntryContainerTreeItem(searchedPasswordEntryContainer, rootTreeItem);
    }

    private TreeItem<IEntryContainer> findPasswordEntryContainerTreeItem(IEntryContainer searchedPasswordEntryContainer,
            TreeItem<IEntryContainer> currentTreeItem) {
        IEntryContainer currentPasswordEntryContainer = currentTreeItem.getValue();

        if (currentPasswordEntryContainer == searchedPasswordEntryContainer) {
            return currentTreeItem;
        }

        List<TreeItem<IEntryContainer>> childTreeItems = currentTreeItem.getChildren();

        for (TreeItem<IEntryContainer> childTreeItem : childTreeItems) {
            TreeItem<IEntryContainer> foundTreeItem =
                    findPasswordEntryContainerTreeItem(searchedPasswordEntryContainer, childTreeItem);

            if (foundTreeItem != null) {
                return foundTreeItem;
            }
        }

        return null;
    }

    private void doSearchPasswordEntries(String partialString) {
        IPasswordRecordFinder finder = factory.createPasswordRecordFinder();

        if (finder == null) {
            ErrorUtil.showFactoryMethodError(IPasswordRecordFinder.class);
            return;
        }

        //Deselect password group
        overviewPane.getPasswordEntryContainerTreeView().getSelectionModel().clearSelection();

        //Find matching password records
        List<IPasswordRecord> matchingPasswordRecords =
                finder.findMatchingPasswordRecords(partialString, passwordLibrary);

        //Fill table with found entries
        updatePasswordRecordTableView(matchingPasswordRecords);
    }

    /**
     * Executes adding a given password record to the currently selected password group.
     *
     * @param passwordRecord The password record to be added
     */
    protected void doAddPasswordRecord(IPasswordRecord passwordRecord) {
        IPasswordGroup selectedPasswordGroup = getSelectedPasswordGroup();

        if (selectedPasswordGroup == null) {
            return;
        }

        selectedPasswordGroup.addEntry(passwordRecord);
        updatePasswordGroup(selectedPasswordGroup);
        selectPasswordRecord(passwordRecord);
    }

    /**
     * Executes deleting a given password record.
     *
     * @param passwordRecord The password record to be deleted
     */
    protected void doDeletePasswordRecord(IPasswordRecord passwordRecord) {
        IPasswordGroup selectedPasswordGroup = getSelectedPasswordGroup();

        if (selectedPasswordGroup == null) {
            return;
        }

        selectedPasswordGroup.removeEntry(passwordRecord);
        updatePasswordGroup(selectedPasswordGroup);
    }

    /**
     * Updates a given password group.
     *
     * @param passwordGroup The password group to be updated
     */
    protected void updatePasswordGroup(IPasswordGroup passwordGroup) {
        if (passwordGroup == null) {
            return;
        }

        IPasswordGroup selectedPasswordGroup = getSelectedPasswordGroup();

        updatePasswordEntryContainerTreeView();

        //Restore selection
        selectPasswordGroup(selectedPasswordGroup);


        if (passwordGroup == selectedPasswordGroup) {
            updatePasswordRecordTableView(passwordGroup);
        }
    }

    /**
     * Updates a given password record.
     *
     * @param passwordRecord The password record to be updated.
     */
    protected void updatePasswordRecord(IPasswordRecord passwordRecord) {
        IPasswordRecord selectedPasswordRecord = getSelectedPasswordRecord();

        IPasswordGroup selectedPasswordGroup = getSelectedPasswordGroup();
        updatePasswordRecordTableView(selectedPasswordGroup);

        // Restore previously selected record.
        selectPasswordRecord(selectedPasswordRecord);
    }

    /**
     * Selects the TreeItem in the view belonging to the given group.
     *
     * @param passwordGroup The password group to select.
     */
    protected void selectPasswordGroup(IPasswordGroup passwordGroup) {
        TreeItem<IEntryContainer> treeItem = findPasswordEntryContainerTreeItem(passwordGroup);

        if (treeItem != null) {
            PasswordEntryContainerTreeView passwordEntryContainerTreeView =
                    overviewPane.getPasswordEntryContainerTreeView();
            passwordEntryContainerTreeView.getSelectionModel().select(treeItem);
        }
    }

    /**
     * Returns the currently selected TreeItem if it is a password group.
     *
     * @return The selected password group if succesful, null otherwise
     */
    public IPasswordGroup getSelectedPasswordGroup() {
        PasswordEntryContainerTreeView passwordEntryContainerTreeView =
                overviewPane.getPasswordEntryContainerTreeView();
        TreeItem<IEntryContainer> selectedPasswordEntryContainerTreeItem =
                passwordEntryContainerTreeView.getSelectionModel().getSelectedItem();

        if (selectedPasswordEntryContainerTreeItem == null) {
            return null;
        }

        IEntryContainer selectedPasswordEntryContainer = selectedPasswordEntryContainerTreeItem.getValue();

        if (selectedPasswordEntryContainer instanceof IPasswordGroup) {
            return (IPasswordGroup) selectedPasswordEntryContainer;
        }

        return null;
    }

    /**
     * Selects a given password record in the view.
     *
     * @param passwordRecord The password record to be selected
     */
    protected void selectPasswordRecord(IPasswordRecord passwordRecord) {
        TableView<IPasswordRecord> passwordRecordTableView = overviewPane.getPasswordRecordTableView();
        passwordRecordTableView.getSelectionModel().select(passwordRecord);
    }

    /**
     * Returns the currently selected password record.
     *
     * @return The selected password record
     */
    public IPasswordRecord getSelectedPasswordRecord() {
        PasswordRecordTableView passwordRecordTableView = overviewPane.getPasswordRecordTableView();
        return passwordRecordTableView.getSelectionModel().getSelectedItem();
    }

    /**
     * Gets a list of currently selected password records.
     *
     * @return The selected password records.
     */
    public List<IPasswordRecord> getSelectedPasswordRecords() {
        PasswordRecordTableView passwordRecordTableView = overviewPane.getPasswordRecordTableView();
        return passwordRecordTableView.getSelectionModel().getSelectedItems();
    }

    private void updatePasswordEntryContainerTreeView() {
        PasswordEntryContainerTreeView passwordEntryContainerTreeView =
                overviewPane.getPasswordEntryContainerTreeView();

        PasswordEntryContainerTreeItem rootTreeItem = createPasswordEntryTreeViewItem(passwordLibrary);

        passwordEntryContainerTreeView.setRoot(rootTreeItem);

        expandTreeView(passwordEntryContainerTreeView);
    }

    private PasswordEntryContainerTreeItem createPasswordEntryTreeViewItem(IEntryContainer passwordEntryContainer) {
        PasswordEntryContainerTreeItem treeViewItem = new PasswordEntryContainerTreeItem(passwordEntryContainer);

        List<TreeItem<IEntryContainer>> children = treeViewItem.getChildren();
        List<IEntry> entries = passwordEntryContainer.getEntries();

        for (IEntry entry : entries) {
            if (entry instanceof IPasswordGroup childPasswordGroup) {
                PasswordEntryContainerTreeItem childTreeViewItem = createPasswordEntryTreeViewItem(childPasswordGroup);
                children.add(childTreeViewItem);
            }
        }

        return treeViewItem;
    }

    private static void expandTreeView(TreeView<?> treeView) {
        TreeItem<?> treeItem = treeView.getRoot();
        expandTreeViewItem(treeItem);
    }

    private static void expandTreeViewItem(TreeItem<?> treeItem) {
        if (treeItem != null && !treeItem.isLeaf()) {
            treeItem.setExpanded(true);
            for (TreeItem<?> child : treeItem.getChildren()) {
                expandTreeViewItem(child);
            }
        }
    }

    private void updatePasswordRecordTableView(IPasswordGroup selectedPasswordGroup) {
        if (selectedPasswordGroup != null) {
            List<IEntry> entries = selectedPasswordGroup.getEntries();

            List<IPasswordRecord> records = new ArrayList<>();

            for (IEntry entry : entries) {
                if (entry instanceof IPasswordRecord passwordRecord) {
                    records.add(passwordRecord);
                }
            }

            updatePasswordRecordTableView(records);
        }
    }

    private void updatePasswordRecordTableView(List<IPasswordRecord> records) {
        TableView<IPasswordRecord> passwordRecordTableView = overviewPane.getPasswordRecordTableView();
        List<IPasswordRecord> tableItems = passwordRecordTableView.getItems();

        tableItems.clear();
        tableItems.addAll(records);
    }

    public OverviewPane getView() {
        return overviewPane;
    }
}
