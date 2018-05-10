package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.RemoveMockResourceAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.core.StateHandler
import javafx.application.Platform
import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyListWrapper
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ItemViewModel
import tornadofx.observable

class MockResourceListViewModel(
        private val store: AppStore = AppStore
): ItemViewModel<AppState>(), StateHandler<AppState> {
    val mockResources = bind { ReadOnlyListWrapper<MockResource>(item?.mockResources?.observable()) }
    val selected = SimpleObjectProperty<MockResource>()

    init {
        store.register(this)
    }

    override fun handle(state: AppState) {
        Platform.runLater {
            item = state
        }
    }

    fun createNewMockResource() {
        // TODO
    }

    fun deleteSelectedMockResource() {
        selected.get()?.let {
            store.dispatch(RemoveMockResourceAction(it))
        }
    }
}