package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppState
import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.AddMockResourceAction
import de.schlangguru.restui.app.actions.RemoveMockResourceAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.app.server.SequentialResponseStrategy
import de.schlangguru.restui.core.StateHandler
import de.schlangguru.restui.gui.observableList
import javafx.application.Platform
import javafx.beans.property.ReadOnlyListWrapper
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ItemViewModel
import tornadofx.observable

class MockResourceListViewModel(
        private val store: AppStore = AppStore
): ItemViewModel<AppState>(), StateHandler<AppState> {
    val mockResources = bind { ReadOnlyListWrapper<MockResource>(item?.mockResources?.observable()) }
    val selectedItem = SimpleObjectProperty<MockResource>()
    val selectedIndex = SimpleIntegerProperty()

    init {
        store.register(this)
    }

    override fun handle(state: AppState) {
        val currentSelectedIndex = selectedIndex.value
        Platform.runLater {
            item = state
            selectedIndex.value = currentSelectedIndex
        }
    }

    fun deleteSelectedMockResource() {
        selectedItem.get()?.let {
            store.dispatch(RemoveMockResourceAction(it))
        }
    }

    fun addMockResource(path: String) {
        var path = path
        if (!path.startsWith("/")) {
            path = "/$path"
        }

        val resource = MockResource(path = path, method = "GET", responseStrategy = SequentialResponseStrategy(), responses = emptyList<MockResponse>())
        store.dispatch(AddMockResourceAction(resource))
    }
}