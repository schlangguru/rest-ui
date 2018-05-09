package de.schlangguru.restui.gui.views

import de.schlangguru.restui.gui.viewmodels.RequestViewModel
import de.schlangguru.restui.app.model.RequestHeader
import javafx.geometry.Insets
import javafx.scene.layout.VBox
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

            tableview<RequestHeader> {
                column("Name", RequestHeader::name)
                column("Value", RequestHeader::value)
            }

            textarea(viewModel.entity) {
                isEditable = false
            }
        }
    }

}