package de.schlangguru.restui.gui.views

import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.gui.viewmodels.RequestViewModel
import de.schlangguru.restui.app.model.RequestHeader
import de.schlangguru.restui.gui.viewmodels.RequestHeaderViewModel
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.layout.VBox
import javafx.scene.text.FontWeight
import tornadofx.*

class RequestDetails : View() {
    override val root = VBox()

    private val viewModel: RequestViewModel by inject()

    init {
        with(root) {
            padding = Insets(10.0)

            hbox {
                label("URL: ")
                text(viewModel.host)
                text(viewModel.path)
            }

            pane {
                padding = Insets(3.0)
            }

            splitpane {
                orientation = Orientation.VERTICAL
                setDividerPosition(0, .5)

                listview(viewModel.headers) {
                    cellFragment(RequestHeaderCellFragment::class)
                }

                textarea(viewModel.entity) {
                    isEditable = false
                }
            }
        }
    }
}

class RequestHeaderCellFragment: ListCellFragment<RequestHeader>() {
    private val header = RequestHeaderViewModel().bindTo(this)

    override val root = hbox {
        label(header.name) {
            style {
                fontWeight = FontWeight.BOLD
            }
        }
        label(": ") {
            style {
                fontWeight = FontWeight.BOLD
            }
        }
        label(header.value)
    }
}