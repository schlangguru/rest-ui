package de.schlangguru.restui.gui.views

import de.schlangguru.restui.gui.viewmodels.MainMenuViewModel
import javafx.scene.control.MenuBar
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.stage.FileChooser
import tornadofx.*

class MainMenu: View() {
    override val root = MenuBar()
    private val viewModel: MainMenuViewModel by inject()

    init {
        with(root) {
            menu("File") {
                item(viewModel.startStopServerMenuItemText) {
                    accelerator = KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN)
                    disableProperty().bind(viewModel.startStopServerMenuItemDisabled)
                    action { viewModel.startStopServer() }
                }
                item("Save Configuration to...") {
                    accelerator = KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)
                    action {
                        val selectedFiles = chooseFile(mode = FileChooserMode.Save, filters = arrayOf(FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml")))
                        if (selectedFiles.size > 0) {
                            viewModel.saveConfiguration(selectedFiles[0])
                        }
                    }
                }
                item("Load Configuration...") {
                    action {
                        val selectedFiles = chooseFile(mode = FileChooserMode.Single, filters = arrayOf(FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml")))
                        if (selectedFiles.size > 0) {
                            viewModel.loadConfiguration(selectedFiles[0])
                        }
                    }
                }
            }
            menu("Help") {
                item("About REST-UI")
            }
        }
    }
}