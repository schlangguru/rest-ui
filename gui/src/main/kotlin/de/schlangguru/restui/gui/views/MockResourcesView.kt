package de.schlangguru.restui.gui.views

import javafx.scene.control.SplitPane
import tornadofx.View

class MockResourcesView: View() {
    override val root = SplitPane()

    init {
        with(root) {
            add(MockResourceList::class)
            add(MockResourceDetails::class)
            setDividerPosition(0, .3)
        }
    }

}