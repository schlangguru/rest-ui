package de.schlangguru.restui.gui.views

import de.schlangguru.restui.gui.passwordTextField
import de.schlangguru.restui.gui.viewmodels.CertificateGeneratorViewModel
import de.schlangguru.restui.gui.viewmodels.SettingsViewModel
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import tornadofx.*

class CertificateGeneratorDialog : Fragment() {
    override val root = VBox()
    private val viewModel: CertificateGeneratorViewModel by inject()
    private val settingsViewModel: SettingsViewModel by inject()

    init {
        title = "Create self signed certificate"
        with(root) {
            toolbar {
                pane {
                    hgrow = Priority.ALWAYS
                }
                button {
                    tooltip("Create Certificate")
                    imageview("/icons/checkmark.png")
                    action {
                        chooseFile()
                        viewModel.commit {
                            syncSettings()
                            close()
                        }
                    }
                }
            }
            form {
                fieldset("Certificate Info") {
                    field("Alias") {
                        textfield(viewModel.alias)
                    }
                    field("Key Size") {
                        combobox(viewModel.keysize, viewModel.availableKeySizes)
                    }
                    field("Keystore Password") {
                        passwordTextField(viewModel.storePassword)
                    }
                    field("Key Password") {
                        passwordTextField(viewModel.keyPassword)
                    }
                    field("Distinguished Name") {
                        textfield(viewModel.dName)
                    }
                }
            }
        }
    }

    /**
     * Sets the settings of the [SettingsViewModel] to the values of this dialog.
     */
    private fun syncSettings() {
        settingsViewModel.keystorePath.value = viewModel.keystorePath.value
        settingsViewModel.storePassword.value = viewModel.storePassword.value
        settingsViewModel.keyPassword.value = viewModel.keyPassword.value
    }

    /**
     * Opens a file-chooser to select a file.
     */
    private fun chooseFile() {
        val selectedFiles = chooseFile(mode = FileChooserMode.Save, filters = arrayOf(FileChooser.ExtensionFilter("JKS files (*.jks)", "*.jks")))
        if (selectedFiles.isNotEmpty()) {
            viewModel.keystorePath.value = selectedFiles[0].absolutePath
        }
    }
}