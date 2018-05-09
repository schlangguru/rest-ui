package de.schlangguru.restui.gui.views

import de.schlangguru.restui.gui.viewmodels.MainMenuViewModel
import javafx.scene.control.MenuBar
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
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
            }
            menu("Help") {
                item("About REST-UI")
            }
        }
    }
}