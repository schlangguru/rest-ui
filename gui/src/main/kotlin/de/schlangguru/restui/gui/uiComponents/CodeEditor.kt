package de.schlangguru.restui.gui.uiComponents

import javafx.beans.property.Property
import javafx.beans.value.ObservableValue
import javafx.concurrent.Worker
import javafx.scene.layout.StackPane
import javafx.scene.web.WebView
import netscape.javascript.JSObject
import org.apache.commons.text.StringEscapeUtils
import tornadofx.onChange
import java.util.concurrent.CompletableFuture

class CodeEditor (textProperty: Property<String>) : StackPane() {
    private val webview = WebView()
    private val htmlTemplate = "/codeEditor/template.html"
    private val isReady = CompletableFuture<Boolean>()

    private var text: String?
        set(value) {
            webview.engine.executeScript("editor.setValue('${StringEscapeUtils.escapeEcmaScript(value ?: "")}')")
        }
        get() {
            return webview.engine.executeScript("""editor.getValue()""") as String
        }

    init {
        registerReadyListener()
        registerTextObserver(textProperty)
        onReady { text = textProperty.value }

        webview.engine.load(CodeEditor::class.java.getResource(htmlTemplate).toExternalForm())
        webview.setPrefSize(650.0, 325.0)
        webview.setMinSize(650.0, 325.0)

        children.add(webview)
    }


    private fun onReady(block: () -> Unit) {
        isReady.thenRun(block)
    }

    private fun registerReadyListener() {
        webview.engine.loadWorker.stateProperty().addListener(
                { observable: ObservableValue<out Worker.State>, oldValue: Worker.State, newValue: Worker.State ->
                    if (newValue == Worker.State.SUCCEEDED) {
                        isReady.complete(true)
                    }
                })
    }

    private fun registerTextObserver(textProperty: Property<String>) {
        val windowDOM = webview.engine.executeScript("window") as JSObject
        windowDOM.setMember("_fxTextPropertyBridge", object {
            fun setValue(text: String) {
                if (textProperty.value != text) {
                    textProperty.value = text
                }
            }
        })

        textProperty.onChange {
            if (text != it) {
                text = it
            }
        }
    }
}