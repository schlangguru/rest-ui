package de.schlangguru.restui.gui.views

import de.schlangguru.restui.app.model.MockResourceResponse
import de.schlangguru.restui.gui.viewmodels.MockResourceResponseViewModel
import de.schlangguru.restui.gui.viewmodels.MockResourceViewModel
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.StageStyle
import javafx.util.converter.NumberStringConverter
import tornadofx.*

class MockResourceDetails : View() {
    override val root = VBox()

    private val viewModel: MockResourceViewModel by inject()
    private val responseViewModel: MockResourceResponseViewModel by inject()

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
                        readonlyColumn("Name", MockResourceResponse::name)
                        readonlyColumn("Status", MockResourceResponse::statusCode)
                        readonlyColumn("Content-Type", MockResourceResponse::contentType)
                        readonlyColumn("Entity", MockResourceResponse::content)

                        smartResize()
                        bindSelected(responseViewModel)
                        onUserSelect { find(MockResourceResponseDetails::class).openModal(stageStyle = StageStyle.UTILITY) }
                    }
                }
            }
        }
    }
}

class MockResourceResponseDetails: Fragment() {
    override val root = VBox()
    private val viewModel: MockResourceResponseViewModel by inject()

    init {
        with (root) {
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
                fieldset("Response: ${viewModel.name.value}") {
                    field("Status Code:") {
                        textfield(viewModel.statusCode, NumberStringConverter("#"))
                    }
                    field("Content Type: ") {
                        textfield(viewModel.contentType)
                    }
                    field("Entity: ") {
                        textarea(viewModel.content)
                    }
                }
            }
        }
    }
}