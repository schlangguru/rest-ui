package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.model.MockResource
import de.schlangguru.restui.app.model.MockResourceResponse
import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.app.model.RequestHeader
import de.schlangguru.restui.app.server.ResponseStrategy
import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.ItemViewModel
import tornadofx.observable

class MockResourceViewModel: ItemViewModel<MockResource>() {
    val path = bind (MockResource::path)
    val method = bind (MockResource::method)
}
