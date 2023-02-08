package portunus.view.javafx;

import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * An abstract class for a dialog with an ok and a cancel option.
 *
 * @param <T> The model type belonging to this instance
 */
public abstract class OKCancelDialog<T> extends Dialog<T> {
    /**
     * Initializes the dialog.
     */
    public OKCancelDialog() {
        assembleControl();
        initializeControl();
    }

    /**
     * Give a title to this dialog.
     *
     * @return The given title
     */
    protected abstract String createTitle();

    /**
     * Not implemented!
     *
     * @return always null
     */
    protected Image createImage() {
        return null;
    }

    /**
     * Creates the content pane for this dialog.
     *
     * @return The generated pane for display
     */
    protected abstract Pane createContentPane();

    /**
     * Assembles the control for the content pane. Adds titles, size, icon and some more stuff.
     */
    protected void assembleControl() {
        setTitle(createTitle());
        setResizable(true);


        setIcon();

        DialogPane dialogPane = getDialogPane();

        Pane contentPane = createContentPane();
        dialogPane.setContent(contentPane);

        addButtons();

        Callback<ButtonType, T> resultConverter = createResultConverter();
        setResultConverter(resultConverter);
    }

    /**
     * Initializes the control and focuses it.
     */
    protected void initializeControl() {
        Control initialFocusControl = getInitialFocusControl();

        if (initialFocusControl != null) {
            Platform.runLater(initialFocusControl::requestFocus);
        }
    }

    /**
     * Callback returning the correkt result type.
     *
     * @return The callback
     */
    protected Callback<ButtonType, T> createResultConverter() {
        return buttonType -> {
            if (buttonType == ButtonType.OK) {
                return getOKResult();
            }

            if (buttonType == ButtonType.CANCEL) {
                return getCancelResult();
            }

            return null;
        };
    }

    protected Control getInitialFocusControl() {
        return null;
    }

    /**
     * Called when the dialog gets exited with the OK option.
     *
     * @return The result
     */
    protected abstract T getOKResult();

    protected T getCancelResult() {
        return null;
    }

    /**
     * Returns the current content pane.
     *
     * @return The content pane
     */
    protected Pane getContentPane() {
        DialogPane dialogPane = getDialogPane();
        return (Pane) dialogPane.getContent();
    }

    private void setIcon() {
        //Set icon if provided.
        Image image = createImage();

        if (image != null) {
            Stage stage = (Stage) getDialogPane().getScene().getWindow();
            List<Image> icons = stage.getIcons();
            icons.add(image);
        }
    }

    private void addButtons() {
        DialogPane dialogPane = getDialogPane();
        List<ButtonType> buttonTypes = dialogPane.getButtonTypes();
        buttonTypes.add(ButtonType.OK);
        buttonTypes.add(ButtonType.CANCEL);
    }
}
