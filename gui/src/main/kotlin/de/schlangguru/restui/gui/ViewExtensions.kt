package de.schlangguru.restui.gui

import javafx.beans.property.IntegerProperty
import javafx.scene.control.ListView
import tornadofx.FX
import tornadofx.onChange
import java.util.logging.Level

fun <T> ListView<T>.bindSelectedIndexBidirectional(property: IntegerProperty) {
    selectionModel.selectedIndexProperty().onChange {
        if (property.value != it) {
            property.value = it
        }
    }

    property.onChange {
        if (selectionModel.selectedIndex != it) {
            selectionModel.select(it)
        }
    }
}

fun removeStylesheet(stylesheet: String) {
    val css = FX::class.java.getResource(stylesheet)
    FX.stylesheets.remove(css.toExternalForm())
}