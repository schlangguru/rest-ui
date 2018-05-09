package de.schlangguru.restui.gui.views

import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import tornadofx.*

class ContentView: View() {
    override val root = TabPane()

    init {
        with(root) {
            vgrow = Priority.ALWAYS

            tab("Requests") {
                isClosable = false
                add(RequestsView::class)
            }
            tab("Mock Resources") {
                isClosable = false
                add(ActionsView::class)
            }
            tab("Settings") {
                isClosable = false
                add(SettingsView::class)
            }
        }
    }
}