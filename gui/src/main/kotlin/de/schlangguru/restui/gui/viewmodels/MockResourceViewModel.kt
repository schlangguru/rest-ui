package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.AppStore
import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResourceResponse
import javafx.beans.property.SimpleListProperty
import tornadofx.ItemViewModel
import tornadofx.observable

class MockResourceViewModel(
        private val store: AppStore = AppStore
): ItemViewModel<MockResource>() {
    val mockResourceID = bind (MockResource::id)
    val path = bind (MockResource::path)
    val method = bind (MockResource::method)
    val responses = bind { SimpleListProperty<MockResourceResponseViewModel>(item?.responses?.map { MockResourceResponseViewModel(item = it) }?.observable()) }

    val availableMethods = listOf("GET", "POST", "PUT", "DELETE", "HEAD").observable()
}

class MockResourceResponseViewModel(
        private val store: AppStore = AppStore,
        item: MockResourceResponse? = null
): ItemViewModel<MockResourceResponse>() {
    val name = bind (MockResourceResponse::name)
    val statusCode = bind (MockResourceResponse::statusCode)
    val contentType = bind (MockResourceResponse::contentType)
    val content = bind (MockResourceResponse::content)

    init {
        item?.let { this.item = it }
    }
}