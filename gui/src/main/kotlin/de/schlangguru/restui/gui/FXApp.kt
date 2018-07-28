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
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.App
import tornadofx.FX
import tornadofx.addStageIcon
import tornadofx.setStageIcon

/**
 * Main Function to launch the application.
 */
fun main(vararg args: String) {
    Application.launch(FXApp::class.java, *args)
}

/**
 * Initializes the application.
 */
class FXApp: App(MainView::class) {
    private val store = AppStore

    override fun start(stage: Stage) {
        super.start(stage)

        store.register(ThemeStateHandler())
        initRestServer()
    }

    /**
     * Initializes the Restserver.
     */
    private fun initRestServer() {
        RestServerImpl()
    }

    /**
     * Stops and closes the application.
     */
    override fun stop() {
        store.dispatchAndWait(StopServerAction())
        store.shutdown()
        super.stop()
    }

}