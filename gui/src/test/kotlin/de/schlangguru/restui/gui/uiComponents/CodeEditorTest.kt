package de.schlangguru.restui.gui.uiComponents

import javafx.beans.property.SimpleStringProperty
import javafx.scene.web.WebView
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testfx.api.FxRobot
import org.testfx.api.FxToolkit

class CodeEditorTest {

    /** The js variable of the created editor. */
    private val editorDOM = "editor"
    private lateinit var fxRobot: FxRobot

    init {
        // Initilaize JavaFX
        FxToolkit.registerPrimaryStage()
    }

    @BeforeEach
    fun setup() {
        fxRobot = FxRobot()
    }

    @Test
    fun `it should set the readonly option of the js editor`() {
        fxRobot.interact {
            val webview = WebView()
            val codeEditor = CodeEditor(webview = webview)

            val defaultOption = webview.engine.executeScript("$editorDOM.getOption('readOnly')") as Boolean
            assertThat(defaultOption, `is`(false))

            codeEditor.isEditable = false

            val updatedOption = webview.engine.executeScript("$editorDOM.getOption('readOnly')") as String
            assertThat(updatedOption, `is`("nocursor"))
        }
    }

    @Test
    fun `it should set the inital text of the given textProperty`() {
        fxRobot.interact {
            val webview = WebView()
            val textProperty = SimpleStringProperty("hello World")
            val codeEditor = CodeEditor(textProperty, webview)

            val initialContent = webview.engine.executeScript("$editorDOM.getValue()") as String
            assertThat(initialContent, `is`("hello World"))
        }
    }

    @Test
    fun `it should update the given textProperty`() {
        fxRobot.interact {
            val webview = WebView()
            val textProperty = SimpleStringProperty()
            val codeEditor = CodeEditor(textProperty, webview)

            webview.engine.executeScript("$editorDOM.setValue('hello world')")
            assertThat(textProperty.value, `is`("hello world"))
        }
    }

    @Test
    fun `it should show the value of the given textProperty`() {
        fxRobot.interact {
            val webview = WebView()
            val textProperty = SimpleStringProperty()
            val codeEditor = CodeEditor(textProperty, webview)

            textProperty.value = "hello world"

            val editorContent = webview.engine.executeScript("$editorDOM.getValue()") as String
            assertThat(editorContent, `is`("hello world"))
        }
    }
}