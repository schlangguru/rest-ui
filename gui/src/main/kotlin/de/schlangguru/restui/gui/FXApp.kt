package de.schlangguru.restui.gui

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.AddMockResourceAction
import de.schlangguru.restui.app.actions.StopServerAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResourceResponse
import de.schlangguru.restui.app.server.RestServerImpl
import de.schlangguru.restui.app.server.SequentialResponseStrategy
import de.schlangguru.restui.gui.views.MainView
import javafx.application.Application
import tornadofx.App

fun main(vararg args: String) {
    Application.launch(FXApp::class.java, *args)
}

class FXApp: App(MainView::class) {
    private val store = AppStore

    init {
        RestServerImpl()

        val responses = mapOf(
                Pair("eins", MockResourceResponse(200, "text/html", "eins")),
                Pair("zwei", MockResourceResponse(200, "text/html", "zwei")),
                Pair("drei", MockResourceResponse(200, "application/json", "{\"key\": \"value\"}"))
        )
        val mockResource = MockResource("/mockResource", "GET", SequentialResponseStrategy(), responses)

        store.dispatch(AddMockResourceAction(mockResource))
    }

    override fun stop() {
        super.stop()
        store.dispatch(StopServerAction())
    }

}