package de.schlangguru.restui.gui

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.AddMockResourceAction
import de.schlangguru.restui.app.actions.StopServerAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.app.server.RestServerImpl
import de.schlangguru.restui.app.server.ScriptedResponseStrategy
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

        val textMockResource = MockResource(path = "/text", method = "GET", responseStrategy = SequentialResponseStrategy(), responses = listOf(
                MockResponse("main", 200, "text/html", "GET ok"),
                MockResponse("secondary", 204, "text/html", "")
        ))

        val jsonMockResource = MockResource(path = "/json", method = "GET", responseStrategy = SequentialResponseStrategy(), responses = listOf(
                MockResponse("main", 200, "application/json", "{\"key\": \"value\"}")
        ))

        val postMockResource = MockResource(path = "/post", method =  "POST", responseStrategy = SequentialResponseStrategy(), responses =  listOf(
                MockResponse("main", 200, "text/html", "POST ok")
        ))

        val script = """
            print (JSON.stringify(_request))
            _request.queryParameter["q"]
        """
        val scriptedResponseResource = MockResource(path = "/script", method = "GET", responseStrategy = ScriptedResponseStrategy(script), responses = listOf(
                MockResponse("main", 200, "text/html", "Proudly presented by script."),
                MockResponse("secondary", 200, "text/html", "Proudly presented by script. (Secondary)")
        ))

        store.dispatch(AddMockResourceAction(textMockResource))
        store.dispatch(AddMockResourceAction(jsonMockResource))
        store.dispatch(AddMockResourceAction(postMockResource))
        store.dispatch(AddMockResourceAction(scriptedResponseResource))
    }

    override fun stop() {
        super.stop()
        store.dispatch(StopServerAction())
    }

}