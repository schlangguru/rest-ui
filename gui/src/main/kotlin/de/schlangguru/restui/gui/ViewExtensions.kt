package de.schlangguru.restui.gui

import javafx.beans.property.IntegerProperty
import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.ListView
import javafx.scene.control.TextInputDialog
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Window
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import tornadofx.*
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

/**
 * Allows to attach a [CodeArea] to a [Pane] with the TornadoFX Builder style.
 * The text of the codeArea is bound to the given [textProperty]. Use the [setup] block
 * to initialize the codeArea to your needs.
 */
fun Pane.codeArea(textProperty: Property<String>, setup: CodeArea.() -> Unit = {}) {
    val codeArea = CodeArea()
    with (codeArea) {
        setParagraphGraphicFactory(LineNumberFactory.get(codeArea))
        replaceText(0, 0, textProperty.value)
        this.textProperty().onChange { textProperty.value = it }
    }
    codeArea.setup()
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