package de.schlangguru.restui.gui.views

import de.schlangguru.restui.gui.viewmodels.MockResponseViewModel
import de.schlangguru.restui.gui.viewmodels.MockResourceViewModel
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.StageStyle
import javafx.util.converter.IntegerStringConverter
import tornadofx.*

class MockResourceDetails : View() {
    override val root = VBox()

    private val viewModel: MockResourceViewModel by inject()

    init {
        with(root) {
            toolbar {
                pane {
                    hgrow = Priority.ALWAYS
                }
                button{
                    tooltip("Reset")
                    imageview("/icons/checkmark.png")
                    action { viewModel.commit() }
                }
                button{
                    tooltip("Reset")
                    imageview("/icons/undo.png")
                    action { viewModel.rollback() }
                }
            }

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
                        column("Name", MockResponseViewModel::name)
                        column("Status", MockResponseViewModel::statusCode)
                        column("Content-Type", MockResponseViewModel::contentType)
                        column("Entity", MockResponseViewModel::content)

                        smartResize()
                        onUserSelect { MockResourceResponseDetails(it).openWindow(stageStyle = StageStyle.UTILITY) }
                    }
                }
            }
        }
    }
}

class MockResourceResponseDetails (
        private val viewModel: MockResponseViewModel
) : Fragment() {
    override val root = VBox()

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
            }

            form {
                fieldset("Response: ${viewModel.name.value}") {
                    field("Status Code:") {
                        textfield(viewModel.statusCode, IntegerStringConverter())
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