package de.schlangguru.restui.gui.viewmodels

import de.schlangguru.restui.app.model.Request
import de.schlangguru.restui.app.model.RequestHeader
import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.ItemViewModel
import tornadofx.observable

class RequestViewModel: ItemViewModel<Request>() {
    val host = bind (Request::host)
    val path = bind (Request::path)
    val method = bind (Request::method)
    val headers = bind { ReadOnlyListWrapper<RequestHeader>(item?.headers?.observable()) }
    val entity = bind (Request::entity)
}

class RequestHeaderViewModel: ItemViewModel<RequestHeader>() {
    val name = bind (RequestHeader::name)
    val value = bind (RequestHeader::value)
}