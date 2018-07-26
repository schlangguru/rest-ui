package de.schlangguru.restui.gui.uiComponents

import com.sun.javafx.webkit.WebConsoleListener
import javafx.beans.property.Property
import javafx.concurrent.Worker
import javafx.scene.layout.StackPane
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import netscape.javascript.JSObject
import org.apache.commons.text.StringEscapeUtils
import tornadofx.onChange
import java.util.concurrent.CompletableFuture


/**
 * Provides a code editor ui component.
 * The code editor is based on [WebView] and the codemirror
 * project. See [codemirror.net](https://codemirror.net/).
 */
class CodeEditor(textProperty: Property<String>) : StackPane() {
    private val webview = WebView()
    val webviewReadyFuture = CompletableFuture<Worker.State>()
    private val htmlTemplate = "/codeEditor/template.html"

    private val jsInbound = JSInbound(textProperty)
    private val jsOutbound = JSOutbound(webview.engine)

    /**
     * Editable property.
     * If set to false, the editor will be set to read-only mode.
     * Default is `true`.
     */
    var isEditable: Boolean = true
        set(value) = whenReady {
            val option = if (value) "false" else "'nocursor'"
            jsOutbound.setOption("readOnly", option)
            field = value
        }

    init {
        setupReadyListener()
        setupWebView()
        bindTextPropertyToJS(textProperty)

        children.add(webview)
    }

    private fun whenReady(block: () -> Unit) {
        webviewReadyFuture.thenRun(block)
    }

    /**
     * Binds the [textProperty] to the content of the js code editor so
     * the the property will always reflect the contents of the editor.
     */
    private fun bindTextPropertyToJS(textProperty: Property<String>) = whenReady {
        // editor change -> textproperty
        val windowDOM = webview.engine.executeScript("window") as JSObject
        windowDOM.setMember("_fxTextPropertyBridge", jsInbound)

        // textproperty change -> editor
        textProperty.onChange {
            if (jsOutbound.getText() != it) {
                jsOutbound.setText(it)
            }
        }

        // Inital text
        jsOutbound.setText(textProperty.value)
    }

    private fun setupWebView() {
        webview.engine.load(CodeEditor::class.java.getResource(htmlTemplate).toExternalForm())
        webview.setPrefSize(650.0, 325.0)
        webview.setMinSize(650.0, 325.0)

        WebConsoleListener.setDefaultListener { _, message, lineNumber, _ ->
            println("CodeEditor-Log: $message (at $lineNumber)")
        }
    }

    /**
     * Blocks until the editor is loaded in the webview.
     */
    private fun setupReadyListener() {
        webview.engine.loadWorker.stateProperty().addListener { _, _, newValue: Worker.State ->
            if (newValue == Worker.State.SUCCEEDED) {
                webviewReadyFuture.complete(Worker.State.SUCCEEDED)
            }
        }
    }

    /**
     * Provides the interface from JS code to java.
     * Allows the JS code to interact with the [textProperty].
     */
    class JSInbound(private val textProperty: Property<String>) {

        /**
         * Called from the js editor when its content has changed.
         * Updates the [textProperty] with the new [text] of the editor.
         */
        @Suppress("UNUSED")
        fun onTextChanged(text: Any?) {
            if (textProperty.value != text) {
                textProperty.value = text?.toString()
            }
        }

    }

    /**
     * Provides the interface from java to the JS code.
     */
    class JSOutbound(private val engine: WebEngine) {

        /**
         * Sets a configuration option in the code editor.
         */
        fun setOption(optionKey: String, value: Any?) {
            val script = "editor.setOption('$optionKey', $value)"
            engine.executeScript(script)
        }

        /**
         * Sets the text content of the code editor.
         */
        fun setText(value: String?) {
            val escapedValue = StringEscapeUtils.escapeEcmaScript(value ?: "")
            val script = "editor.setValue('$escapedValue')"
            engine.executeScript(script)
        }

        /**
         * Returns the text content of the code editor.
         */
        fun getText(): String {
            val script = "editor.getValue()"
            return engine.executeScript(script) as String
        }
    }
}