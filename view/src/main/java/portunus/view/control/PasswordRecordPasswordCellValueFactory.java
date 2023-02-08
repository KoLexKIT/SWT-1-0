package portunus.view.control;

import portunus.core.IPasswordRecord;
import portunus.view.javafx.ReadOnlyCellValueFactory;

/**
 * Class generating the password cell values for password records in the  password record table view.
 */
public class PasswordRecordPasswordCellValueFactory extends ReadOnlyCellValueFactory<IPasswordRecord, String> {
    @Override
    protected String formatValue(IPasswordRecord passwordRecord) {
        String password = passwordRecord.getPassword();

        if (password.isEmpty()) {
            return "";
        } else {
            return "*****";
        }
    }
}
