package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResourceResponse
import javafx.beans.property.SimpleListProperty
import tornadofx.ItemViewModel
import tornadofx.observable

class MockResourceViewModel: ItemViewModel<MockResource>() {
    val path = bind (MockResource::path)
    val method = bind (MockResource::method)
    val responses = bind { SimpleListProperty<MockResourceResponse>(item?.responses?.observable()) }

    val availableMethods = listOf("GET", "POST", "PUT", "DELETE", "HEAD").observable()
}
