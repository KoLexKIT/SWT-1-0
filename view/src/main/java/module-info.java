/**
 * This module contains the UI of Portunus and many helper classes and methods.
 */
module portunus.view {
    requires transitive portunus.core;
    requires transitive javafx.controls;

    exports portunus.view;
    exports portunus.view.contextmenu;
    exports portunus.view.control;
    exports portunus.view.dialog;
    exports portunus.view.image;
    exports portunus.view.util;
}
