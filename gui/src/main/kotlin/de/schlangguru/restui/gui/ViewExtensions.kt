package de.schlangguru.restui.gui

import de.schlangguru.restui.gui.uiComponents.CodeEditor
import javafx.beans.property.IntegerProperty
import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ListView
import javafx.scene.control.TextInputDialog
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Window
import tornadofx.*

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
fun Pane.codeEditor(textProperty: Property<String>, setup: CodeEditor.() -> Unit = {}) {
    val editor = CodeEditor(textProperty)
    editor.setup()
    add(editor)
}

/**
 * Creates a password field that has a show/hide text button.
 */
fun Pane.passwordTextField(textProperty: Property<String>, showTextWhen: SimpleBooleanProperty = SimpleBooleanProperty()) {
    val eyeImage = Image(FXApp::class.java.getResource("/icons/eye.png").toExternalForm())
    val eyeOffImage = Image(FXApp::class.java.getResource("/icons/eye-off.png").toExternalForm())
    val imageBinding = showTextWhen.objectBinding {
        if (it == true) eyeImage else eyeOffImage
    }

    passwordfield(textProperty) {
        visibleWhen { showTextWhen.not() }
        managedWhen  { showTextWhen.not() }
    }
    textfield(textProperty) {
        visibleWhen { showTextWhen }
        managedWhen  { showTextWhen }
    }
    button {
        imageview() {
            imageProperty().bind(imageBinding)
        }
        action {
            showTextWhen.value = !showTextWhen.value
        }
    }
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
fun <E> observableList(collection: Collection<E>?): ObservableList<E> = FXCollections.observableArrayList(collection ?: emptyList())