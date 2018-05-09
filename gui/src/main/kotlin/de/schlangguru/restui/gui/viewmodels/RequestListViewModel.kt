package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.core.StateHandler
import javafx.application.Platform
import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.ItemViewModel
import tornadofx.observable

class RequestListViewModel(
        store: AppStore = AppStore
): ItemViewModel<AppState>(), StateHandler<AppState> {
    val requests = bind { ReadOnlyListWrapper<Request>(item?.requestInbox?.observable()) }

    init {
        store.register(this)
    }

    override fun handle(state: AppState) {
        Platform.runLater {
            item = state
        }
    }
}