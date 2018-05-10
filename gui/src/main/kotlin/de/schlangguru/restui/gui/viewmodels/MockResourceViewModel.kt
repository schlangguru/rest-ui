package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResourceResponse
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ItemViewModel
import tornadofx.observable

class MockResourceViewModel: ItemViewModel<MockResource>() {

    val path = bind (MockResource::path)
    val method = bind (MockResource::method)
    val responses = bind { SimpleListProperty<MockResourceResponse>(item?.responses?.observable()) }

    val selectedResponse = SimpleObjectProperty<MockResourceResponse>()
    val availableMethods = listOf("GET", "POST", "PUT", "DELETE", "HEAD").observable()
}

class MockResourceResponseViewModel: ItemViewModel<MockResourceResponse>() {
    val name = bind (MockResourceResponse::name)
    val statusCode = bind { SimpleIntegerProperty(item?.statusCode ?: 0) }
    val contentType = bind (MockResourceResponse::contentType)
    val content = bind (MockResourceResponse::content)
}