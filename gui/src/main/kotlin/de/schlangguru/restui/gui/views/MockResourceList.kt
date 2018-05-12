package de.schlangguru.restui.gui.views

import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.gui.bindSelectedIndexBidirectional
import de.schlangguru.restui.gui.viewmodels.MockResourceListViewModel
import de.schlangguru.restui.gui.viewmodels.MockResourceViewModel
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.FontWeight
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
                    action { listViewModel.createNewMockResource() }
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