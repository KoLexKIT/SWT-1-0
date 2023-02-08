package portunus.view.javafx;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Class to easy access to the {@link javafx.util.Callback} interface.
 *
 * @param <S> The callbacks argument type
 * @param <T> The callbacks methods return type
 */
public interface CellValueFactory<S, T> extends Callback<TableColumn.CellDataFeatures<S,T>, ObservableValue<T>> {
    //Intentionally empty. Should only ease access to clumsy interface.
}
