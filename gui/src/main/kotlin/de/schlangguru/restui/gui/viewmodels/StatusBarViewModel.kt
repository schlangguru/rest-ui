package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.model.ServerStatus
import de.schlangguru.restui.core.StateHandler
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class StatusBarViewModel(
        store: AppStore = AppStore
): ItemViewModel<AppState>(), StateHandler<AppState> {
    private val TEXT_SERVER_STOPPED = "Server gestoppt"
    private val TEXT_SERVER_STOPPING = "Server wird gestoppt..."
    private val TEXT_STERVER_STARTED = "Server gestartet: %s"
    private val TEXT_SERVER_STARTING = "Server wird gestartet..."

    val statusText = SimpleStringProperty(this, "status_text", "")

    init {
        store.register(this)
    }

    override fun handle(state: AppState) {
        Platform.runLater {
            item = state
            upateServerStatus(state.serverStatus)
        }
    }

    private fun upateServerStatus(serverStatus: ServerStatus?) {
        when (serverStatus) {
            ServerStatus.Stopped -> statusText.set(TEXT_SERVER_STOPPED)
            ServerStatus.Started -> statusText.set(TEXT_STERVER_STARTED.format("${item?.host}:${item?.port}"))
            ServerStatus.Stopping -> statusText.set(TEXT_SERVER_STOPPING)
            ServerStatus.Starting -> statusText.set(TEXT_SERVER_STARTING)
        }
    }
}