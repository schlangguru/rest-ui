package de.schlangguru.restui.gui.views

import javafx.scene.layout.VBox
import tornadofx.View


class MainView: View() {
    override val root = VBox()

    init {
       with(root) {
           add(MainMenu::class)
           add(ContentView::class)
           add(StatusBar::class)

           setPrefSize(900.0, 600.0)
       }
    }

}
