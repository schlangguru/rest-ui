package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.actions.UpdateMockResourceAction
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResponse
import de.schlangguru.restui.app.server.SequentialResponseStrategy
import javafx.beans.property.SimpleListProperty
import tornadofx.ItemViewModel
import tornadofx.observable

class MockResourceViewModel(
        private val store: AppStore = AppStore
): ItemViewModel<MockResource>() {
    val mockResourceID = bind (MockResource::id)
    val path = bind (MockResource::path)
    val method = bind (MockResource::method)
    val responses = bind { SimpleListProperty<MockResponseViewModel>(item?.responses?.map { MockResponseViewModel(it) }?.observable()) }

    val availableMethods = listOf("GET", "POST", "PUT", "DELETE", "HEAD").observable()

    override fun onCommit() {
        store.dispatch(UpdateMockResourceAction(MockResource(
                mockResourceID.value,
                path.value,
                method.value,
                SequentialResponseStrategy(), //TODO
                responses.value.map { MockResponse(
                        it.name.value,
                        it.statusCode.value,
                        it.contentType.value,
                        it.content.value
                ) }
        )))
    }
}

class MockResponseViewModel(
        item: MockResponse
): ItemViewModel<MockResponse>() {
    val name = bind (MockResponse::name)
    val statusCode = bind (MockResponse::statusCode)
    val contentType = bind (MockResponse::contentType)
    val content = bind (MockResponse::content)

    init {
        this.item = item
    }
}