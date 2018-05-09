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

        val textMockResource = MockResource("/text", "GET", SequentialResponseStrategy(), mapOf(
                Pair("main", MockResourceResponse(200, "text/html", "GET ok"))
        ))

        val jsonMockResource = MockResource("/json", "GET", SequentialResponseStrategy(), mapOf(
                Pair("main", MockResourceResponse(200, "application/json", "{\"key\": \"value\"}"))
        ))

        val postMockResource = MockResource("/post", "POST", SequentialResponseStrategy(), mapOf(
                Pair("main", MockResourceResponse(200, "text/html", "POST ok"))
        ))

        store.dispatch(AddMockResourceAction(textMockResource))
        store.dispatch(AddMockResourceAction(jsonMockResource))
        store.dispatch(AddMockResourceAction(postMockResource))
    }

    override fun stop() {
        super.stop()
        store.dispatch(StopServerAction())
    }

}