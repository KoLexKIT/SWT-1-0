package portunus.view;

import java.util.List;

import portunus.view.control.PasswordEntryContainerTreeView;
import portunus.view.control.PasswordRecordTableView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * The central UI class for Portunus assembling all other UI elements.
 */
public class OverviewPane extends BorderPane {
    private PasswordEntryContainerTreeView passwordEntryContainerTreeView;

    private TextField searchTextField;
    private Button search_button;

    private PasswordRecordTableView passwordRecordTableView;

    /**
     * Constructor for an overviewpane. Constructs the whole UI.
     */
    public OverviewPane() {
        constructUI();
    }

    private void constructUI() {
        passwordEntryContainerTreeView = new PasswordEntryContainerTreeView();
        searchTextField = new TextField();
        search_button = new Button("Search");
        passwordRecordTableView = new PasswordRecordTableView();


        SplitPane splitPane = new SplitPane();
        List<Node> splitPaneItems = splitPane.getItems();
        setCenter(splitPane);

        splitPaneItems.add(passwordEntryContainerTreeView);

        BorderPane rightPane = new BorderPane();

        BorderPane searchPane = new BorderPane();
        searchPane.setCenter(searchTextField);
        searchPane.setRight(search_button);
        rightPane.setTop(searchPane);

        rightPane.setCenter(passwordRecordTableView);

        splitPaneItems.add(rightPane);

        splitPane.setDividerPosition(0, 0.3);
    }

    public PasswordEntryContainerTreeView getPasswordEntryContainerTreeView() {
        return passwordEntryContainerTreeView;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    public Button getSearchButton() {
        return search_button;
    }

    public PasswordRecordTableView getPasswordRecordTableView() {
        return passwordRecordTableView;
    }
}
