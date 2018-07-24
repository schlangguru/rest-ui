package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.*
import de.schlangguru.restui.app.model.ServerStatus
import de.schlangguru.restui.core.StateHandler
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import java.io.File

class MainMenuViewModel(
        private val store: AppStore = AppStore
): ItemViewModel<AppState>(), StateHandler<AppState> {
    private val TEXT_START_SERVER = "Start Server"
    private val TEXT_STOP_SERVER = "Stop Server"

    val startStopServerMenuItemText = SimpleStringProperty()
    val startStopServerMenuItemDisabled = SimpleBooleanProperty()

    init {
        store.register(this)
    }

    override fun handle(state: AppState) {
        Platform.runLater {
            item = state
            updateStartStopServerMenuItem(state.serverStatus)
        }
    }

    private fun updateStartStopServerMenuItem(status: ServerStatus?) {
        when (status) {
            ServerStatus.Stopped -> {
                startStopServerMenuItemText.set(TEXT_START_SERVER)
                startStopServerMenuItemDisabled.set(false)
            }
            ServerStatus.Started -> {
                startStopServerMenuItemText.set(TEXT_STOP_SERVER)
                startStopServerMenuItemDisabled.set(false)
            }
            ServerStatus.Starting -> startStopServerMenuItemDisabled.set(true)
            ServerStatus.Stopping -> startStopServerMenuItemDisabled.set(true)
        }
    }

    fun startStopServer() {
        when (item?.serverStatus) {
            ServerStatus.Stopped -> {
                store.dispatch(ServerStatusChangedAction(ServerStatus.Starting))
                store.dispatch(StartServerAction())
            }
            ServerStatus.Started -> {
                store.dispatch(ServerStatusChangedAction(ServerStatus.Stopping))
                store.dispatch(StopServerAction())
            }
            else -> { /* TODO Error Message in AppState? */ }
        }
    }

    fun saveConfiguration(selectedFile: File) {
        store.dispatch(SaveStateAction(selectedFile.absolutePath))
    }

    fun loadConfiguration(file: File) {
        store.dispatch(LoadStateAction(file.absolutePath))
    }
}