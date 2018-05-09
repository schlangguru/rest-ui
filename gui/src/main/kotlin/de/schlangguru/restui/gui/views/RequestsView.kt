package de.schlangguru.restui.gui.views

import de.schlangguru.restui.gui.views.RequestDetails
import de.schlangguru.restui.gui.views.RequestList
import javafx.scene.control.SplitPane
import tornadofx.View

class RequestsView: View() {
    override val root = SplitPane()

    init {
        with(root) {
            add(RequestList::class)
            add(RequestDetails::class)
            setDividerPosition(0, .3)
        }
    }

}