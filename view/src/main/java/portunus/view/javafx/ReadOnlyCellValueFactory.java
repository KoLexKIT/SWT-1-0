package portunus.view.javafx;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;

/**
 * A cell value factory that is read only.
 *
 * @param <S> The callbacks argument type
 * @param <T> The callbacks methods return type
 */
public abstract class ReadOnlyCellValueFactory<S, T> implements CellValueFactory<S, T> {
    @Override
    public ObservableValue<T> call(CellDataFeatures<S, T> param) {
        S value = param.getValue();
        T formattedValue = formatValue(value);

        return new ReadOnlyObjectWrapper<>(formattedValue);
    }

    /**
     * Formats the value of type. Intentionally protected so it can not be called.
     *
     * @param value The value to format
     * @return The view this factory belongs to.
     */
    protected abstract T formatValue(S value);
}
