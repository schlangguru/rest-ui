package de.schlangguru.restui.gui.views

import de.schlangguru.restui.gui.passwordTextField
import de.schlangguru.restui.gui.viewmodels.SettingsViewModel
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
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
                fieldset("UI") {
                    field("Theme: ") {
                        checkbox("Use Dark Theme", viewModel.useDarkTheme)
                    }
                }
                fieldset("Server") {
                    field("Host:") {
                        textfield(viewModel.serverHost)
                    }
                    field("Port: ") {
                        textfield(viewModel.serverPort, NumberStringConverter("#"))
                    }
                    field("HTTPS: ") {
                        checkbox("Enabled", viewModel.useHTTPS)
                    }
                    field("Keystore: ") {
                        enableWhen{ viewModel.useHTTPS }
                        textfield(viewModel.keystorePath)
                        button("Choose file...") {
                            action {
                                chooseJKS()
                            }
                        }
                    }
                    field("Keystore Password: ") {
                        enableWhen{ viewModel.useHTTPS }
                        passwordTextField(viewModel.storePassword, viewModel.showStorePassword)
                    }
                    field("Key Password: ") {
                        enableWhen{ viewModel.useHTTPS }
                        passwordTextField(viewModel.keyPassword, viewModel.showKeyPassword)
                    }
                }

            }
        }
    }

    /**
     * Opens a file-chooser to select a jks.
     * Sets the selected jks in the viewmodel.
     */
    private fun chooseJKS() {
        val selectedFiles = chooseFile(mode = FileChooserMode.Single, filters = arrayOf(FileChooser.ExtensionFilter("JKS files (*.jks)", "*.jks")))
        if (selectedFiles.isNotEmpty()) {
            viewModel.keystorePath.value = selectedFiles[0].absolutePath
        }
    }
}