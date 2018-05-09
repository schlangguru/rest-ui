package de.schlangguru.restui.gui.views

import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.gui.viewmodels.RequestListViewModel
import de.schlangguru.restui.gui.viewmodels.RequestViewModel
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.FontWeight
import tornadofx.*


class RequestList: View() {
    override val root = VBox()

    private val requestListViewModel: RequestListViewModel by inject()
    private val requestViewModel: RequestViewModel by inject()

    init {
        with (root) {
            listview<Request>(requestListViewModel.requests) {
                cellFragment(RequestListCellFragment::class)
                bindSelected(requestViewModel)
                vgrow = Priority.ALWAYS
            }
        }
    }
}

class RequestListCellFragment : ListCellFragment<Request>() {
    val request = RequestViewModel().bindTo(this)

    override val root = hbox {
        label(request.method) {
            style {
                fontWeight = FontWeight.BOLD
            }
        }
        label(" ")
        label(request.path)
    }
}