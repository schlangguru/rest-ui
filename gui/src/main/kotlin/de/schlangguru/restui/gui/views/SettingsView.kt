package de.schlangguru.restui.gui.views

import de.schlangguru.restui.gui.viewmodels.SettingsViewModel
import javafx.geometry.Insets
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.util.converter.NumberStringConverter
import tornadofx.*

class SettingsView: View() {
    override val root = VBox()
    private val viewModel: SettingsViewModel by inject()

    init {
        with(root) {

            toolbar {
                pane {
                    hgrow = Priority.ALWAYS
                }
                button{
                    tooltip("Reset")
                    imageview("/icons/undo.png")
                    action { viewModel.rollback() }
                    enableWhen(viewModel.dirty)
                }
                button {
                    tooltip("Save")
                    imageview("/icons/checkmark.png")
                    action { viewModel.commit() }
                    enableWhen(viewModel.dirty)
                }
            }

            form {
                fieldset("Server") {
                    field("Host:") {
                        textfield(viewModel.serverHost)
                    }
                    field("Port: ") {
                        textfield(viewModel.serverPort, NumberStringConverter("#"))
                    }
                    field("Theme: ") {
                        checkbox("Use Dark Theme", viewModel.useDarkTheme)
                    }
                }
            }
        }
    }
}