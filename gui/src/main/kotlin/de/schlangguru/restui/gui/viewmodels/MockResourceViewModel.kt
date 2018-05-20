package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.UpdateMockResourceAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.app.server.ScriptedResponseStrategy
import de.schlangguru.restui.app.server.SequentialResponseStrategy
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel
import tornadofx.observable
import tornadofx.onChange

class MockResourceViewModel(
        private val store: AppStore = AppStore
): ItemViewModel<MockResource>() {
    val mockResourceID = bind (MockResource::id)
    val path = bind (MockResource::path)
    val method = bind (MockResource::method)
    val responseStrategy = bind (MockResource::responseStrategy)
    val responseStrategyName = SimpleStringProperty()
    val responseStrategyIcon = SimpleStringProperty()
    val responses = bind { SimpleListProperty<MockResponse>(item?.responses?.observable()) }

    val availableMethods = listOf("GET", "POST", "PUT", "DELETE", "HEAD").observable()

    init {
        responseStrategy.onChange {
            responseStrategyName.value = when(it) {
                is SequentialResponseStrategy -> "Sequential"
                is ScriptedResponseStrategy -> "Scripted"
                else -> "<unknown>"
            }

            responseStrategyIcon.value = when(it) {
                is SequentialResponseStrategy -> "/icons/alphabetical_sorting_az.png"
                is ScriptedResponseStrategy -> "/icons/document.png"
                else -> ""
            }
        }
    }

    override fun onCommit() {
        store.dispatch(UpdateMockResourceAction(MockResource(
                mockResourceID.value,
                path.value,
                method.value,
                SequentialResponseStrategy(), //TODO
                responses.value
        )))
    }
}

class MockResponseViewModel: ItemViewModel<MockResponse>() {
    val name = bind (MockResponse::name)
    val statusCode = bind (MockResponse::statusCode)
    val contentType = bind (MockResponse::contentType)
    val content = bind (MockResponse::content)

    override fun onCommit() {
        item = MockResponse(name.value, statusCode.value, contentType.value, content.value)
    }
}