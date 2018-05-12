package de.schlangguru.restui.gui

import javafx.beans.property.IntegerProperty
import javafx.scene.control.ListView
import tornadofx.onChange

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