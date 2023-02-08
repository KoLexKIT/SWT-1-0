package portunus.view.javafx;

import java.util.List;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * A general Add/Edit dialog for Portunus.
 *
 * @param <T> The type of element to add or edit
 */
public abstract class AddEditDialog<T> extends OKCancelDialog<T> {
    private final T model;

    /**
     * Constructor for the dialog.
     *
     * @param model The model to add/edit
     */
    public AddEditDialog(T model) {
        this.model = model;

        updateViewFromModel();
    }

    @Override
    protected GridPane createContentPane() {
        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        configureColumns(gridPane);

        return gridPane;
    }

    /**
     * Sets up the columns on the GridPane.
     *
     * @param contentPane The preconfigured GridPane of this dialog
     */
    protected void configureColumns(GridPane contentPane) {
        List<ColumnConstraints> columnConstraints = contentPane.getColumnConstraints();

        columnConstraints.clear();

        columnConstraints.add(new ColumnConstraints());

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.ALWAYS);
        columnConstraints.add(column2);
    }

    @Override
    protected void assembleControl() {
        super.assembleControl();

        GridPane gridPane = getContentPane();
        assembleContentPane(gridPane);
    }

    @Override
    protected GridPane getContentPane() {
        return (GridPane) super.getContentPane();
    }

    /**
     * Add contents to the gridpane.
     *
     * @param gridPane The pane to add contents to
     */
    protected abstract void assembleContentPane(GridPane gridPane);

    @Override
    protected T getOKResult() {
        updateModelFromView();
        return model;
    }

    /**
     * Update the view from the model belonging to this class.
     */
    protected abstract void updateViewFromModel();

    /**
     * Update the model belonging to this class form the view.
     */
    protected abstract void updateModelFromView();

    protected T getModel() {
        return model;
    }
}
