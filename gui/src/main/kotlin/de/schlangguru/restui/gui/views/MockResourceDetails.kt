package de.schlangguru.restui.gui.views

import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.gui.viewmodels.MockResourceViewModel
import de.schlangguru.restui.gui.viewmodels.MockResponseViewModel
import de.schlangguru.restui.gui.viewmodels.ResponseStrategyViewModel
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.StageStyle
import javafx.util.converter.IntegerStringConverter
import tornadofx.*

class MockResourceDetails : View() {
    override val root = VBox()

    private val viewModel: MockResourceViewModel by inject()
    private val responseViewModel: MockResponseViewModel by inject()
    private val responseStrategyViewModel: ResponseStrategyViewModel by inject()

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
                    enableWhen(viewModel.dirty)
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
                    field ("Response: Strategy") {
                        label(responseStrategyViewModel.type)
                        button {
                            tooltip("Edit")
                            imageview("/icons/edit.png")
                            action { find<ScriptedResponseStrategyEditor>().openModal(stageStyle = StageStyle.UTILITY) }
                        }
                    }
                }

                fieldset("Responses") {
                    tableview(viewModel.responses) {
                        readonlyColumn("Name", MockResponse::name)
                        readonlyColumn("Status", MockResponse::statusCode)
                        readonlyColumn("Content-Type", MockResponse::contentType)
                        readonlyColumn("Entity", MockResponse::content)

                        smartResize()
                        bindSelected(responseViewModel)
                        onUserSelect { find<MockResponseDetails>().openModal(stageStyle = StageStyle.UTILITY) }
                    }
                }
            }
        }
    }
}

class MockResponseDetails: Fragment() {
    override val root = VBox()
    private val viewModel: MockResponseViewModel by inject()
    private val mockResourceViewModel: MockResourceViewModel by inject()

    init {
        with (root) {
            toolbar {
                pane {
                    hgrow = Priority.ALWAYS
                }
                button{
                    tooltip("Save")
                    imageview("/icons/checkmark.png")
                    action {
                        val index = mockResourceViewModel.responses.value.indexOf(viewModel.item)
                        viewModel.commit {
                            mockResourceViewModel.responses.value[index] = viewModel.item
                            close()
                        }
                    }
                    enableWhen(viewModel.dirty)
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

class ScriptedResponseStrategyEditor: Fragment() {
    override val root = VBox()
    private val viewModel: ResponseStrategyViewModel by inject()
    private val mockResourceViewModel: MockResourceViewModel by inject()

    init {
        with (root) {
            toolbar {
                pane {
                    hgrow = Priority.ALWAYS
                }
                button {
                    tooltip("Save")
                    imageview("/icons/checkmark.png")
                    action {
                        viewModel.commit {
                            mockResourceViewModel.responseStrategy.value = viewModel.item
                            close()
                        }
                    }
                    enableWhen(viewModel.dirty)
                }
                button {
                    tooltip("Reset")
                    imageview("/icons/undo.png")
                    action { viewModel.rollback() }
                    enableWhen(viewModel.dirty)
                }
            }
            form {
                fieldset {
                    field("Type:") {
                        combobox(viewModel.type, viewModel.availableTypes)
                    }
                    field("Script:") {
                        textarea(viewModel.script)
                        visibleWhen { viewModel.isEditable }
                    }
                }
            }
        }
    }
}