package de.schlangguru.restui.gui.views

import de.schlangguru.restui.app.model.MockResourceResponse
import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.gui.viewmodels.RequestViewModel
import de.schlangguru.restui.app.model.RequestHeader
import de.schlangguru.restui.gui.viewmodels.MockResourceViewModel
import de.schlangguru.restui.gui.viewmodels.RequestHeaderViewModel
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.layout.VBox
import javafx.scene.text.FontWeight
import javafx.util.converter.NumberStringConverter
import tornadofx.*

class MockResourceDetails : View() {
    override val root = VBox()

    private val viewModel: MockResourceViewModel by inject()

    init {
        with(root) {
            form {
                fieldset("Mock Resource") {
                    field("HTTP Method:") {
                        combobox(viewModel.method, viewModel.availableMethods)
                    }
                    field("Path: ") {
                        textfield(viewModel.path) {
                            promptText = "/your/resource/path"
                        }
                    }
                }

                fieldset("Responses") {
                    tableview(viewModel.responses) {
                        smartResize()
                        readonlyColumn("Name", MockResourceResponse::name)
                        readonlyColumn("Status", MockResourceResponse::statusCode)
                        readonlyColumn("Content-Type", MockResourceResponse::contentType)
                        readonlyColumn("Entity", MockResourceResponse::content)
                    }
                }
            }
        }
    }
}