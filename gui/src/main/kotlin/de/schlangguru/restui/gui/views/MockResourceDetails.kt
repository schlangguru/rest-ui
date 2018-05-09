package de.schlangguru.restui.gui.views

import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.gui.viewmodels.RequestViewModel
import de.schlangguru.restui.app.model.RequestHeader
import de.schlangguru.restui.gui.viewmodels.MockResourceViewModel
import de.schlangguru.restui.gui.viewmodels.RequestHeaderViewModel
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.layout.VBox
import javafx.scene.text.FontWeight
import tornadofx.*

class MockResourceDetails : View() {
    override val root = VBox()

    private val viewModel: MockResourceViewModel by inject()

    init {
        with(root) {

        }
    }
}