package de.schlangguru.restui.gui.views

import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.gui.bindSelectedIndexBidirectional
import de.schlangguru.restui.gui.viewmodels.MockResourceListViewModel
import de.schlangguru.restui.gui.viewmodels.MockResourceViewModel
import de.schlangguru.restui.gui.viewmodels.NewMockResourceDialogViewModel
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.FontWeight
import javafx.stage.StageStyle
import tornadofx.*


class MockResourceList: View() {
    override val root = VBox()

    private val listViewModel: MockResourceListViewModel by inject()
    private val detailsViewModel: MockResourceViewModel by inject()

    init {
        with (root) {
            toolbar {
                pane {
                    hgrow = Priority.ALWAYS
                }
                button{
                    tooltip("Create New")
                    imageview("/icons/plus.png")
                    action { find<NewMockResourceDialog>().openModal(stageStyle = StageStyle.UTILITY) }
                }
                button {
                    tooltip("Delete")
                    imageview("/icons/empty_trash.png")
                    action { listViewModel.deleteSelectedMockResource() }
                    enableWhen(listViewModel.selectedItem.isNotNull)
                }
            }

            listview(listViewModel.mockResources) {
                cellFragment(MockResourceListCellFragment::class)
                bindSelected(detailsViewModel)
                bindSelected(listViewModel.selectedItem)
                bindSelectedIndexBidirectional(listViewModel.selectedIndex)
                vgrow = Priority.ALWAYS
            }
        }
    }
}

class MockResourceListCellFragment: ListCellFragment<MockResource>() {
    private val resource = MockResourceViewModel().bindTo(this)

    override val root = hbox {
        label(resource.method) {
            style {
                fontWeight = FontWeight.BOLD
            }
        }
        label(" ")
        label(resource.path)
    }
}

class NewMockResourceDialog : Fragment() {
    override val root = VBox()
    private val viewModel: NewMockResourceDialogViewModel by inject()

    init {
        title = "New Mock Resource"
        with(root) {
            form {
                fieldset {
                    field("Path:") {
                        textfield(viewModel.path)
                    }
                }
            }
            toolbar {
                pane {
                    hgrow = Priority.ALWAYS
                }
                button("Add") {
                    action {
                        viewModel.commit()
                        close()
                    }
                }
                button("Cancel") {
                    action {
                        viewModel.rollback()
                        close()
                    }
                }
            }
        }
    }
}