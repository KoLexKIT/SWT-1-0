/**
 * This module contains several packages that contain the controller implementation of portunus.
 */
module portunus.controller {
    requires transitive portunus.core;
    requires transitive portunus.view;

    exports portunus.controller.util;
}
