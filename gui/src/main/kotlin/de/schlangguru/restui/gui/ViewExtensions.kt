package de.schlangguru.restui.gui

import javafx.beans.property.IntegerProperty
import javafx.beans.property.Property
import javafx.scene.control.ListView
import javafx.scene.layout.Pane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
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
 * Allows to attach a [CodeArea] to a [Pane] with the TornadoFX Builder style.
 */
fun Pane.codeArea(textProperty: Property<String>, setup: CodeArea.() -> Unit = {}) {
    val codeArea = CodeArea()
    with (codeArea) {
        paragraphGraphicFactory = LineNumberFactory.get(codeArea)
        replaceText(0, 0, textProperty.value ?: "")
        this.textProperty().onChange { textProperty.value = it }
    }
    codeArea.setup()
    add(codeArea)
}