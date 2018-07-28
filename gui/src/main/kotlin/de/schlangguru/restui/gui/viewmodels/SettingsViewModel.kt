package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.UpdateSettingsAction
import de.schlangguru.restui.core.StateHandler
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import tornadofx.toProperty

class SettingsViewModel (
        private val store: AppStore = AppStore
): ItemViewModel<AppState>(), StateHandler<AppState> {
    val serverHost: SimpleStringProperty = bind { item?.host.toProperty() }
    val serverPort: SimpleIntegerProperty = bind { SimpleIntegerProperty(item?.port ?: 0) }
    val useDarkTheme: SimpleBooleanProperty = bind { item?.useDarkTheme.toProperty() }
    val useHTTPS: SimpleBooleanProperty = bind { item?.useHTTPS.toProperty() }
    val keystorePath: SimpleStringProperty = bind { item?.keystorePath.toProperty() }
    val storePassword: SimpleStringProperty = bind { item?.storePassword.toProperty() }
    val showStorePassword = SimpleBooleanProperty(false)
    val keyPassword: SimpleStringProperty = bind { item?.keyPassword.toProperty() }
    val showKeyPassword = SimpleBooleanProperty(false)

    init {
        store.register(this)
    }

    override fun handle(state: AppState) {
        Platform.runLater {
            itemProperty.set(state)
        }
    }

    override fun onCommit() {
        store.dispatch(UpdateSettingsAction(
                serverHost.value,
                serverPort.value,
                useDarkTheme.value,
                useHTTPS.value,
                keystorePath.value,
                storePassword.value,
                keyPassword.value
        ))
    }
}