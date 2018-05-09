package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.UpdateSettingsAction
import de.schlangguru.restui.core.StateHandler
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import tornadofx.ge
import tornadofx.toProperty

class SettingsViewModel (
        private val store: AppStore = AppStore
): ItemViewModel<AppState>(), StateHandler<AppState> {
    val serverHost: SimpleStringProperty = bind { item?.host.toProperty() }
    val serverPort: SimpleIntegerProperty = bind { SimpleIntegerProperty(item?.port ?: 0) }

    init {
        store.register(this)
    }

    override fun handle(state: AppState) {
        Platform.runLater {
            itemProperty.set(state)
        }
    }

    override fun onCommit() {
        store.dispatch(UpdateSettingsAction(serverHost.get(), serverPort.get()))
    }
}