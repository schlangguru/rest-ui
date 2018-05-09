package de.schlangguru.restui.gui.views

import de.schlangguru.restui.gui.viewmodels.StatusBarViewModel
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import tornadofx.View
import tornadofx.hgrow
import tornadofx.label
import tornadofx.pane

class StatusBar: View() {
    override val root = HBox()
    private val viewModel: StatusBarViewModel by inject()

    init {
        with(root) {
            label(viewModel.statusText)
        }
    }
}