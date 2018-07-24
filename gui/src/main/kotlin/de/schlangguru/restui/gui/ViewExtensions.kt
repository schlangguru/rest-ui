package de.schlangguru.restui.gui

import de.schlangguru.restui.gui.uiComponents.CodeEditor
import javafx.beans.property.IntegerProperty
import javafx.beans.property.Property
import javafx.collections.FXCollections
import javafx.scene.control.ListView
import javafx.scene.control.TextInputDialog
import javafx.scene.layout.Pane
import javafx.stage.Window
import tornadofx.FX
import tornadofx.add
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

fun removeStylesheet(stylesheet: String) {
    val css = FX::class.java.getResource(stylesheet)
    FX.stylesheets.remove(css.toExternalForm())
}

/**
 * Allows to attach a [CodeEditor] to a [Pane] with the TornadoFX Builder style.
 * The text of the codeEditor is bound to the given [textProperty].
 */
fun Pane.codeEditor(textProperty: Property<String>) {
    val codeArea = CodeEditor(textProperty)
    add(codeArea)
}

/**
 * Opens a [TextInputDialog] for the user.
 * The [onOk] callback is called with the input
 * of the user if the "ok" button is clicked.
 */
fun prompt(title: String = "",
           header: String = "",
           content: String = "",
           owner: Window? = null,
           onOk: (String) -> Unit = {}) {

    val dialog = TextInputDialog()
    dialog.title = title
    dialog.headerText = header
    dialog.contentText = content

    val result = dialog.showAndWait()
    result.ifPresent { onOk(it) }
}

/**
 * Nullpointer save wrapper for [FXCollections.observableArrayList].
 */
fun <E> observableList(collection: Collection<E>?) = FXCollections.observableArrayList(collection ?: emptyList())